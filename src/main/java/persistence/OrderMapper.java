package persistence;

import entities.Order;
import exceptions.DatabaseException;

import java.sql.*;

public class OrderMapper {

    public static Order createOrderId(int contactInformationId, ConnectionPool connectionPool){
        String sql = "INSERT INTO orders (contact_information_id, status) VALUES (?, 'approved') RETURNING order_id, contact_information_id, total_price, status";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, contactInformationId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int orderId = rs.getInt("order_id");
                int contactId = rs.getInt("contact_information_id");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");

                return new Order(orderId, totalPrice, status);
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }

        return null;
    }
}
