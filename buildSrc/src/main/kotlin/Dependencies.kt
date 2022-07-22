import org.gradle.api.Project

val Project.Versions: Versions
    get() = Versions(this)

class Versions(project: Project) {
    val jetpackCompose = "1.2.0-rc03"
}
