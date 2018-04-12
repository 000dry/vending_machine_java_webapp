package ClientHandlers;

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
        System.out.println("From client: " + message);

        if(this.message.equals("A")
                || this.message.equals("B")
                || this.message.equals("C")
                ) {
            app.getVendingMachine().vendItem(this.message);

        } else if (this.message.equals("DOLLAR")
                || this.message.equals("QUARTER")
                || this.message.equals("DIME")
                || this.message.equals("NICKEL")
                ) {
            boolean userHasCoin = app.getUser().removeCoinFromWallet(coinHandler.get(this.message));
            app.getVendingMachine().insertCoin(userHasCoin, coinHandler.get(this.message));

        } else if(this.message.equals("SERVICE")) {

            app.getVendingMachine().emptyAllCoins();
            app.getVendingMachine().restockAllItems(app.getItem1(), app.getItem2(), app.getItem3());
            app.getVendingMachine().refillAllCoins();

        } else if(this.message.equals("RETURN")) {

        } else {
            System.out.println("^--Invalid message from Client");
        }
    }
}
