package User;

import Money.Coin;

import java.util.ArrayList;

public class User {

    ArrayList<Coin> wallet;
    int dollars;
    int quarters;
    int dimes;
    int nickels;


    public User(){
        this.wallet = new ArrayList<>();
        this.dollars = 0;
        this.quarters = 0;
        this.dimes = 0;
        this.nickels = 0;

        startWithTenDollarsTotal();
        countAllCoins();
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

    public void startWithTenDollarsTotal(){
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.DOLLAR);
        this.addCoinToWallet(Coin.QUARTER);
        this.addCoinToWallet(Coin.QUARTER);
        this.addCoinToWallet(Coin.QUARTER);
        this.addCoinToWallet(Coin.DIME);
        this.addCoinToWallet(Coin.DIME);
        this.addCoinToWallet(Coin.NICKEL);
    }

    public void receiveChange(ArrayList<Coin> change){
        if(change.size() != 0) {
            for (int i = 0; i < change.size(); i++) {
                Coin coin = change.get(i);
                this.addCoinToWallet(coin);
            }
        }
    }

    public void countDollars(){
        this.dollars = 0;
        for(int i = 0; i < this.wallet.size(); i++){
            if(this.wallet.get(i) == Coin.DOLLAR){
                this.dollars += 1;
            }
        }
    }

    public void countQuarters() {
        this.quarters = 0;
        for(int i = 0; i < this.wallet.size(); i++){
            if(this.wallet.get(i) == Coin.QUARTER){
                this.quarters += 1;
            }
        }
    }

    public void countDimes(){
        this.dimes = 0;
        for(int i = 0; i < this.wallet.size(); i++){
            if(this.wallet.get(i) == Coin.DIME){
                this.dimes += 1;
            }
        }
    }

    public void countNickels(){
        this.nickels = 0;
        for(int i = 0; i < this.wallet.size(); i++){
            if(this.wallet.get(i) == Coin.NICKEL) {
                this.nickels += 1;
            }
        }
    }

    public void countAllCoins() {
        countDollars();
        countQuarters();
        countDimes();
        countNickels();
    }
}
