package entities;

public class GuestCustomer extends User {

    //To read
    public GuestCustomer(String address, String email) {
        super(address, email);
    }

    //To create
    public GuestCustomer(int id, String email, String address) {
        super(id, email, address);
    }
}
