plugins {
    id("com.android.application") version Versions.ANDROID_GRADLE_PLUGIN apply false
    id("com.android.library") version Versions.ANDROID_GRADLE_PLUGIN apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version Versions.KTLINT apply false

    id("mirego.release") version "2.0"
    id("mirego.publish") version "1.0"
}

buildscript {
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.KTLINT}")
    }
}

release {
    checkTasks = listOf(
        ":utils:check"
    )
    buildTasks = listOf(
        ":utils:publish"
    )
    updateVersionPart = 2
}

tasks {
    val writeDevVersion by registering(WriteProperties::class) {
        outputFile = file("${rootDir}/gradle.properties")
        properties(java.util.Properties().apply { load(outputFile.reader()) }.mapKeys { it.key.toString() })
        val gitCommits = "git rev-list --count HEAD".runCommand(workingDir = rootDir)
        val originalVersion = project.version.toString().replace("-dev\\w+".toRegex(), "")
        property("version", "$originalVersion-dev$gitCommits")
    }
}
