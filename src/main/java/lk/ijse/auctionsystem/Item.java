package lk.ijse.auctionsystem;

public class Item {
    private String itemName;
    private double startPrice;

    public Item() {}

    public Item(String itemName, double startPrice) {
        this.itemName = itemName;
        this.startPrice = startPrice;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public double getStartPrice() {
        return startPrice;
    }
    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }
}
