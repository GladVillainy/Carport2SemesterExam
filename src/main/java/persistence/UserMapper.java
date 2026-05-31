package persistence;

import entities.Customer;
import entities.GuestCustomer;
import entities.Order;
import entities.User;
import exceptions.DatabaseException;
import utility.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static Customer login(String email, String password, ConnectionPool connectionPool) {

        String sql = "SELECT * FROM public.users WHERE email = ?";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("user_id");
                String hashedPw = rs.getString("password");
                String address = rs.getString("address");
                int phone = rs.getInt("phone");
                String role = rs.getString("role");

                //checks if the password we get from db is the same as the input
                if(!PasswordUtil.checkPassword(password, hashedPw)){
                    throw new DatabaseException("Fejl i login. Prøv igen");
                }

                return new Customer(id, email, address, phone, role);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch(SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
    }

    public static void createUser(String email, String password, String address, int phone, String role, ConnectionPool connectionPool) {

        String sql = "INSERT INTO public.users (email, password, address, phone, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, address);
            ps.setInt(4, phone);
            ps.setString(5, role);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "E-mail findes allerede. Vælg en anden";
            }
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
    }

    public static int getIdByEmail(String email, ConnectionPool connectionPool) {

        String sql = "SELECT user_id, email FROM public.users WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                return userId;
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
        return -1;
    }

    public static List<Order> getOrdersByUserId(int userId, ConnectionPool connectionPool) {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT orders.order_id, orders.total_price, orders.status " +
                "FROM public.orders " +
                "JOIN public.contact_information ON orders.contact_information_id = contact_information.contact_information_id " +
                "WHERE contact_information.user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");

                orders.add(new Order(orderId, totalPrice, status));
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
        return orders;
    }
}
