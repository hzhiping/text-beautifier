plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

// shadowJar {
//  archiveBaseName.set("${project.name}.jar")
// }

group = "com.hzhiping"
version = "1.0.4"

repositories {
    // mavenCentral()
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
}

dependencies {
    // https://mvnrepository.com/artifact/cn.hutool/hutool-all
    // implementation("cn.hutool:hutool-all:5.8.31")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    localPath.set("D:\\install\\JetBrains\\IntelliJ IDEA Community Edition 2024.2.0.1")
    // version.set("2023.2.6")
    // type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        // 解决编译时中文报错
        options.encoding = "UTF-8"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"

    }

    // 添加以下内容，解决运行时控制台中文乱码，字符编码为 UTF-8
    withType<JavaExec> {
        jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8")
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
