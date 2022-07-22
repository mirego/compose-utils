plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.compose"

android {
    defaultConfig {
        compileSdk = 33
        minSdk = 24
        targetSdk = 33
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    api("androidx.compose.foundation:foundation:${Versions.jetpackCompose}")
    api("androidx.compose.ui:ui:${Versions.jetpackCompose}")
    api("androidx.compose.animation:animation:${Versions.jetpackCompose}")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets.getByName("main").kotlin.srcDirs().toString())
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "utils"
                artifact(tasks["sourcesJar"])
            }
            create<MavenPublication>("debug") {
                from(components["debug"])
                artifactId = "utils"
                artifact(tasks["sourcesJar"])
            }
        }
    }
}
