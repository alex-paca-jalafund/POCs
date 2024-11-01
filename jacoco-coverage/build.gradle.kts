plugins {
    kotlin("jvm") version "1.8.10"
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation(fileTree("${projectDir}/src/ExternalProjects/calculator/build/classes/java/test") {
        include("**/*.class")
    })
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    classDirectories.setFrom(files("${projectDir}/build/classes/java/main"))

    classDirectories.setFrom(files(
        fileTree("${projectDir}/src/ExternalProjects/calculator/build/classes/java/main") {
            include("**/*.class")
        },
        fileTree("${projectDir}/src/ExternalProjects/calculator/build/classes/java/test") {
            include("**/*.class")
        }
    ))

    additionalSourceDirs.setFrom(files("${projectDir}/src/main/java"))
    sourceDirectories.setFrom(files("${projectDir}/src/main/java"))
}
