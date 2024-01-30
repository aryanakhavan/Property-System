import java.io.Serializable;

public class Property implements Serializable {
    private static int cid = 0;
    private final int id;
    private String name;
    private String location;
    private String details;
    private int price;
    private int area;

    private final User seller;

    public static  void updateID(int id) {
        if (cid < id) {
            cid = id;
        }
    }
    public Property(String name, String location, int price, int area, User seller) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.area = area;
        this.seller = seller;
        this.id = ++cid;
    }

    public Property(String name, String location, int price, int area, User seller, String details) {
        this(name, location, price, area, seller);
        this.details = details;
    }

    @Override
    public String toString() {
        return String.format("--Property--\n" +
                "ID: %d\n" +
                "Name: %s\n" +
                "Location: %s\n" +
                "Price: %d\n" +
                "Area: %d\n" +
                "Details: %s\n" +
                "Seller: %s %s\n" +
                "------------", id, name, location, price, area, details, seller.getUsername(), seller.getPhone());
    }

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public int getArea() {
        return area;
    }

    public int getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public void setName(String name) {
        if (name.isEmpty())
            return;
        this.name = name;
    }

    public void setLocation(String location) {
        if (location.isEmpty())
            return;
        this.location = location;
    }

    public void setDetails(String details) {
        if (details.isEmpty())
            return;
        this.details = details;
    }

    public void setPrice(int price) {
        if (price < 0)
            return;
        this.price = price;
    }

    public void setArea(int area) {
        if (area < 0)
            return;
        this.area = area;
    }
}
