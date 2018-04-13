# Vending Machine App

A vending machine modelled in Java and hooked up to the front-end using the Javalin micro web framework.

[Live Demo](https://vending-machine-app.herokuapp.com/) - live demo of the app deployed using Heroku.

## Getting Started

This app can be deployed locally from command line. Navigate from the root directory to /target and run the following command:

```
java -jar com.example.deltaDNATest-1.0-SNAPSHOT-jar-with-dependencies.jar
```
This will run the app and launch the server, which can be found in the browser at:

```
http://localhost:7070/
```

__NOTE:__ The jar file has not yet been executed from machine other than the one it was built on but as far as I know I didn't create any custom build paths, so the build should be universal.

Alternatively, run Server.java from IntelliJ.

## Built With

* [Javalin](https://javalin.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Jackson](https://github.com/FasterXML/jackson) - JSON library for Java


## Acknowledgments

The server for this app was adapted from a few tutorials found at https://javalin.io/tutorials/ by [David Åse](https://github.com/tipsy/).

