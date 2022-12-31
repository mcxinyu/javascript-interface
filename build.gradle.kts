// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0-rc03" apply false
    id("com.android.library") version "7.4.0-rc03" apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlinVersion apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
}
