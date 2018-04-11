import MachineComponents.VendingMachine;
import MoneyHandlers.AvailableChange;
import MoneyHandlers.Coin;
import StockTypes.Chocolate;
import StockTypes.Drink;
import StockTypes.Savoury;
import StockTypes.StockItem;
import org.junit.Before;
import org.junit.Test;

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
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.DOLLAR);
        user.addCoinToWallet(Coin.QUARTER);
        user.addCoinToWallet(Coin.NICKEL);
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
    public void canVendItem__returnsItem(){
        vendingMachine.restockItem("A", item1);
        assertEquals(item1, vendingMachine.vendItem("A"));
    }

    @Test
    public void canVendItem__diminishesStock(){
        vendingMachine.restockItem("A", item1);
        vendingMachine.vendItem("A");
        int stockOfKeyA = vendingMachine.getStockCountOf("A");
        assertEquals(9, stockOfKeyA);
    }

    @Test
    public void canVendItem__unlessStockIsEmpty(){
        assertEquals(null, vendingMachine.vendItem("A"));
    }

    //SERVICING

    @Test
    public void canRefillAllCoins(){
        vendingMachine.refillAllCoins();
        assertEquals(210, availableChange.getChange().size());
    }

    @Test
    public void canGetTotalValueOfAvailableChange__empty(){
        assertEquals(0.00, vendingMachine.getTotalValueOfChange(), 0.00);
    }

    @Test
    public void canGetTotalValueOfAvailableChange__afterRefill(){
        vendingMachine.refillAllCoins();
        assertEquals(50.00, vendingMachine.getTotalValueOfChange(), 0.00);
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
        boolean userHasCoin = user.removeCoinFromWallet(coin); //User set up with 2 * DOLLAR at @Before -- will equal true
        vendingMachine.insertCoin(userHasCoin, coin);
        assertEquals(1, vendingMachine.getCoinsInserted().size());
    }

    @Test
    public void userCannotInsertCoinsNotInWallet(){
        Coin coin = Coin.DIME;
        boolean userHasCoin = user.removeCoinFromWallet(coin); //User not given DIME at @Before -- will equal false
        vendingMachine.insertCoin(userHasCoin, coin);
        assertEquals(0, vendingMachine.getCoinsInserted().size());
    }




}
