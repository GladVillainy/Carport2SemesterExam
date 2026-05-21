package persistence;

import entities.Customer;
import entities.Material;
import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminMapper {
    public static void createCustomer(String email, String password, String address,
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

    public static void editCustomerByID(int id, String email, String password, String address,
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

    public static void deleteCustomerByID(int id, ConnectionPool connectionPool) {
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

    public static List<Customer> getAllRegistered(ConnectionPool connectionPool) {

        List<Customer> registeredList = new ArrayList<>();

        String sql = "SELECT * FROM public.users";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int phone = Integer.parseInt(rs.getString("phone"));
                String role = rs.getString("role");

                registeredList.add(new Customer(id, email ,address, phone, role));
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
        return registeredList;
    }


    /// Material ///

    public static void createMaterial(String name, String price, String description, String length, ConnectionPool connectionPool) {
        String sql = "INSERT INTO public.material (name, price, description, length) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, price);
            ps.setString(3, description);
            ps.setString(4, length);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af nyt materiale");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Materialet findes allerede. Vælg en anden";
            }
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }

    }

    public static void editMaterialByID(String name, String price, String description, String length, int id, ConnectionPool connectionPool) {
        // uses coalesce + nullif to revert back if felt is null else update with new data.
        String sql = "UPDATE public.material SET " +
                "name = COALESCE(NULLIF(?, ''), name), " +
                "price = COALESCE(NULLIF(?, ''), price), " +
                "description = COALESCE(NULLIF(?, ''), description), " +
                "length = COALESCE(NULLIF(?, ''), length) " +
                "WHERE material_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, price);
            ps.setString(3, description);
            ps.setString(4, length);
            ps.setInt(5, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl opstået ved ændring af materiale");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }

    }

    public static void deleteMaterialByID(int id, ConnectionPool connectionPool) {
        String sql = "DELETE FROM public.material WHERE material_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl opstået ved sletningen af materialet");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }

    }

    public static List<Material> seeAllMaterial(ConnectionPool connectionPool) {

        List<Material> materialsList = new ArrayList<>();

        String sql = "SELECT * FROM public.material";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("material_id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                int length = rs.getInt("length");
                materialsList.add(new Material(id, name, price, description, length));
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
        return materialsList;
    }

}
