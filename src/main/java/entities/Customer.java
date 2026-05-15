package entities;

public class Customer extends User{
    private int phoneNumber;
    private String role;


    //To create
    public Customer(String address, String email, int phoneNumber, String role) {
        super(address, email);
        this.phoneNumber = phoneNumber;
        this.role = "Customer";
    }

    //To read
    public Customer(int id, String email, String address, int phoneNumber, String role) {
        super(id, email, address);
        this.phoneNumber = phoneNumber;
        this.role = "Customer";
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
