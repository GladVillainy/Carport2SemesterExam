package entities;

public class User {
    private int id;
    private String email;
    private String address;

    //To create
    public User(String address, String email) {
        this.address = address;
        this.email = email;
    }

    //To read
    public User(int id, String email, String address) {
        this.id = id;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
}
