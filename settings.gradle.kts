pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // For fetching plugins like AGP and Kotlin Gradle Plugin
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Good practice
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SentinelApp" // Or your desired project name
include(":app") // Assuming your app module is named "app"