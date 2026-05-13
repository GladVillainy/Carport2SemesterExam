package entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private ArrayList<Carport> carports = new ArrayList<>();
    private ArrayList<TotalOrderLines> orderLines = new ArrayList<>();

    private int id;
    private double totalPrice;
    private String status;

    //To create
    public Order(ArrayList<Carport> carports, ArrayList<TotalOrderLines> orderLines,
                 double totalPrice, String status) {
        this.carports = carports;
        this.orderLines = orderLines;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    //To read
    public Order(ArrayList<Carport> carports, ArrayList<TotalOrderLines> orderLines,
                 int id, double totalPrice, String status) {
        this.carports = carports;
        this.orderLines = orderLines;
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public ArrayList<Carport> getCarports() {
        return carports;
    }

    public ArrayList<TotalOrderLines> getOrderLines() {
        return orderLines;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
}
