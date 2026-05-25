package persistence;

import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper {

    public static int createContactId(int userId, String email, String phone, String address, ConnectionPool connectionPool) {
        String sql = "INSERT INTO contact_information (user_id, email, phone, address) VALUES (?, ?, ?, ?) RETURNING contact_information_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("contact_information_id");
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }

        return -1;
    }
}
