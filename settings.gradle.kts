pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.fabricmc.net")
    }
}

plugins {
    id("io.github.bizcub.multiloader") version "0.8+"
}

multiloader {
    match("26.2", fb, fg)

//    match("26.2",   fb, nf)
//    match("26.1.2", fb, nf)
//    match("1.21.11",fb, nf)
//    match("1.21.10",fb, nf)
//    match("1.21.8", fb, nf)
//    match("1.21.5", fb, nf)
//    match("1.21.4", fb, nf)
//    match("1.21.3", fb, nf)
//    match("1.21.1", fb, nf)
//    match("1.20.6", fb)
//    match("1.20.4", fb)
//    match("1.20.2", fb)
//    match("1.20.1", fb)
}
