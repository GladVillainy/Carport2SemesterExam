package entities;

import java.util.ArrayList;

public class TotalOrderLines {
    private ArrayList<OrderLine> orderLines = new ArrayList<>();

    public TotalOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    @Override
    public String toString() {
        return "TotalOrderLines{" +
                "orderLines=" + orderLines +
                '}';
    }
}
