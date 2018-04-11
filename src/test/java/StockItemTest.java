import StockTypes.Savoury;
import StockTypes.StockItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockItemTest {

    StockItem stockItem;

    @Before
    public void before(){
        stockItem = new Savoury("Wotsits", 0.65, "Crisps", "Cheese");
    }

    @Test
    public void productHasName(){
        assertEquals("Wotsits", stockItem.getName());
    }

    @Test
    public void hasPrice(){
        assertEquals(0.65, stockItem.getPrice(), 0.00);
    }
}
