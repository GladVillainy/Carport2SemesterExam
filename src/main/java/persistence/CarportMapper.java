package persistence;

import entities.Carport;
import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarportMapper {

    public static Carport createCarport(int order_id, int length, int width, int height, String roofType, boolean shed, ConnectionPool connectionPool) {

        String sql = "INSERT INTO public.carport (order_id, length, width, height, roof_type, shed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, order_id);
            ps.setInt(2, length);
            ps.setInt(3, width);
            ps.setInt(4, height);
            ps.setString(5, roofType);
            ps.setBoolean(6, shed);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny carport");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
        return new Carport(length, width, height, roofType, shed);
    }
}
