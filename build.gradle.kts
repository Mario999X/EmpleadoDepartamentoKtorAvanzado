val ktor_version: String by project
val kotlin_version: String by project
//val logback_version: String by project
val logbackclassic_version: String by project
val micrologging_version: String by project

// Koin
val koin_ktor_version: String by project
val ksp_version: String by project
val koin_ksp_version: String by project
val koin_version: String by project

// Base de dato
val h2_r2dbc_version: String by project
val kotysa_version: String by project

// Cache
val cache_version: String by project

plugins {
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

group = "resa.mario"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor core
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")

    // Motor de Ktor
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")

    // JSON content negotiation
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")

    // Logging
    //implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("ch.qos.logback:logback-classic:$logbackclassic_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")

    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor_version") // Koin para Ktor
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor_version") // Koin para Ktor con Logger
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version") // Si usamos Koin con KSP Anotaciones
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version") // Si usamos Koin con KSP Anotaciones

    // H2 R2DBC para usar H2 como base de datos
    // Reactividad con Kotysa
    implementation("org.ufoss.kotysa:kotysa-r2dbc-coroutines:$kotysa_version")
    // H2 R2DBC para usar H2 como base de datos
    runtimeOnly("io.r2dbc:r2dbc-h2:$h2_r2dbc_version")

    // Cache
    implementation("io.github.reactivecircus.cache4k:cache4k:$cache_version")
}

// Para Koin Annotations, directorio donde se encuentran las clases compiladas
// KSP - To use generated sources
sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}