// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    afterEvaluate {
        extensions.findByType<io.gitlab.arturbosch.detekt.extensions.DetektExtension>()?.apply {
            toolVersion = "1.23.7"
            config.setFrom(rootProject.files("config/detekt/detekt.yml"))
            buildUponDefaultConfig = true
            ignoreFailures = true
        }

        extensions.findByType<org.jlleitschuh.gradle.ktlint.KtlintExtension>()?.apply {
            ignoreFailures.set(true)
        }
    }
}

tasks.register("codeQuality") {
    description = "Run all code quality checks (detekt and ktlint)"
    group = "verification"
    dependsOn(
        "detekt",
        "ktlintCheck",
    )
}

tasks.register("codeFormat") {
    description = "Format code using ktlint"
    group = "formatting"
    dependsOn("ktlintFormat")
}
