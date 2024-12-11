plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.opencsv:opencsv:5.5.2")
    implementation("com.vk.api:sdk:1.0.16")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("org.slf4j:slf4j-api:1.7.26")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.commons:commons-collections4:4.5.0-M2")
    implementation("commons-io:commons-io:2.17.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    implementation("org.xerial:sqlite-jdbc:3.47.1.0")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("jfree:jfreechart:1.0.13")
}

tasks.test {
    useJUnitPlatform()
}