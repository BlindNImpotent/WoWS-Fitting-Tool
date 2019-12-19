import org.springframework.boot.gradle.tasks.bundling.BootWar

plugins {
    java
    id("war")
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

dependencies {
    implementation(project(":WoWSFT-Shared"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-context-support")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-collections4:4.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

//    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

val Project.profile get() = findProperty("profile") ?: "dev"

sourceSets {
    main {
        java.srcDirs("src/main/java", ":WoWSFT-Shared/src/main/java")
        resources.srcDirs("src/main/resources", "src/main/resources-$profile")
    }
}

tasks.getByName<BootWar>("bootWar") {
    mainClassName = "WoWSFT.Application"

    from("src/main/ebextensions") {
        into(".ebextensions")
    }
}