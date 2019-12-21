plugins {
    java
    id("org.springframework.boot") version "2.2.2.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.8.RELEASE" apply false
}

group = "WoWSFT"
version = "1.0"

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java.sourceCompatibility = JavaVersion.VERSION_1_8

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework:spring-context-support")

        implementation("org.apache.commons:commons-lang3")
        implementation("org.apache.commons:commons-collections4:4.4")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}