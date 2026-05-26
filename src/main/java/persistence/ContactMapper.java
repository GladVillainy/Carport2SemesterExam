package persistence;

import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper {

    public static int createContactId(int userId, String email, String phone, String address, ConnectionPool connectionPool) {

        try (Connection connection = connectionPool.getConnection()) {

            //prøver at se om der er et eksisterende kontaktId
            String selectSql = "SELECT contact_information_id FROM contact_information WHERE user_id = ?";

            try (PreparedStatement checkPs = connection.prepareStatement(selectSql)) {
                checkPs.setInt(1, userId);

                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {
                    return rs.getInt("contact_information_id");
                }
            }

            //hvis ikke overstående return bliver udført, laver vi et nyt Id
            String insertSql = "INSERT INTO contact_information (user_id, email, phone, address) VALUES (?, ?, ?, ?) RETURNING contact_information_id";

            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setInt(1, userId);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, address);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getInt("contact_information_id");
                }
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }

        return -1;
    }
}
