plugins {

}

dependencies {
    implementation(project(":WoWSFT-Shared"))
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        resources.srcDir("src/main/resources")
    }
}