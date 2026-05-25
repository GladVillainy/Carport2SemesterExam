package entities;

public class User {
    private int id;
    private String email;
    private String address;
    private int phone;

    //To create
    public User(String address, String email, int phone) {
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    //To read
    public User(int id, String email, String address, int phone) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.phone = phone;
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

    public int getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
