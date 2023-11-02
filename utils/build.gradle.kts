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
    namespace = "com.mirego.compose.utils"

    defaultConfig {
        compileSdk = 34
        minSdk = 21
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.KOTLIN_COMPILER
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
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

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "utils"
            }
        }
    }
}

ktlint {
    android.set(true)
}
