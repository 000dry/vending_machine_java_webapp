# Vending Machine App

A vending machine modelled in Java and hooked up to the front-end using the Javalin micro web framework.

[Live Demo](https://vending-machine-app.herokuapp.com/) - live demo of the app deployed using Heroku.

For demoing purposes, two console logs have been left in so that changes in the user and vending machine states can be tracked in the browser's console.

## Getting Started

This app can be deployed locally from command line. From the root directory, run the following command:

```
java -jar ./target/com.example.deltaDNATest-1.0-SNAPSHOT-jar-with-dependencies.jar
```
This will run the app and launch the server, which can be found in the browser at:

```
http://localhost:7070/
```

__NOTE:__ The jar file has not yet been executed from a machine other than the one it was built on but as far as I know I didn't create any custom build paths, so the build should be universal.

Alternatively, run Server.java from IntelliJ.

## App Instructions

* App loads with 10 of each item in the vending machine and $10 worth of coins in the user's wallet.
* Click on a coin button to insert a coin and bring up a running total of coins inserted.
* Hit either the button "A", "B" or "C" when you are ready to purchase.
* The item selected will be vended in the bottom-right of the grid.
* Hit "Return" to end the transaction and receive all change.
* Hit "Service" to refil stock and reset the machine's available change.
* Hit "New Session" to reset all states.

## Built With

* [Javalin](https://javalin.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Jackson](https://github.com/FasterXML/jackson) - JSON library for Java


## Acknowledgments

The server for this app was adapted from a few tutorials found at https://javalin.io/tutorials/ by [David Åse](https://github.com/tipsy/).

