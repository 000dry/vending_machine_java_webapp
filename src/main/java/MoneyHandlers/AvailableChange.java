package MoneyHandlers;

import java.util.ArrayList;

public class AvailableChange {

    ArrayList<Coin> change;
    int dollarQuantity;
    int quarterQuantity;
    int dimeQuantity;
    int nickelQuantity;

    public AvailableChange(int dollarQuantity, int quarterQuantity, int dimeQuantity, int nickelQuantity){
        this.change = new ArrayList<>();
        this.dollarQuantity = dollarQuantity;
        this.quarterQuantity = quarterQuantity;
        this.dimeQuantity = dimeQuantity;
        this.nickelQuantity = nickelQuantity;
    }

    public ArrayList<Coin> getChange(){
        return this.change;
    }

    public int getDollarQuantity() {
        return this.dollarQuantity;
    }

    public int getQuarterQuantity() {
        return this.quarterQuantity;
    }

    public int getDimeQuantity() {
        return this.dimeQuantity;
    }

    public int getNickelQuantity() {
        return this.nickelQuantity;
    }

    public void addCoin(Coin coin) {
        this.change.add(coin);
    }

    public void refillDollars() {
        int dollarCount = 0;

        while(dollarCount < this.dollarQuantity){
            this.addCoin(Coin.DOLLAR);
            dollarCount++;
        }
    }

    public void refillQuarters(){
        int quarterCount = 0;

        while(quarterCount < this.quarterQuantity){
            this.addCoin(Coin.QUARTER);
            quarterCount++;
        }
    }

    public void refillDimes(){
        int dimeCount = 0;

        while(dimeCount < this.dimeQuantity){
            this.addCoin(Coin.DIME);
            dimeCount++;
        }
    }

    public void refillNickels(){
        int nickelCount = 0;

        while(nickelCount < this.nickelQuantity){
            this.addCoin(Coin.NICKEL);
            nickelCount++;
        }
    }
}
