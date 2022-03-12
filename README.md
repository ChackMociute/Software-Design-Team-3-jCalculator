# Software Design project 'jCalculator'
## Team 3

### Team Members

1. Martynas Vaznonis
2. Bekir Altun
3. Matthijs Hulsebos
4. Isaac Lewis

## Source code
There are code in 3 places, divided to allow for plugin development
app/ contains the main framework responsible for handling user input and managing plugins
api/ contains the shared classes used both by the applications and the plugins
plugins/ contains some sample plugins

## Building
Running the following command will build the app, api, and sample plugins
gradlew build
A jar with the plugins folder is created in app/build/libs/
Run with
java -jar \[jar name\]
You might need to run as administrator for it to work
