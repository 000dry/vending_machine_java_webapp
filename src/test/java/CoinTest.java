import MoneyHandlers.Coin;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinTest {

    @Test
    public void canGetValue(){
        assertEquals(0.10, Coin.DIME.getValue(), 0.00);
    }

}
