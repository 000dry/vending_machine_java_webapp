package Stock;

public class Chocolate extends StockItem {

    String ingredient;

    public Chocolate(String productName, double price, String ingredient){
        super(productName, price);
        this.ingredient = ingredient;
    }

}
