plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"

}

android {
    namespace = "com.example.mockmessagetester"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.mockmessagetester"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    // Compose 核心 BOM (物料清单，自动管理版本)
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

// Compose 基础 UI 库与图形库
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
// Compose 预览工具
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
// Material Design 3 (我们极简主题的基础)
    implementation("androidx.compose.material3:material3")
// 小图标
    implementation("androidx.compose.material:material-icons-extended")
//viewmodel for compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("androidx.compose.foundation:foundation")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
}