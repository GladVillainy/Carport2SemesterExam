package entities;

public class User {
    private int id;
    private String email;
    private String address;

    //To create
    public User(String email, String adress) {
        this.email = email;
        this.address = adress;
    }

    //To read
    public User(int id, String email, String adress) {
        this.id = id;
        this.email = email;
        this.address = adress;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
