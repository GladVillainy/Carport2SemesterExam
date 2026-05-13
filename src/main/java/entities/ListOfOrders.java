package entities;

import java.util.ArrayList;

public class ListOfOrders {
    private ArrayList<Order> listOfOrders = new ArrayList<>();

    public ListOfOrders(ArrayList<Order> listOfOrders){
        this.listOfOrders = listOfOrders;
    }

    public ArrayList<Order> getListOfOrders() {
        return listOfOrders;
    }
}
