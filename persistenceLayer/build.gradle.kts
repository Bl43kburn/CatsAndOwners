plugins {
    id("java")
    id("java-library")
}


group = "com.blackburn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")

    implementation("org.postgresql:postgresql:42.6.0")

    implementation("org.projectlombok:lombok:1.18.22")

    annotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("org.springframework.boot:spring-boot-starter-security:3.0.5")

}
