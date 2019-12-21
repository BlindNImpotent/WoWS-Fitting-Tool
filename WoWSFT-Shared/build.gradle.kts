import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {

}

dependencies {

}

sourceSets {
    main {
        java.srcDirs("src/main/java")
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true