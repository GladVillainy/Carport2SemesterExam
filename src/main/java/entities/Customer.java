package entities;

public class Customer extends User{
    private int phoneNumber;
    private String role;
    private String password;

    //To create - needs password to create
    public Customer(String address, String email, int phoneNumber, String role, String password) {
        super(address, email);
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = "Customer";
    }

    //To read - does not need to show password
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
