plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.2"
}

jmh {
    iterations = 3
    warmupIterations = 1
    threads = 1
    warmup = "1s"

    includes = listOf("MockBlockingBenchmark")
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

    implementation("org.openjdk.jmh:jmh-core:1.33")
    implementation("org.openjdk.jmh:jmh-generator-annprocess:1.33")

    implementation("io.projectreactor:reactor-core:3.4.10")
}

tasks.withType<JavaCompile> {
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}