# Software Design project 'jCalculator'
## Team 3

### Team Members

1. Martynas Vaznonis
2. Isaac Lewis
3. Bekir Altun
4. Matthijs Hulsebos

## Pre built 
A pre-built fat jar is provided in the out\ folder.

## Building
To build the gradle build task must be ran from IDEA (For now it doesn't work from the command line for some reason)

Add configuration -> + button -> Gradle 
    -> Gradle project: root directory (software-design-vu-2020)
    -> Task: build
    
Run the configuration in IDEA, and the fat-jar along with the sample plugins is built to app\build\libs

## Running
The fat jar file can be ran with this command:

    java -jar app-plugin-fatjar.jar
  
  
## Structure
There is code in 3 places, divided to allow for plugin development

* app/ contains the main framework responsible for handling user input and managing plugins
* api/ contains the shared classes used both by the applications and the plugins
* plugins/ contains some sample plugins
