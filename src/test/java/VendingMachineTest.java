import Machine.VendingMachine;
import Money.AvailableChange;
import Money.Coin;
import Stock.Chocolate;
import Stock.Drink;
import Stock.Savoury;
import Stock.StockItem;
import User.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class VendingMachineTest {

    AvailableChange availableChange;
    VendingMachine vendingMachine;
    StockItem item1;
    StockItem item2;
    StockItem item3;
    int maxStock;
    User user;

    @Before
    public void before(){
        availableChange = new AvailableChange(30, 40, 60, 80);

        vendingMachine = new VendingMachine(availableChange, 10);
        maxStock = vendingMachine.getMaxStock();

        item1 = new Savoury("Wotsits", 0.65, "Crisps", "Cheese");
        item2 = new Chocolate("Snickers", 1.00, "Peanut");
        item3 = new Drink("Dr Pepper", 1.25);

        user = new User();
    }

    //STOCK

    @Test
    public void canGetAmountInserted__startsEmpty(){
        assertEquals(0, vendingMachine.getCoinsInserted().size());
    }

    @Test
    public void canGetMaxStockPerItem(){
        assertEquals(10, vendingMachine.getMaxStock());
    }

    @Test
    public void canAccessStockViaAlphabeticKeys__shouldStartEmpty(){
        int stockOfKeyA = vendingMachine.getStockCountOf("A");
        assertEquals(0, stockOfKeyA);
    }

    @Test
    public void canAddItemsToVendingMachineStock(){
        vendingMachine.addItemToStock("B", item1);
        vendingMachine.addItemToStock("B", item1);
        int stockOfKeyB = vendingMachine.getStockCountOf("B");
        assertEquals(2, stockOfKeyB);
    }

    @Test
    public void canRestockItemToMaximumStock(){
        vendingMachine.addItemToStock("C", item1);
        vendingMachine.addItemToStock("C", item1);
        vendingMachine.restockItem("C", item1);
        int stockOfKeyC = vendingMachine.getStockCountOf("C");
        assertEquals(maxStock, stockOfKeyC);
    }

    @Test
    public void canRestockAllItemsToMaximumStock(){
        vendingMachine.restockAllItems(item1, item2, item3);

        int stockOfKeyA = vendingMachine.getStockCountOf("A");
        int stockOfKeyB = vendingMachine.getStockCountOf("B");
        int stockOfKeyC = vendingMachine.getStockCountOf("C");
        assertEquals(maxStock, stockOfKeyA);
        assertEquals(maxStock, stockOfKeyB);
        assertEquals(maxStock, stockOfKeyC);
    }

    //VENDING


    @Test
    public void canVendItem__diminishesStock(){
        vendingMachine.restockItem("A", item1);
        vendingMachine.vendItem("A");
        int stockOfKeyA = vendingMachine.getStockCountOf("A");
        assertEquals(9, stockOfKeyA);
    }
    //SERVICING

    @Test
    public void canRefillAllCoins(){
        vendingMachine.refillAllCoins();
        assertEquals(210, availableChange.getChange().size());
    }

    @Test
    public void canOnlyRefillChangeWhenEmpty(){
        vendingMachine.refillAllCoins();
        vendingMachine.refillAllCoins();
        assertEquals(210, availableChange.getChange().size());
    }

    @Test
    public void canEmptyAvailableChange(){
        vendingMachine.refillAllCoins();
        vendingMachine.emptyAllCoins();
        assertEquals(0, availableChange.getChange().size());
    }

    //TRANSACTION

    @Test
    public void canAddCoinToAmountInserted(){
        vendingMachine.addCoinToCoinsInserted(Coin.DOLLAR);
        assertEquals(1, vendingMachine.getCoinsInserted().size());
    }

    @Test
    public void userCanInsertCoinIfAvailableInWallet(){
        Coin coin = Coin.DOLLAR;
        boolean userHasCoin = user.removeCoinFromWallet(coin); //User.User set up with 2 * DOLLAR at @Before -- will equal true
        vendingMachine.insertCoin(userHasCoin, coin);
        assertEquals(1, vendingMachine.getCoinsInserted().size());
    }


    @Test
    public void runningTotalUpdatesWhen__coinIsInserted(){
       vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
       vendingMachine.addCoinToCoinsInserted(Coin.DIME);
       assertEquals(0.35, vendingMachine.getRunningTotal(), 0.00);
    }

    @Test
    public void runningTotalUpdatesWhen__itemIsVended(){
        vendingMachine.restockAllItems(item1, item2, item3);
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.vendItem("A");
        assertEquals(0.10, vendingMachine.getRunningTotal(), 0.00);
    }

    @Test
    public void canAddAllCoinsInsertedToAvailableChange(){
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.refillAllCoins();
        vendingMachine.addCoinsInsertedToAvailableChange();
        assertEquals(215, vendingMachine.getAvailableChange().getChange().size());
    }

    @Test
    public void canGiveChangeFromTransactionToUser(){
        vendingMachine.addCoinToCoinsInserted(Coin.QUARTER);
        vendingMachine.addCoinToCoinsInserted(Coin.DOLLAR);
        vendingMachine.addCoinToCoinsInserted(Coin.DOLLAR);
        vendingMachine.addCoinToCoinsInserted(Coin.NICKEL);
        vendingMachine.refillAllCoins();
        ArrayList<Coin> change = vendingMachine.giveChangeFromTransaction();
        user.receiveChange(change);
        assertEquals(206, vendingMachine.getAvailableChange().getChange().size());
        assertEquals(19, user.getWallet().size());
    }

}
