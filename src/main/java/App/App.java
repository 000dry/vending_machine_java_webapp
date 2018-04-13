package App;

import Machine.VendingMachine;
import Money.AvailableChange;
import Money.Coin;
import Stock.Chocolate;
import Stock.Drink;
import Stock.Savoury;
import Stock.StockItem;
import User.User;


public class App{

    VendingMachine vendingMachine;
    User user;
    AvailableChange availableChange;
    StockItem item1;
    StockItem item2;
    StockItem item3;

    public App(){
        this.availableChange = new AvailableChange(30, 40, 60, 80); //AvailableChange starts with 210 coins
        this.vendingMachine = new VendingMachine(availableChange, 10);

        this.user = new User();

        this.item1 = new Savoury("Wotsits", 0.65, "Crisps", "Cheese");
        this.item2 = new Chocolate("Snickers", 1.00, "Peanut");
        this.item3 = new Drink("Dr Pepper", 1.25);

        this.vendingMachine.refillAllCoins(); //VendingMachine starts with $50.00
        this.vendingMachine.restockAllItems(this.item1, this.item2, this.item3);

    }

    public VendingMachine getVendingMachine() {
        return this.vendingMachine;
    }

    public User getUser(){
        return this.user;
    }

    public StockItem getItem1(){
        return this.item1;
    }

    public StockItem getItem2() {
        return this.item2;
    }

    public StockItem getItem3() {
        return this.item3;
    }
}
