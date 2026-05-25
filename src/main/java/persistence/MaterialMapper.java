package persistence;

import entities.Material;
import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialMapper {

    public static Material getMaterialByName(String name, ConnectionPool connectionPool) {

        String sql = "SELECT * FROM material WHERE name = ? LIMIT 1";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("material_id");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                int length = rs.getInt("length");

                return new Material(id, name, price, description, length);
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }

        return null;
    }

}
