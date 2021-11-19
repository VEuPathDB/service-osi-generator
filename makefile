APP_PACKAGE  := $(shell ./gradlew -q print-package)
PWD          := $(shell pwd)
MAIN_DIR     := src/main/java/$(shell echo $(APP_PACKAGE) | sed 's/\./\//g')
TEST_DIR     := $(shell echo $(MAIN_DIR) | sed 's/main/test/')
GEN_DIR      := $(MAIN_DIR)/generated
ALL_PACKABLE := $(shell find src/main -type f)
BIN_DIR      := .tools/bin

C_BLUE := "\\033[94m"
C_NONE := "\\033[0m"
C_CYAN := "\\033[36m"

.PHONY: default
default:
	@echo "Please choose one of:"
	@echo ""
	@echo "$(C_BLUE)  make compile$(C_NONE)"
	@echo "    Compiles the existing code in 'src/'.  Regenerates files if the"
	@echo "    api spec has changed."
	@echo ""
	@echo "$(C_BLUE)  make test$(C_NONE)"
	@echo "    Compiles the existing code in 'src/' and runs unit tests."
	@echo "    Regenerates files if the api spec has changed."
	@echo ""
	@echo "$(C_BLUE)  make jar$(C_NONE)"
	@echo "    Compiles a 'fat jar' from this project and it's dependencies."
	@echo ""
	@echo "$(C_BLUE)  make docker$(C_NONE)"
	@echo "    Builds a runnable docker image for this service"
	@echo ""
	@echo "$(C_BLUE)  make install-dev-env$(C_NONE)"
	@echo "    Ensures the current dev environment has the necessary "
	@echo "    installable tools to build this project."
	@echo ""
	@echo "$(C_BLUE)  make gen-jaxrs$(C_NONE)"
	@echo "    Ensures the current dev environment has the necessary "
	@echo "    installable tools to build this project."
	@echo ""

.PHONY: compile
compile: install-dev-env
	@./gradlew clean compileJava

.PHONY: test
test: install-dev-env
	@./gradlew clean test

.PHONY: jar
jar: install-dev-env build/libs/service.jar

.PHONY: docker
docker:
	@docker build --no-cache -t $(shell ./gradlew -q print-container-name) \
		--build-arg=GITHUB_USERNAME=$(GITHUB_USERNAME) \
		--build-arg=GITHUB_TOKEN=$(GITHUB_TOKEN) .

.PHONY: install-dev-env
install-dev-env:
	@if [ ! -d .tools ]; then \
		git clone https://github.com/VEuPathDB/lib-jaxrs-container-build-utils .tools; \
	else \
		cd .tools && git pull && cd ..; \
	fi
	@$(BIN_DIR)/check-env.sh
	@$(BIN_DIR)/install-fgputil.sh
	@$(BIN_DIR)/install-oracle.sh
	@$(BIN_DIR)/install-raml2jaxrs.sh
	@$(BIN_DIR)/install-raml-merge.sh
	@$(BIN_DIR)/install-npm.sh

fix-path:
	@$(BIN_DIR)/fix-path.sh $(MAIN_DIR)
	@$(BIN_DIR)/fix-path.sh $(TEST_DIR)

gen-jaxrs: api.raml merge-raml
	@$(BIN_DIR)/generate-jaxrs.sh $(APP_PACKAGE)
	@echo "$(C_BLUE)Monkeypatch workarounds for generator bugs$(C_NONE)"
	# Fix Organism API interface
	@sed -i 's/long/Long/g' "$(GEN_DIR)/resources/Organisms.java"
	# Fix Organism POST request
	@sed -i 's/long/Long/g' "$(GEN_DIR)/model/OrganismPostRequest.java"
	@sed -i 's/long/Long/g' "$(GEN_DIR)/model/OrganismPostRequestImpl.java"
	# Fix Organism PUT request
	@sed -i 's/long/Long/g' "$(GEN_DIR)/model/OrganismPutRequest.java"
	@sed -i 's/long/Long/g' "$(GEN_DIR)/model/OrganismPutRequestImpl.java"
	# Fix IdSet API interface
	@sed -i 's/long/Long/g' "$(GEN_DIR)/resources/IdSets.java"


gen-docs: api.raml merge-raml
	@$(BIN_DIR)/generate-docs.sh

merge-raml:
	@$(BIN_DIR)/merge-raml schema > schema/library.raml

.PHONY: api-test
api-test:
	docker-compose -f docker/docker-compose.test.yml down
	docker-compose -f docker/docker-compose.test.yml build
	docker-compose -f docker/docker-compose.test.yml run test
	docker-compose -f docker/docker-compose.test.yml down

#
# File based targets
#

build/libs/service.jar: \
      gen-jaxrs \
      gen-docs \
      vendor/fgputil-accountdb-1.0.0.jar \
      vendor/fgputil-cache-1.0.0.jar \
      vendor/fgputil-cli-1.0.0.jar \
      vendor/fgputil-core-1.0.0.jar \
      vendor/fgputil-db-1.0.0.jar \
      vendor/fgputil-events-1.0.0.jar \
      vendor/fgputil-json-1.0.0.jar \
      vendor/fgputil-server-1.0.0.jar \
      vendor/fgputil-servlet-1.0.0.jar \
      vendor/fgputil-solr-1.0.0.jar \
      vendor/fgputil-test-1.0.0.jar \
      vendor/fgputil-web-1.0.0.jar \
      vendor/fgputil-xml-1.0.0.jar \
      build.gradle.kts \
      service.properties
	@echo "$(C_BLUE)Building application jar$(C_NONE)"
	@./gradlew clean test jar
