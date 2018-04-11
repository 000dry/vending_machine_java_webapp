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
        this.availableChange = new AvailableChange(25, 40, 60, 75);
        this.vendingMachine = new VendingMachine(availableChange, 10);

        this.user = new User();

        this.item1 = new Savoury("Wotsits", 0.65, "Crisps", "Cheese");
        this.item2 = new Chocolate("Snickers", 1.00, "Peanut");
        this.item3 = new Drink("Dr Pepper", 1.25);

        this.vendingMachine.refillAllCoins();
        this.vendingMachine.restockAllItems(this.item1, this.item2, this.item3);

        this.user.addCoinToWallet(Coin.DOLLAR);
        this.user.addCoinToWallet(Coin.DOLLAR);
        this.user.addCoinToWallet(Coin.QUARTER);
        this.user.addCoinToWallet(Coin.QUARTER);
        this.user.addCoinToWallet(Coin.QUARTER);
        this.user.addCoinToWallet(Coin.DIME);
        this.user.addCoinToWallet(Coin.DIME);
        this.user.addCoinToWallet(Coin.NICKEL); //User.User starts with $3.00
    }
}
