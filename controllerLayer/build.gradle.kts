plugins {
    id("java")
    id("java-library")
    id("org.springframework.boot") version "3.0.5"
}

group = "com.blackburn"

repositories {
    mavenCentral()
}

dependencies{
    implementation(project(":cats:serviceLayer"))

    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    implementation("org.springframework.boot:spring-boot-starter-web:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.5")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.0.4")
    implementation("org.springframework.data:spring-data-jpa:3.0.4")


    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.5")

    testCompileOnly("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")

    testImplementation("org.springframework.security:spring-security-test:6.0.2")
    testImplementation("org.mockito:mockito-inline:5.2.0")
}

tasks.test {
    useJUnitPlatform()
}