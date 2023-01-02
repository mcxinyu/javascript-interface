plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("org.jetbrains.dokka")
}

android {
    namespace = "com.mcxinyu.javascriptinterface.tbsx5"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//tasks.dokkaHtml.configure {
//    moduleName.set("javascript-interface-tbsx5")
//    outputDirectory.set(rootProject.projectDir.resolve("docs/tbsx5"))
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
                artifactId = "javascript-interface-tbsx5"

                artifact(sourcesJar)
            }
        }
    }
}

dependencies {
    compileOnly("androidx.core:core-ktx:1.9.0")
    compileOnly("androidx.appcompat:appcompat:1.5.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    compileOnly("com.tencent.tbs:tbssdk:44226")
    compileOnly(project(":core"))
}