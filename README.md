# Software Design project 'jCalculator'
## Team 3

### Team Members

1. Martynas Vaznonis
2. Isaac Lewis
3. Bekir Altun
4. Matthijs Hulsebos

## Source code
There is code in 3 places, divided to allow for plugin development

* app/ contains the main framework responsible for handling user input and managing plugins
* api/ contains the shared classes used both by the applications and the plugins
* plugins/ contains some sample plugins

## Building
Running the following command will build the app, api, and sample plugins

    ./gradlew build

A jar with the plugins folder is created in app/build/libs/ that can be ran with

## Running
The fat jar file can be run from where it is generated or from the out folder by typing:

    java -jar app-plugin-fatjar.jar
  
