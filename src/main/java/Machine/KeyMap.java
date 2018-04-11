package Machine;

import Stock.StockItem;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyMap {

    HashMap<String, ArrayList> stock;

    public KeyMap(){
        this.stock = new HashMap<>();
        stock.put("A", new ArrayList<StockItem>());
        stock.put("B", new ArrayList<StockItem>());
        stock.put("C", new ArrayList<StockItem>());
    }

    public void addStock(String key, StockItem item){
        ArrayList<StockItem> stockToAdd = this.stock.get(key);
        stockToAdd.add(item);
        stock.put(key, stockToAdd);
    }

    public void removeStock(String key, ArrayList<StockItem> remainingStock) {
        stock.put(key, remainingStock);
    }
}
