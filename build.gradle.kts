plugins {
    id("java")
}

group = "bet.astral"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val jda = "5.1.1";

dependencies {
    implementation("net.dv8tion:JDA:$jda") {
        // Optionally disable audio natives to reduce jar size by excluding `opus-java`
        exclude(module="opus-java")
    }

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}