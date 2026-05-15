package persistence;

import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminMapper {
    public static void createUser(String email, String password, String address,
                                  int phoneNumber, String role, ConnectionPool connectionPool) {

        String sql = "INSERT INTO public.users (email, address, phone, password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, address);
            ps.setInt(3, phoneNumber);
            ps.setString(4, password);
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

    public static void editUserByID(int id, String email, String password, String address,
                                    String phoneNumber, String role, ConnectionPool connectionPool) {
        // uses coalesce + nullif to revert back if felt is null else update with new data.
        String sql = "UPDATE public.users SET " +
                "email = COALESCE(NULLIF(?, ''), email), " +
                "address = COALESCE(NULLIF(?, ''), address), " +
                "phone = COALESCE(NULLIF(?, ''), phone), " +
                "password = COALESCE(NULLIF(?, ''), password), " +
                "role = COALESCE(NULLIF(?, ''), role) " +
                "WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, address);
            ps.setString(3, phoneNumber);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.setInt(6, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl opstået ved ændring af brugeren");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
    }

    public static void deleteUserByID(int id, ConnectionPool connectionPool) {
        String sql = "DELETE FROM public.users WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl opstået ved sletningen af brugeren");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
    }


}
