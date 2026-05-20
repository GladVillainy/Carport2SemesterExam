package persistence;

import entities.*;
import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesMapper {
    public static List<Order> showAllOrdersInformation(ConnectionPool connectionPool) {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM public.orders " +
                "JOIN public.order_line ON orders.order_id = order_line.order_id " +
                "JOIN public.material ON order_line.material_id = material.material_id " +
                "JOIN public.contact_information ON orders.contact_information_id = contact_information.contact_information_id " +
                "JOIN public.carport ON orders.order_id = carport.order_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //Order
                int orderId = rs.getInt("order_id");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");

                //User
                //gets user data from db
                String email = rs.getString("email");
                String address = rs.getString("address");
                int phone = rs.getInt("phone");
                //create user
                User customer = new User(address, email, phone);

                //Caport
                //Gets carport data from db
                int carportId = rs.getInt("carport_id");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                int height = rs.getInt("height");
                String roofType = rs.getString("roof_type");
                boolean shed = rs.getBoolean("shed");
                //creates caport object
                Carport carport = new Carport(carportId, width, length, height, roofType, shed);

                //Material
                //Gets material from db
                int materialId = rs.getInt("material_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                int matLength = rs.getInt("length");
                //creates material object
                Material material = new Material(materialId, name, price, description, matLength);

                //Orderline
                //Gets orderline data from db
                int orderLineId = rs.getInt("order_line_id");
                int quantity = rs.getInt("quantity");
                OrderLine orderLine = new OrderLine(orderLineId, quantity, material);

                //Creates list and adds orderline
                ArrayList<OrderLine> orderLines = new ArrayList<>();
                orderLines.add(orderLine);

                //Wraps orderlines in TotalOrderLines and adds to list
                TotalOrderLines totalOrderLines = new TotalOrderLines(orderLines);
                ArrayList<TotalOrderLines> totalOrderLinesList = new ArrayList<>();
                totalOrderLinesList.add(totalOrderLines);


                orders.add(new Order(carport, totalOrderLinesList, customer, orderId, totalPrice, status));
            }
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
