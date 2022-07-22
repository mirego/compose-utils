plugins {
    id("com.android.application") version "7.3.0-beta05" apply false
    id("com.android.library") version "7.3.0-beta05" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false

    id("mirego.release") version "2.0"
    id("mirego.publish") version "1.0"
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
