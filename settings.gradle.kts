pluginManagement {
    resolutionStrategy {
        repositories {
            gradlePluginPortal()
            google()
            mavenCentral()
            maven("https://s3.amazonaws.com/mirego-maven/public")
        }

        eachPlugin {
            if (requested.id.namespace == "mirego") {
                useModule("mirego:${requested.id.name}-plugin:${requested.version}")
            }
        }
    }
}
rootProject.name = "ComposeUtils"

include(":utils")
