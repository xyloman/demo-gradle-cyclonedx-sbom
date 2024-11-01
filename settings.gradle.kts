rootProject.name = "demo"

plugins {
    id("com.gradle.develocity") version("3.18.1")
    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.0.2"
}

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
    }
}
