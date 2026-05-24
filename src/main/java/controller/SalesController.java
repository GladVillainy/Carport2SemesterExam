package controller;


import entities.Order;
import io.javalin.Javalin;
import io.javalin.http.Context;
import persistence.ConnectionPool;
import persistence.SalesMapper;
import validators.InputValidator;
import validators.RoleValidator;

import java.util.Comparator;
import java.util.List;

public class SalesController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        //Gatekeeps sales page, if user without permission somehow try to inter
        // it will return it to index

        app.get("/sales", ctx -> {
            if (!RoleValidator.hasRole(ctx, "sælger") && !RoleValidator.hasRole(ctx, "admin")) {
                ctx.redirect("/index");
                return;
            }
            showAllOrders(ctx, connectionPool);
        });

        app.post("/sales", ctx -> {
            if (!RoleValidator.hasRole(ctx, "sælger") && !RoleValidator.hasRole(ctx, "admin")) {
                ctx.redirect("/index");
                return;
            }
            showAllOrders(ctx, connectionPool);
        });

        app.get("/orderView", ctx -> {
            if (!RoleValidator.hasRole(ctx, "sælger") && !RoleValidator.hasRole(ctx, "admin")) {
                ctx.redirect("/index");
                return;
            }
            showAllOrders(ctx, connectionPool);
        });

        app.post("/orderView", ctx -> {
            if (!RoleValidator.hasRole(ctx, "sælger") && !RoleValidator.hasRole(ctx, "admin")) {
                ctx.redirect("/index");
                return;
            }
            showOneOrder(ctx, connectionPool);
        });

        app.post("/editPrice", ctx -> {
            if (!RoleValidator.hasRole(ctx, "sælger") && !RoleValidator.hasRole(ctx, "admin")) {
                ctx.redirect("/index");
                return;
            }
            editPriceByID(ctx, connectionPool);
        });

        app.post("/editStatus", ctx -> {
            if (!RoleValidator.hasRole(ctx, "sælger") && !RoleValidator.hasRole(ctx, "admin")) {
                ctx.redirect("/index");
                return;
            }
            editStatusByID(ctx, connectionPool);
        });

    }

    public static void showAllOrders(Context ctx, ConnectionPool connectionPool) {
        //Gets all orders
        List<Order> allOrders = SalesMapper.showAllOrdersInformation(connectionPool);

        //filter orders by pending, sort by id lowest < highest
        List<Order> allPending = allOrders.stream()
                .filter(u -> u.getStatus().equalsIgnoreCase("pending"))
                .sorted(Comparator.comparing(Order::getId))
                .toList();

        //filter orders by paid, sort by id lowest < highest
        List<Order> allPaid = allOrders.stream()
                .filter(u -> u.getStatus().equalsIgnoreCase("paid"))
                .sorted(Comparator.comparing(Order::getId))
                .toList();

        //filter orders by approved, sort by id lowest < highest
        List<Order> allApproved = allOrders.stream()
                .filter(u -> u.getStatus().equalsIgnoreCase("approved"))
                .sorted(Comparator.comparing(Order::getId))
                .toList();

        ctx.attribute("allPending", allPending);
        ctx.attribute("allPaid", allPaid);
        ctx.attribute("allApproved", allApproved);
        ctx.render("sales.html");
    }

    public static void showOneOrder(Context ctx, ConnectionPool connectionPool) {
        //Gets order_id from form
        int orderId = Integer.parseInt(ctx.formParam("order_id"));

        //Gets all orders and finds the one with the right id
        List<Order> allOrders = SalesMapper.showAllOrdersInformation(connectionPool);
        Order order = allOrders.stream()
                .filter(o -> o.getId() == orderId)
                .findFirst()
                .orElse(null);

        ctx.attribute("order", order);
        ctx.render("orderView.html");
    }

    public static void editPriceByID(Context ctx, ConnectionPool connectionPool) {
        //Verifyes if ID is empty or not a number, to prevent NumberFormatException
        String idInput = ctx.formParam("order_id");
        if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
            ctx.attribute("errorMessage", "Ugyldigt ordre ID");
            ctx.redirect("/orderView");
            return;
        }
        int id = Integer.parseInt(idInput);


        //Verifyes if price isnt empty and isnt a number, to prevent NumberFormatException
        String inputPrice = ctx.formParam("total_price");
        if (InputValidator.isItEmpty(inputPrice) || !InputValidator.isNumeric(inputPrice)) {
            ctx.attribute("errorMessage", "Pris skal udfyldes og være et tal");
            ctx.redirect("/orderView");
            return;
        }
        double price = Double.parseDouble(inputPrice);

        SalesMapper.editPrice(connectionPool, price, id);
        ctx.redirect("/orderView");
    }

    public static void editStatusByID(Context ctx, ConnectionPool connectionPool) {
        //Verifyes if ID is empty or not a number, to prevent NumberFormatException
        String idInput = ctx.formParam("order_id");
        if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
            ctx.attribute("errorMessage", "Ugyldigt ordre ID");
            ctx.redirect("/orderView");
            return;
        }
        int id = Integer.parseInt(idInput);

        String status = ctx.formParam("status");

        SalesMapper.editStatus(connectionPool, status, id);
        ctx.redirect("/orderView");
    }
}
