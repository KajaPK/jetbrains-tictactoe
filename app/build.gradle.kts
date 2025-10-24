plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":lib"))
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("org.jetbrains.kotlinx.tictactoe.MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}
