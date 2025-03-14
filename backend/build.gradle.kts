plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("kapt") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "me.fortibrine"
version = "0.0.1"

tasks {
	compileJava {
		sourceCompatibility = "17"
		targetCompatibility = "17"
	}

	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
		kotlinOptions {
			jvmTarget = "17"
		}
	}

	bootJar {
		archiveFileName.set("NmtApp.jar")
	}

}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.postgresql:postgresql:42.7.4")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.core:jackson-databind")

	implementation("org.codehaus.jackson:jackson-core-asl:1.9.13")
	implementation("org.codehaus.jackson:jackson-mapper-asl:1.9.13")

	implementation("org.mapstruct:mapstruct:1.5.3.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

//	implementation("org.flywaydb:flyway-core")
}

//kotlin {
//
//	compilerOptions {
//		freeCompilerArgs.addAll("-Xjsr305=strict")
//	}
//}
