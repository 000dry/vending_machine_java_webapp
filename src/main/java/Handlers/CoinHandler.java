package Handlers;

import Money.Coin;
import java.util.HashMap;

public class CoinHandler {

    HashMap<String, Coin> coinFromString;

    public CoinHandler(){
        this.coinFromString = new HashMap<>();
        coinFromString.put("DOLLAR", Coin.DOLLAR);
        coinFromString.put("QUARTER", Coin.QUARTER);
        coinFromString.put("DIME", Coin.DIME);
        coinFromString.put("NICKEL", Coin.NICKEL);
    }

    public HashMap<String,Coin> getCoinFromString() {
        return this.coinFromString;
    }
}
