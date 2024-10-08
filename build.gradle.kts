plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "kopo.data"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}
ext {
	// open feign 용 cloud version
	set("springCloudVersion", "2023.0.3")
}
//extra["springCloudVersion"] = "2023.0.3"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	implementation("org.springframework.boot:spring-boot-starter-mail")
	// 타임리프 사용 안함!!
//	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains:annotations:24.0.0")
    compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	// 테스팅용 h2 인메모리 데이터베이스!
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")

	// openfeign 추가
	implementation ("org.springframework.cloud:spring-cloud-starter-openfeign")

	// production code 에서 json 만질 일이 있어서 추가..
//	implementation("com.google.code.gson:gson:2.11.0")

	// 테스트코드에서 사용하고싶어서 추가..
    testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testImplementation("com.google.code.gson:gson:2.8.9") NOTE record 지원안해서 버전업
	testImplementation("com.google.code.gson:gson:2.11.0")
}



dependencyManagement {
	imports {
		// ext, extra[] 안먹혀 local val 로 추가
		val springCloudVersion = "2023.0.3"
		// open feign 을 위해 BOM 추가
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
