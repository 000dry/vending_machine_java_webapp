package Handlers;

import App.App;
import Money.Coin;
import java.util.HashMap;

public class MessageHandler {

    String message;
    App app;
    HashMap<String, Coin> coinHandler;

    public MessageHandler(String message, App app){
        this.message = message;
        this.app = app;
        this.coinHandler = new CoinHandler().getCoinFromString();
    }

    public void commandFromClient(){
        if(message.equals("A")
                || message.equals("B")
                || message.equals("C")
                ) {
            app.vendingMachine.vendItem(message);
        } else if (message.equals("DOLLAR")
                || message.equals("QUARTER")
                || message.equals("DIME")
                || message.equals("NICKEL")
                ) {
            app.vendingMachine.addCoinToCoinsInserted(coinHandler.get(message));
        }
    }
}
