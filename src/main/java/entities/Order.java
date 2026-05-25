package entities;



import java.sql.Timestamp;
import java.util.ArrayList;

public class Order {
    private Carport carport;
    private ArrayList<TotalOrderLines> orderLines = new ArrayList<>();
    private User customer;

    private int id;
    private double totalPrice;
    private String status;

    //lav en ny order uden for databasen som test uden den er afhængig af Carport, orderLines, osv osv
    public Order(int orderId, double totalPrice, String  status) {
        this.id = orderId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    //To create
    public Order(Carport carport, ArrayList<TotalOrderLines> orderLines,
                 User customer, double totalPrice, String status) {
        this.carport = carport;
        this.orderLines = orderLines;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    //To read
    public Order(Carport carport, ArrayList<TotalOrderLines> orderLines,
                 User customer, int id, double totalPrice, String status) {
        this.carport = carport;
        this.orderLines = orderLines;
        this.customer = customer;
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public ArrayList<TotalOrderLines> getOrderLines() { return orderLines; }
    public User getCustomer() { return customer; }
    public int getId() { return id; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
    public Carport getCarport() {
        return carport;
    }
}