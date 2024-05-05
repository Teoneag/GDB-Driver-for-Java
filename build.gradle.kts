plugins {
    id("java")
    id("application")
}

group = "com.teoneag"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.teoneag.Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}