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

## Using in Composite builds

In case you have a copy of this project locally and want to include the 
files from the local copy rather than the remote:

In your project, add the following to `settings.gradle`
```
if (file("../Common/.composite-enable").exists()) {
    includeBuild ('../Common/') {
        dependencySubstitution {
            substitute module('com.github.valartech:android-commons') with project(':commons')
        }
    }
}
``` 

Add these tasks to your project `build.gradle`:
```
task enableCommonsCompositeBuild {
    group = 'Tools'
    description = 'Enable Commons composite build'
    doLast {
        new File("../Common/.composite-enable").createNewFile()
    }
}

task disableCommonsBuild {
    group = 'Tools'
    description = 'Disable Commons composite build'
    doLast {
        File file = file("../Common/.composite-enable")
        if (file.exists()) {
            file.delete()
        }
    }
}
```
Run as needed to enable/disable composite builds. 

Note that your copy of this project needs
1. To be a sibling project to your copy of your own project.
2. To be inside a folder named "Common".  
