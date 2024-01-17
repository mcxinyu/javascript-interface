plugins {
    `kotlin-dsl`
}

repositories {
    google()
    gradlePluginPortal()
    maven("https://packages.aliyun.com/maven/repository/2011675-release-hz6rRf/") {
        credentials { username = "5ef809301732b0ac2fd33e49"; password = "_r=B(pBaU-7t" }
    }
    maven("https://repo.nju.edu.cn/repository/maven-public/")
}