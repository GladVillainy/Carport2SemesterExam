package persistence;

import entities.OrderLine;
import entities.TotalOrderLines;
import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderLinesMapper {

    public static TotalOrderLines createAllOrderLines(int orderId, TotalOrderLines totalOrderLines, ConnectionPool connectionPool){
        for (OrderLine orderLine : totalOrderLines.getOrderLines()) {
            int materialId = orderLine.getMaterial().getId();
            int quantity = orderLine.getQuantity();
            createOrderLine(orderId, materialId, quantity, connectionPool);
        }
        return totalOrderLines;
    }

    public static void createOrderLine(int orderId, int materialId, int quantity, ConnectionPool connectionPool) {

        String sql = "INSERT INTO order_line (order_id, material_id, quantity) VALUES (?, ?, ?) RETURNING order_line_id ";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, materialId);
            ps.setInt(3, quantity);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return;
            }

        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
        throw new DatabaseException("DB fejl: kunne ikke oprette order_line");
    }
}
