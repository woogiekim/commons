import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
    kotlin("jvm") version "2.2.21"
    kotlin("kapt") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("kotlin")
        plugin("kotlin-spring")
    }

    group = "io.github.woogiekim"
    version = "0.0.1"
    description = "commons"

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        compileOnly("org.projectlombok:lombok")
        compileOnly("org.jetbrains.kotlin:kotlin-reflect")
        compileOnly("org.apache.commons:commons-lang3:3.12.0")

        annotationProcessor("org.projectlombok:lombok")

        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("com.ninja-squad:springmockk:3.1.1")

        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        dependsOn(tasks.ktlintCheck)
    }

    configure<KtlintExtension> {
        verbose.set(true)
    }
}

project("core") {
    dependencies {
        compileOnly("org.springframework.boot:spring-boot-starter")
        compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    }
}

project("mvc") {
    dependencies {
        implementation(project(":core"))

        implementation("org.modelmapper:modelmapper:3.0.0")
        implementation("org.modelmapper:modelmapper-module-record:1.0.0")

        compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
        compileOnly("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        compileOnly("org.springframework.boot:spring-boot-starter-web")
    }
}