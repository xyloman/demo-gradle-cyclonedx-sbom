plugins {
    java
    `maven-publish`
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.cyclonedx.bom") version "1.10.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.platform:junit-platform-reporting")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Test>().configureEach {
    val outputDir = reports.junitXml.outputLocation
    jvmArgumentProviders += CommandLineArgumentProvider {
        listOf(
            "-Djunit.platform.reporting.open.xml.enabled=true",
            "-Djunit.platform.reporting.output.dir=${outputDir.get().asFile.absolutePath}"
        )
    }
}


publishing {
    publications {
        create<MavenPublication>("bootJava") {
            artifact(tasks.named("bootJar"))
        }
    }

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/xyloman/demo-gradle-cyclonedx-sbom")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}