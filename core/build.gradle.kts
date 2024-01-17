plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("org.jetbrains.dokka")
}

android {
    namespace = "com.mcxinyu.javascriptinterface"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

//tasks.dokkaHtml.configure {
//    moduleName.set("javascript-interface")
//    outputDirectory.set(rootProject.projectDir.resolve("docs/core"))
//}

group = "com.github.mcxinyu"
version = Versions.coreLibVersion

val sourcesJar by tasks.register<Jar>("sourcesJar"){
    archiveClassifier.set("sources")
    from(android.sourceSets.map { it.java.getSourceFiles() })
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])

                groupId = "com.github.mcxinyu"
                artifactId = "javascript-interface"

                artifact(sourcesJar)
            }
        }
        repositories {
            maven {
                name = "XXX"
                url = uri("${project.buildDir}/repo")
            }
        }
    }
}

dependencies {
    compileOnly("androidx.core:core-ktx:1.9.0")
    compileOnly("androidx.appcompat:appcompat:1.5.1")
}