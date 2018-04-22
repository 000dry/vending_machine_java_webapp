# Overview

The Vending machine is composed of Java models and a controller (MessageHandler), with a Javalin server that serves up JSON (converted using Jackson ObjectMapper, sent via Websocket) to a JS client.

__Javalin__ - Java/Kotlin micro web framework built on top of Jetty with only slightly less performance, similar to Sinatra/Express/Koa. Contains useful condensation of some Jetty methods (e.g. websockets are easier to utilise). Great docs and tutorials, from which my server was adapted.

__Jackson__ - used Jackson’s ObjectMapper to convert the App Java object to JSON so that it could be used by the JS client.

__Maven__ - dependency and build management. Used because Javalin docs are geared towards Maven, including tutorial on Heroku deployment.

## User
```
ArrayList<Coin> wallet;
int dollars;
int quarters;
int dimes;
int nickels;
```
Has a wallet and the ability to track the quantity of each type of coin.

## Stock Item
```
String productName;
double price;
```
Product name and price, no real special methods or properties.

## Coin
```
NICKEL(0.05),
DIME(0.10),
QUARTER(0.25),
DOLLAR(1.00);
```
Coin enum with values. Although further down the line this required converting back and forth, it helps the coin to have properties beyond just its value, and means only certain predefined values can exist (e.g. no chance of a 35 cent coin sneaking through).

## Available Change
```
ArrayList<Coin> change;
int dollarQuantity;
int quarterQuantity;
int dimeQuantity;
int nickelQuantity;
```
Array of coins with integers that provide the quantities of each coin type the machine should have on startup/refill.

## KeyMap
```
public KeyMap(){
    this.stock = new HashMap<>();
    stock.put("A", new ArrayList<StockItem>());
    stock.put("B", new ArrayList<StockItem>());
    stock.put("C", new ArrayList<StockItem>());
}
```
HashMap that pairs alphabetical string keys with empty ArrayList values. VendingMachine class can fill these with StockItems.

## Vending Machine
```
AvailableChange availableChange;
ArrayList<Coin> coinsInserted;
int maxStockPerItem;
KeyMap allItems;
double runningTotal;
```
Where the business happens.

* availableChange - object from which it gives change for transactions and adds money spent to.

* coinsInserted - each coin inserted individually and their value added to the runningTotal. They are kept entirely separate from the transaction and the whole array is added to the availableChange once the user ends the transaction and receives change.

* maxStockPerItem — property defines the max stock per item — stock of item cannot exceed this.

* runningTotal — used to track total value of coins inserted and deductions when a product is purchased. Also used to calculate how many of each coin is due when change is given.

## App
```
VendingMachine vendingMachine;
User user;
AvailableChange availableChange;
StockItem item1;
StockItem item2;
StockItem item3;
```
This class exists to instantiate all the required components of the Vending Machine together and doesn’t have any real methods of its own other than getters.

As this is where everything operates from when running, it may have been better to have the majority of the methods live here.

## Server
```
ws.onMessage((WsSession session, String message) -> {
    if (message.equals("NEW SESSION")) {
        app = new App();
    }
    mH = new MessageHandler(message, app);
    mH.commandFromClient();
    String updatedJsonInString = mapper.writeValueAsString(app);
    session.getRemote().sendString(updatedJsonInString);
});
```
Brief snapshot of server functionality __^

onMessage called when server receives message from client.

Processes this using the MessageHandler class, which are provided the App in its current state and the message to act upon.

The message is processed with the commandFromClient() method and the updated app is sent back to the client as JSON.

## CoinHandler
```
public CoinHandler(){
    this.coinFromString = new HashMap<>();
    coinFromString.put("DOLLAR", Coin.DOLLAR);
    coinFromString.put("QUARTER", Coin.QUARTER);
    coinFromString.put("DIME", Coin.DIME);
    coinFromString.put("NICKEL", Coin.NICKEL);
}
```
CoinHandler is a HashMap that pairs ‘coin’ string keys with their Enum counterpart.
As messages from the client are received as strings, this is helpful for converting the message to something useful to the controller/model.

## MessageHandler
```
if(this.message.equals("A")
        || this.message.equals("B")
        || this.message.equals("C")
        ) {
    this.app.getVendingMachine().vendItem(this.message);

} else if (this.message.equals("DOLLAR")
        || this.message.equals("QUARTER")
        || this.message.equals("DIME")
        || this.message.equals("NICKEL")
        ) {
    boolean userHasCoin = this.app.getUser().removeCoinFromWallet(coinHandler.get(this.message));
    this.app.getVendingMachine().insertCoin(userHasCoin, coinHandler.get(this.message));
    this.app.getUser().countAllCoins();

} else if(this.message.equals("SERVICE")) {
    this.app.getVendingMachine().emptyAllCoins();
    this.app.getVendingMachine().restockAllItems(this.app.getItem1(), this.app.getItem2(), this.app.getItem3());
    this.app.getVendingMachine().refillAllCoins();

} else if(this.message.equals("RETURN")) {
    this.app.getVendingMachine().addCoinsInsertedToAvailableChange();
    ArrayList<Coin> change = this.app.getVendingMachine().giveChangeFromTransaction();
    this.app.getUser().receiveChange(change);
    this.app.getUser().countAllCoins();

} else {
    System.out.println("^--Non-actionable message from Client");
}
```
— If the message is an alphabetical key, e.g. the string “A” —>
* ```.vendItem()``` — vends from stock ArrayList paired with that key.

— If the message is a coin, e.g. the string “DOLLAR” —>

* ```getUser().removeCoinFromWallet()``` — stored in a variable to be used by insertCoin, as this method removes a coin and returns true if successful.

* ```getVendingMachine().insertCoin()``` — adds coin to coins inserted - requires userHasCoin == true in order to proceed

* ```getUser().countAllCoins()``` — recounts the quantities of each type of coin in the users wallet so that when the updated App gets sent to the client, it displays the correct amounts.

— If “SERVICE” —>

* ```.emptyAllCoins()``` — empties change ArrayList in 	availableChange

* ```.restockAllItems()``` - currently not very dynamic — restocks all items given to the maximum amount defined by the vendingMachine.

* ```.refillAllCoins()``` - refills coins to the required quantity of each as set by the properties in ```availableChange```.

— If “RETURN —>

* ```addCoinsInsertedToAvailableChange()``` - clears insertedCoins and adds its contents to ```availableChange.change```

* ```ArrayList<Coin> change``` — variable calls ```.giveChangeFromTransaction()``` which works out and returns the appropriate number of each type of coin to give as change

* ```.receiveChange()``` - change from transaction added to User’s wallet

* Then ```.countAllCoins()``` is called again.



## app.js

* generally speaking, consists of adding event listeners to buttons, which send a message to the server corresponding to the button/action when clicked.

* most of the buttons also check to see if the conditions are valid for that button to actually send a message to the server

* e.g. for this purpose, app.js has a runningTotal of it’s own, to ensure enough money has been inserted to pay for an item before messaging the server to complete the transaction.

* end-to-end validation — transaction is validated on both the server and the client.
