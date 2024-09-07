plugins {
    id("java")
    id("application")
}

group = "com.teoneag"
version = "1.1-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.0")
    implementation("com.teoneag:simple-cli:1.1-SNAPSHOT")
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