plugins {
    id("com.android.library")
    id("kotlin-android")
    id("mirego.publish")
    id("org.jlleitschuh.gradle.ktlint")
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
        kotlinCompilerExtensionVersion = Versions.KOTLIN_COMPILER
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:${Versions.COMPOSE_BOM}")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    api("androidx.compose.foundation:foundation")
    api("androidx.compose.ui:ui")
    api("androidx.compose.animation:animation")
    api("androidx.compose.material:material")
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(kotlin.sourceSets["main"].kotlin.srcDirs)
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
        }
    }
}

ktlint {
    android.set(true)
}
