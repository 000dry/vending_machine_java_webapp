import MoneyHandlers.AvailableChange;
import MoneyHandlers.Coin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AvailableChangeTest {

    AvailableChange availableChange;

    @Before
    public void before(){
        availableChange = new AvailableChange(25, 40, 60, 75);
    }

    @Test
    public void canGetChange__startsEmpty(){
        assertEquals(0, availableChange.getChange().size());
    }

    @Test
    public void canAddCoin(){
        availableChange.addCoin(Coin.DOLLAR);
        assertEquals(1, availableChange.getChange().size());
    }

    @Test
    public void canRefillDollars(){
        availableChange.refillDollars();
        assertEquals(availableChange.getDollarQuantity(), availableChange.getChange().size());
    }

    @Test
    public void canRefillQuarters(){
        availableChange.refillQuarters();
        assertEquals(availableChange.getQuarterQuantity(), availableChange.getChange().size());
    }

    @Test
    public void canRefillDimes(){
        availableChange.refillDimes();
        assertEquals(availableChange.getDimeQuantity(), availableChange.getChange().size());
    }

    @Test
    public void canRefillNickels(){
        availableChange.refillNickels();
        assertEquals(availableChange.getNickelQuantity(), availableChange.getChange().size());
    }
}
