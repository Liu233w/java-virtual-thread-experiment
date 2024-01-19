plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.postgresql:postgresql:42.5.1")
    implementation("org.apache.commons:commons-dbcp2:2.9.0")
}

tasks.withType<JavaCompile> {
//    options.compilerArgs.add("--enable-preview")
}

tasks.test {
    useJUnitPlatform()
//    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec> {
//    jvmArgs("--enable-preview")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
