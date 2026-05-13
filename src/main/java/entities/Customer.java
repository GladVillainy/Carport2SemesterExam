package entities;

public class Customer extends User{
    private int phoneNumber;
    private String phone;
    private String role;


    //To create
    public Customer(String email, String adress, int phoneNumber, String phone, String role) {
        super(email, adress);
        this.phoneNumber = phoneNumber;
        this.phone = phone;
        this.role = "Customer";
    }

    //To read
    public Customer(int id, String email, String adress, int phoneNumber, String phone, String role) {
        super(id, email, adress);
        this.phoneNumber = phoneNumber;
        this.phone = phone;
        this.role = "Customer";
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
