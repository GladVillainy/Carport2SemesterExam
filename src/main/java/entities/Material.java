package entities;

public class Material {
    private int id;
    private String name;
    private double price;
    private String description;
    private int length;

    //To create martial
    public Material(String name, double price, String description, int length) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.length = length;
    }

    public Material(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    //for the db to read
    public Material(int id, String name, double price, String description, int length) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.length = length;
    }

    public Material(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
