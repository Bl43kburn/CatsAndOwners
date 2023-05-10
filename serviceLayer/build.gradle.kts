plugins {
    id("java")
    id("java-library")
}

group = "com.blackburn"

repositories {
    mavenCentral()
}

dependencies{
    implementation(project(":cats:persistenceLayer"))

    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.springframework.data:spring-data-jpa:3.0.4")

    implementation("cz.jirutka.rsql:rsql-parser:2.1.0")
    implementation("io.github.perplexhub:rsql-querydsl-spring-boot-starter:6.0.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.5")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
