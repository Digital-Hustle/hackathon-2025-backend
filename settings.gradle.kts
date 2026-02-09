rootProject.name = "hackathon-2025-backend"

file("services").listFiles()?.forEach { serviceDir ->
    if (serviceDir.isDirectory && file("services/${serviceDir.name}/build.gradle.kts").exists()) {
        include("${serviceDir.name}")
        project(":${serviceDir.name}").projectDir = file("services/${serviceDir.name}")
    }
}