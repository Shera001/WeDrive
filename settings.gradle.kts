pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WeDriveTest"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":feature:wallet")
include(":core:data")
include(":core:domain")
include(":core:designsystem")
include(":feature:addcard")
include(":core:network")
include(":core:common")
include(":core:datastore")
