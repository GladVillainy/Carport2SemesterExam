package entities;

public class OrderLine {
    private int id;
    private int quantity;
    private Material material;
    private int material_length;

    //To create
    public OrderLine(int quantity, Material material) {
        this.quantity = quantity;
        this.material = material;
    }

    //To read
    public OrderLine(int id, int quantity, Material material) {
        this.id = id;
        this.quantity = quantity;
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "OrderLine{" + "\n" +
                "quantity=" + quantity +
                ", material=" + material.getName() +
                '}';
    }
}
