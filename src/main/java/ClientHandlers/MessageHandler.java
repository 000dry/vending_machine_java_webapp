package ClientHandlers;

import App.App;
import Money.Coin;

import java.util.ArrayList;
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
    }
}
