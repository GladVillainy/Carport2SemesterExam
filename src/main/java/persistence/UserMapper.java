package persistence;

import entities.Customer;
import entities.User;
import exceptions.DatabaseException;
import utility.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User login(String email, String password, ConnectionPool connectionPool) {

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
}
