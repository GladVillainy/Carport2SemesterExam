package entities;

public class GuestCustomer extends User {

    //To read

    public GuestCustomer(String address, String email, int phone) {
        super(address, email, phone);
    }


    //To create

    public GuestCustomer(int id, String email, String address, int phone) {
        super(id, email, address, phone);
    }
}
