import MoneyHandlers.Coin;

import java.util.ArrayList;

public class User {

    ArrayList<Coin> wallet;

    public User(){
        this.wallet = new ArrayList<>();
    }

    public ArrayList<Coin> getWallet() {
        return this.wallet;
    }


    public void addCoinToWallet(Coin coin) {
        this.wallet.add(coin);
    }

    public boolean removeCoinFromWallet(Coin coin){
        return this.wallet.remove(coin);
    }
}
