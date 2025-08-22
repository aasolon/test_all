plugins {
    `java-library`
//    id("org.springframework.boot") version "2.5.15"
//    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
//    maven {
//        url "https://nexus.sigma.sbrf.ru/nexus/content/groups/public"
//        allowInsecureProtocol = true
//    }
    mavenLocal()

    mavenCentral()
}

ext["h2.version"] = "1.4.197"

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.4")
        mavenBom("io.modelcontextprotocol.sdk:mcp-bom:0.11.2")
    }
}

dependencies {
//    JETTY
//    implementation ('org.springframework.boot:spring-boot-starter-web') {
//        exclude group: 'org.springframework.boot', module: 'spring-boot-starter-tomcat'
//    }
//    implementation "org.springframework.boot:spring-boot-starter-jetty"

//  TOMCAT
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

    implementation("org.apache.httpcomponents.client5:httpclient5")
    implementation("commons-io:commons-io:2.9.0")
    implementation("com.google.guava:guava:31.0.1-jre")

    implementation("org.jooq:jooq:3.13.4")
    implementation("org.jooq:jooq-meta:3.13.4")
    implementation("org.jooq:jooq-codegen:3.13.4")

    implementation("com.github.briandilley.jsonrpc4j:jsonrpc4j:1.6")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.apache.commons:commons-lang3")
//    implementation("commons-fileupload:commons-fileupload:1.4")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.69")

    implementation("org.apache.ignite:ignite-core:2.10.0")
    implementation("org.apache.ignite:ignite-spring:2.10.0")
//
//    implementation("org.springframework.boot:spring-boot-starter-cache")

    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.kafka:spring-kafka")

//    implementation("ru.sberbank.pprb.sbbol.digitalapi:services:DEV-SNAPSHOT")


    // раскомментировать, чтобы автоматом поднялся Ignnite
//    implementation("org.apache.ignite:ignite-spring-boot-autoconfigure-ext:1.0.0")

    implementation("io.modelcontextprotocol.sdk:mcp-spring-webmvc")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

// When you apply the io.spring.dependency-management plugin, Spring Boot’s plugin will automatically import
// the spring-boot-dependencies bom from the version of Spring Boot that you are using.
// This provides a similar dependency management experience to the one that’s enjoyed by Maven users.
// For example, it allows you to omit version numbers when declaring dependencies that are managed in the bom.
// А чтобы вывести список версий, которые приносит с собой spring-boot-dependencies BOM, можно использовать кастомную таску
// list-spring-boot-dependency-bom-version
// Список версий хранится в переменной dependencyManagement.importedProperties
// (https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/#accessing-properties)
// ./gradlew list-spring-boot-dependency-bom-version
tasks.register("list-spring-boot-dependency-bom-version") {
    group = "introspection"
    description = "Print properties from all BOMs"
    doLast {
        println(dependencyManagement.importedProperties)
    }
}



