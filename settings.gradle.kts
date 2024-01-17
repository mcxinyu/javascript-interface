pluginManagement {
    repositories {
        maven("https://repo.nju.edu.cn/repository/maven-public/")
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://packages.aliyun.com/maven/repository/2011675-release-hz6rRf/") {
            credentials { username = "5ef809301732b0ac2fd33e49"; password = "_r=B(pBaU-7t" }
        }
        mavenCentral()
    }
}
dependencyResolutionManagement {
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://repo.nju.edu.cn/repository/maven-public/")
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://packages.aliyun.com/maven/repository/2011675-release-hz6rRf/") {
            credentials { username = "5ef809301732b0ac2fd33e49"; password = "_r=B(pBaU-7t" }
        }
        mavenCentral()
    }
}
rootProject.name = "javascript-interface"
include(":app")
include(":core")
include(":tbsx5")
