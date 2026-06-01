package persistence;

import entities.Carport;
import entities.Order;
import exceptions.DatabaseException;

import java.sql.*;

public class CarportMapper {

    public static Carport createCarport(int order_id, int length, int width, int height, String roofType, boolean shed, ConnectionPool connectionPool) {

        String sql = "INSERT INTO public.carport (order_id, length, width, height, roof_type, shed) VALUES (?, ?, ?, ?, ?, ?) RETURNING carport_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, order_id);
            ps.setInt(2, length);
            ps.setInt(3, width);
            ps.setInt(4, height);
            ps.setString(5, roofType);
            ps.setBoolean(6, shed);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int carportId = rs.getInt("carport_id");
                return new Carport(carportId, width, length, height, roofType, shed);
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
        return null;
    }

    public static Carport getCarportById(int id, ConnectionPool connectionPool) {

        Carport c = null;

        String sql = "SELECT * FROM public.carport WHERE carport_id = ?";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    int length = rs.getInt("length");
                    int width = rs.getInt("width");
                    int height = rs.getInt("height");
                    String roofType = rs.getString("roof_type");
                    boolean shed = rs.getBoolean("shed");
                    c = new Carport(id,  width, length, height, roofType, shed);
                }
            }
        } catch(SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
        return c;
    }
}
