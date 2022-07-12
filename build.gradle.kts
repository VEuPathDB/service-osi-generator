import org.veupathdb.lib.gradle.container.util.Logger.Level

plugins {
  java
  id("org.veupathdb.lib.gradle.container.container-utils") version "4.0.0"
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

// configure VEupathDB container plugin
containerBuild {

  // Change if debugging the build process is necessary.
  logLevel = Level.Info

  // General project level configuration.
  project {

    // Project Name
    name = "osi-generator"

    // Project Group
    group = "org.veupathdb.service"

    // Project Version
    version = "1.0.0"

    // Project Root Package
    projectPackage = "org.veupathdb.service.osi"

    // Main Class Name
    mainClassName = "Main"
  }

  // Docker build configuration.
  docker {

    // Docker build context
    context = "."

    // Name of the target docker file
    dockerFile = "docker/SVC.Dockerfile"

    // Resulting image tag
    imageName = "osi-generator-service"
  }

  generateJaxRS {
    // List of custom arguments to use in the jax-rs code generation command
    // execution.
    arguments = listOf(/*arg1, arg2, arg3*/)

    // Map of custom environment variables to set for the jax-rs code generation
    // command execution.
    environment = mapOf(/*Pair("env-key", "env-val"), Pair("env-key", "env-val")*/)
  }

}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

tasks.shadowJar {
  exclude("**/Log4j2Plugins.dat")
  archiveFileName.set("service.jar")
}

repositories {
  mavenCentral()
  mavenLocal()
  maven {
    name = "GitHubPackages"
    url  = uri("https://maven.pkg.github.com/veupathdb/maven-packages")
    credentials {
      username = if (extra.has("gpr.user")) extra["gpr.user"] as String? else System.getenv("GITHUB_USERNAME")
      password = if (extra.has("gpr.key")) extra["gpr.key"] as String? else System.getenv("GITHUB_TOKEN")
    }
  }
}

//
// Project Dependencies
//

dependencies {

  // Core lib
  implementation("org.veupathdb.lib:jaxrs-container-core:6.0.0")

  // Jersey
  implementation("org.glassfish.jersey.core:jersey-server:3.0.4")
  implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:3.0.4")

  // Jackson
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.3")

  // Utils
  implementation("org.gusdb:fgputil-core:2.7.1-jakarta")
  implementation("io.vulpine.lib:Jackfish:1.1.0")
  implementation("io.vulpine.lib:java-sql-import:0.2.0")
  implementation("io.vulpine.lib:lib-query-util:2.1.0")
  implementation("com.devskiller.friendly-id:friendly-id:1.1.0")
  implementation("com.zaxxer:HikariCP:3.4.5")
  implementation("org.postgresql:postgresql:42.3.6")
  implementation("info.picocli:picocli:4.6.3")

  // Log4J
  implementation("org.apache.logging.log4j:log4j-api:2.17.2")
  implementation("org.apache.logging.log4j:log4j-core:2.17.2")

  // Metrics (can remove if not adding custom service metrics over those provided by container core)
  implementation("io.prometheus:simpleclient:0.15.0")
  implementation("io.prometheus:simpleclient_common:0.15.0")

  // Unit Testing
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
  testImplementation("org.mockito:mockito-core:2.28.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

}
