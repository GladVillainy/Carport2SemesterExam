package entities;

public class GuestCustomer extends User {

    //To read
    public GuestCustomer(String email, String adress) {
        super(email, adress);
    }

    //To create
    public GuestCustomer(int id, String email, String adress) {
        super(id, email, adress);
    }
}
