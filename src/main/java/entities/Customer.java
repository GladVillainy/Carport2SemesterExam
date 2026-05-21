package entities;

public class Customer extends User{
    private String role;
    private String password;

    //To create - needs password to create

    public Customer(String address, String email, int phone, String role, String password) {
        super(address, email, phone);
        this.role = role;
        this.password = password;
    }

    //To read - does not need to show password
    public Customer(int id, String email, String address, int phone, String role) {
        super(id, email, address, phone);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
