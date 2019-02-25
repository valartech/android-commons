# ValarTech Android Commons

## WIP: Cleanup, documentation and generification of classes is pending
![Release](https://jitpack.io/v/valartech/android-commons.svg)

Utilities and libraries used across most projects, in one nice bundle.

## Adding to your project

```
allprojects {
    repositories { //not under buildscript
        jcenter()
        maven { url "https://jitpack.io" } 
    }
}

dependencies {
    implementation "com.github.valartech:android-commons:${version}"
}
```

Use the snapshot version by using
```
implementation "com.github.valartech:android-commons:master-SNAPSHOT"
```
instead, and adding the following to your module `build.gradle`:
```
configurations.all {
    // Check for updates every build for SNAPSHOTs
    resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
}
```
