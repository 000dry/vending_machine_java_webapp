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

    public VendingMachine(AvailableChange availableChange, int maxStockPerItem){
       this.availableChange = availableChange;
       this.coinsInserted = new ArrayList<>();
       this.maxStockPerItem = maxStockPerItem;
       this.allItems = new KeyMap();
    }

    //GETTERS

    public ArrayList<Coin> getCoinsInserted() {
        return this.coinsInserted;
    }

    public int getMaxStock() {
        return this.maxStockPerItem;
    }

    public int getStockCountOf(String key) {
        return this.allItems.stock.get(key).size();
    }

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

            return vendedItem;
        }
        return null;
    }

    //SERVICING

    public void refillAllCoins() {
        if(availableChange.getChange().size() == 0) {
            availableChange.refillDollars();
            availableChange.refillQuarters();
            availableChange.refillDimes();
            availableChange.refillNickels();
        }
    }

    public double getTotalValueOfChange() {
        ArrayList<Coin> coins = this.availableChange.getChange();
        double total = 0.00;

        for(int i = 0; i < coins.size(); i++){
            double coin = coins.get(i).getValue();
            total += coin;
        }
        return Math.round(total*100.00)/100.00;
    }

    public void emptyAllCoins() {
        this.availableChange.getChange().clear();
    }

    //TRANSACTION

    public void addCoinToCoinsInserted(Coin coin){
        this.coinsInserted.add(coin);
    }

    public void insertCoin(boolean userHasCoin, Coin coin) {
        if(userHasCoin){
            this.addCoinToCoinsInserted(coin);
        }
    }
}
