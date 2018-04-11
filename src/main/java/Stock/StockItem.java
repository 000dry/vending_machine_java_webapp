package Stock;

public abstract class StockItem {

    String productName;
    double price;

    public StockItem(String productName, double price){
        this.productName = productName;
        this.price = price;
    }

    public String getName() {
        return this.productName;
    }

    public double getPrice() {
        return this.price;
    }
}
