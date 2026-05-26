package persistence;

import entities.*;
import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesMapper {
    public static List<Order> showAllOrdersInformation(ConnectionPool connectionPool) {
        List<Order> orders;

        String sql = "SELECT * FROM public.orders " +
                "JOIN public.order_line ON orders.order_id = order_line.order_id " +
                "JOIN public.material ON order_line.material_id = material.material_id " +
                "JOIN public.contact_information ON orders.contact_information_id = contact_information.contact_information_id " +
                "JOIN public.carport ON orders.order_id = carport.order_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            Map<Integer, Order> orderMap = new HashMap<>();

            while (rs.next()) {

                int orderId = rs.getInt("order_id");

                Order order = orderMap.get(orderId);

                if (order == null) {

                    // User
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    int phone = rs.getInt("phone");

                    User customer = new User(address, email, phone);

                    // Carport
                    int carportId = rs.getInt("carport_id");
                    int width = rs.getInt("width");
                    int length = rs.getInt("length");
                    int height = rs.getInt("height");
                    String roofType = rs.getString("roof_type");
                    boolean shed = rs.getBoolean("shed");

                    Carport carport = new Carport(carportId, width, length, height, roofType, shed);

                    // Order info
                    double totalPrice = rs.getDouble("total_price");
                    String status = rs.getString("status");

                    // Opret tom liste
                    ArrayList<OrderLine> orderLines = new ArrayList<>();
                    TotalOrderLines totalOrderLines = new TotalOrderLines(orderLines);

                    ArrayList<TotalOrderLines> totalOrderLinesList = new ArrayList<>();
                    totalOrderLinesList.add(totalOrderLines);

                    order = new Order(
                            carport,
                            totalOrderLinesList,
                            customer,
                            orderId,
                            totalPrice,
                            status
                    );

                    orderMap.put(orderId, order);
                }

                // Material
                int materialId = rs.getInt("material_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                int matLength = rs.getInt("length");

                Material material = new Material(materialId, name, price, description, matLength);

                // OrderLine
                int orderLineId = rs.getInt("order_line_id");
                int quantity = rs.getInt("quantity");
                int materialLength = rs.getInt("material_length");

                OrderLine orderLine = new OrderLine(orderLineId, quantity, materialLength, material);

                // Tilføj orderline til eksisterende order
                order.getOrderLines()
                        .get(0)
                        .getOrderLines()
                        .add(orderLine);
            }

            orders = new ArrayList<>(orderMap.values());
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
        return orders;
    }

public static void editPrice(ConnectionPool connectionPool, double price, int id){
    String sql = "UPDATE public.orders SET total_price = ? WHERE order_id = ?";


    try (Connection connection = connectionPool.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setDouble(1, price);
        ps.setInt(2, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected != 1) {
            throw new DatabaseException("Fejl opstået ved ændring af status");
        }
    } catch (SQLException e) {
        String msg = "Der er sket en fejl. Prøv igen";
        throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
    }
}

    public static void editStatus(ConnectionPool connectionPool, String status, int id){
        String sql = "UPDATE public.orders SET status = ? WHERE order_id = ?";


        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl opstået ved ændring af status");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException("DB fejl: " + e.getMessage() + " (" + msg + ")");
        }
    }

}
