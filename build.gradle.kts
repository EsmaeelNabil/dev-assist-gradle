import com.vanniktech.maven.publish.GradlePlugin
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.1.20"
    id("java-gradle-plugin")
    id("com.vanniktech.maven.publish") version "0.31.0"
}

group = project.property("GROUP") as String
version = project.property("VERSION_NAME") as String

repositories {
    mavenCentral()
    google()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("com.android.tools.build:gradle:8.9.2")

    testImplementation("junit:junit:4.13.2")
}

gradlePlugin {
    plugins {
        create("devAssistPlugin") {
            id = "dev.supersam.devassist"
            implementationClass = "dev.supersam.devassist.DevAssistPlugin"
            displayName = "DevAssist Plugin"
            description = "A plugin that transforms BuildConfig to allow runtime value overrides"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

mavenPublishing {
    configure(GradlePlugin(javadocJar = JavadocJar.Javadoc(), sourcesJar = true))
    publishToMavenCentral(host = SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
}
