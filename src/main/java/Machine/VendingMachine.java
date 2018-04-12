package Machine;

import Money.AvailableChange;
import Money.Coin;
import Stock.StockItem;

import java.util.ArrayList;

public class VendingMachine {

    AvailableChange availableChange;
    ArrayList<Coin> coinsInserted;
    int maxStockPerItem;
    KeyMap allItems;
    double runningTotal;

    public VendingMachine(AvailableChange availableChange, int maxStockPerItem){
       this.availableChange = availableChange;
       this.coinsInserted = new ArrayList<>();
       this.maxStockPerItem = maxStockPerItem;
       this.allItems = new KeyMap();
       this.runningTotal = 0.00;
    }

    //GETTERS


    public AvailableChange getAvailableChange() {
        return this.availableChange;
    }

    public ArrayList<Coin> getCoinsInserted() {
        return this.coinsInserted;
    }

    public int getMaxStock() {
        return this.maxStockPerItem;
    }

    public int getStockCountOf(String key) {
        return this.allItems.stock.get(key).size();
    }

    public double getRunningTotal(){ return Math.round(this.runningTotal * 100.00)/100.00; }

    //STOCK

    public void addItemToStock(String key, StockItem item) {
        this.allItems.addStock(key, item);
    }

    public void restockItem(String key, StockItem item) {
        int currentStockOfItem = this.getStockCountOf(key);

        while(currentStockOfItem < this.maxStockPerItem){
            this.addItemToStock(key, item);
            currentStockOfItem++;
        }
    }

    public void restockAllItems(StockItem item1, StockItem item2, StockItem item3) {
        restockItem("A", item1);
        restockItem("B", item2);
        restockItem("C", item3);
    }

    //VENDING

    public StockItem vendItem(String key) {
        ArrayList<StockItem> remainingStock = this.allItems.stock.get(key);

        if(remainingStock.size() > 0) {
            StockItem vendedItem = remainingStock.remove(0);
            this.allItems.removeStock(key, remainingStock);
            this.runningTotal -= vendedItem.getPrice();
            return vendedItem;
        }
        return null;
    }

    //SERVICING

    public void refillAllCoins() {
        if(this.availableChange.getChange().size() == 0) {
            this.availableChange.refillDollars();
            this.availableChange.refillQuarters();
            this.availableChange.refillDimes();
            this.availableChange.refillNickels();
        }
    }

//    public double getTotalValueOfChange() {
//        ArrayList<Coin> coins = this.availableChange.getChange();
//        double total = 0.00;
//
//        for(int i = 0; i < coins.size(); i++){
//            double coin = coins.get(i).getValue();
//            total += coin;
//        }
//        return Math.round(total*100.00)/100.00;
//    }

    public void emptyAllCoins() {
        this.availableChange.getChange().clear();
    }

    //TRANSACTION

    public void addCoinToCoinsInserted(Coin coin){
        this.coinsInserted.add(coin);
        this.runningTotal += coin.getValue();
    }

    public void insertCoin(boolean userHasCoin, Coin coin) {
        if(userHasCoin){
            this.addCoinToCoinsInserted(coin);
        }
    }

    public void addCoinsInsertedToAvailableChange() {
        if(this.coinsInserted.size() != 0) {
            for (int i = this.coinsInserted.size(); i > 0; i--) {
                Coin coin = this.coinsInserted.remove(i - 1);
                this.availableChange.addCoin(coin);
            }
        }
    }

    public ArrayList<Coin> giveChangeFromTransaction(){
        ArrayList<Coin> coinsToReturn = new ArrayList<>();
        double dollarValue = Coin.DOLLAR.getValue();
        double quarterValue = Coin.QUARTER.getValue();
        double dimeValue = Coin.DIME.getValue();
        double nickelValue = Coin.NICKEL.getValue();

        while(this.getRunningTotal() >= dollarValue){
            coinsToReturn.add(Coin.DOLLAR);
            this.availableChange.removeCoin(Coin.DOLLAR);
            this.runningTotal -= dollarValue;
        }
        while(this.getRunningTotal() >= quarterValue){
            coinsToReturn.add(Coin.QUARTER);
            this.availableChange.removeCoin(Coin.QUARTER);;
            this.runningTotal -= quarterValue;
        }
        while(this.getRunningTotal() >= dimeValue){
            coinsToReturn.add(Coin.DIME);
            this.availableChange.removeCoin(Coin.DIME);
            this.runningTotal -= dimeValue;
        }
        while(this.getRunningTotal() >= nickelValue){
            coinsToReturn.add(Coin.NICKEL);
            this.availableChange.removeCoin(Coin.NICKEL);
            this.runningTotal -= nickelValue;
        }
        this.runningTotal = 0;
        return coinsToReturn;
    }


}
