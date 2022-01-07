package model;

public class Meal {
    private String id;
    private String type;
    private double price;
    private int qty;

    public Meal() {
    }

    public Meal(String id, String type, double price, int qty) {
        this.setId(id);
        this.setType(type);
        this.setPrice(price);
        this.setQty(qty);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
