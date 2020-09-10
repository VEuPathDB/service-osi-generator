FROM veupathdb/alpine-dev-base:latest

COPY [ "gradle/", "gradle/" ]
COPY [ "settings.gradle.kts", "build.gradle.kts", "gradlew", "./" ]

RUN ./gradlew getDeps

COPY [ "src/", "src/" ]

CMD ./gradlew test # --tests '*SearchCollectionsTest'
