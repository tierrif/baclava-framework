# Baclava Framework
Simple JDA command client for any JDA bot using JDA-Reactor.
# Usage
[Javadoc](https://hotlava03.github.io/baclavaframework/io/github/hotlava03/baclavaframework/package-summary.html)
## Dependencies
[JDA](https://github.com/DV8FromTheWorld/JDA) - **J**ava **D**iscord **A**PI wrapper.  
[JDA-Reactor](https://github.com/MinnDevelopment/jda-reactor) - Reactor tools for asynchronous event handling for JDA.  
[SLF4J-Simple](https://mvnrepository.com/artifact/org.slf4j/slf4j-simple/1.6.1) - Thread-safe logger for Java.
    
###If using gradle, do the following:
  
Create a directory at the root of your project named `lib` and add BaclavaFramework's jar in it. You can find this in the repository's releases section.  
**Your repositories and dependencies in your build.gradle should look like this:**
```groovy
repositories {
    jcenter()
}

dependencies {
    implementation 'net.dv8tion:JDA:VERSION'
    implementation 'club.minnced:jda-reactor:VERSION'
    implementation 'org.slf4j:slf4j-simple:VERSION'
    implementation fileTree(dir: 'lib', include: '*.jar')
}
```  
I recommend using shadow. More information [here](https://github.com/johnrengelman/shadow). Your gradle plugins should look like this:
```groovy
plugins {
    id 'java'
    id 'application'
    id 'com.github.johnrengelman.shadow' version 'VERSION'
}
```
**Replace `VERSION` with the respective library version.** 
