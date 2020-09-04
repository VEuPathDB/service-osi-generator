import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    java
}

group = "org.veupathdb.service"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
  testImplementation("com.fasterxml.jackson.core:jackson-databind:2.+")

  testImplementation("com.zaxxer:HikariCP:3.4.5")
  testImplementation("org.postgresql:postgresql:42.2.15")

  testImplementation("io.rest-assured", "rest-assured", "4.3.1")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

tasks.withType<Test> {
  testLogging {
    events.addAll(listOf(FAILED,
      SKIPPED,
      STANDARD_OUT,
      STANDARD_ERROR,
      PASSED))

    exceptionFormat = TestExceptionFormat.FULL
    showExceptions = true
    showCauses = true
    showStackTraces = true
    showStandardStreams = true
    enableAssertions = true
  }
  maxParallelForks = 1
}

val test by tasks.getting(Test::class) {
  // Use junit platform for unit tests
  useJUnitPlatform()
}

tasks.register("getDeps") {
  doLast {
    configurations
      .filter { it.isCanBeResolved }
      .forEach { it.resolve() }
  }
}
