package Stock;

public class Savoury extends StockItem {

    String type;
    String flavour;

    public Savoury(String productName, double price, String type, String flavour){
        super(productName, price);
        this.type = type;
        this.flavour = flavour;
    }
}
