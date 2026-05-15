package controller;

import entities.Customer;
import io.javalin.Javalin;
import persistence.AdminMapper;
import persistence.ConnectionPool;
import validators.RoleValidator;
import io.javalin.http.Context;

import java.util.Comparator;
import java.util.List;


public class AdminController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        //Gatekeeps admin page, if user without permission somehow try to inter
        // it will return it to index
        app.get("/adminUserCRUD", ctx -> {
            if (!RoleValidator.hasRole(ctx, "admin")) {
                ctx.redirect("/index");
                return;
            }
            ctx.render("adminUserCRUD.html");
        });
        app.post("/createUser", ctx -> createUser(ctx, connectionPool));
        app.post("/deleteUserByID", ctx -> deleteUserByID(ctx, connectionPool));
        app.post("/editUserByID", ctx -> editUserByID(ctx, connectionPool));


    }

    //TODO check if mikkel already made this
    public static void createUser(Context ctx, ConnectionPool connectionPool) {

    }

    public static void deleteUserByID(Context ctx, ConnectionPool connectionPool) {
        if (RoleValidator.hasRole(ctx, "admin")) {
            //gets attribute
            int id = Integer.parseInt(ctx.formParam("user_id"));

            //sends it to
            AdminMapper.deleteCustomerByID(id, connectionPool);

            //Refreshes the admin page
            ctx.redirect("/adminUserCRUD");
        } else {
            //redirect to frontpage, if role is not "admin"
            ctx.redirect("/index");
        }
    }

    public static void editUserByID(Context ctx, ConnectionPool connectionPool) {
        //Validate if user is an admin
        if (RoleValidator.hasRole(ctx, "admin")) {
            //gets attribute
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");
            String address = ctx.formParam("address");
            String role = ctx.formParam("role");
            String phoneNumber = ctx.formParam("phoneNumber");
            int id = Integer.parseInt(ctx.formParam("user_id"));

            //sends the new attributes to be edited
            AdminMapper.editCustomerByID(id, email, password, address, phoneNumber, role, connectionPool);

            //Refreshes the admin page
            ctx.redirect("/adminUserCRUD");
        } else {
            //redirect to frontpage, if role is not "admin"
            ctx.redirect("/index");
        }
    }

    public static void showAllCustomers(Context ctx, ConnectionPool connectionPool) {
        if (RoleValidator.hasRole(ctx, "admin")) {
            List<Customer> allCustomers = AdminMapper.getAllRegistered(connectionPool);
            //Sorts so we only sees customer, not admin or salesperson.
            allCustomers = allCustomers.stream()
                    .filter(u -> u.getRole().equalsIgnoreCase("customer"))
                    .sorted(Comparator.comparing(Customer::getEmail))
                    .toList();
            ctx.sessionAttribute("allCustomer", allCustomers);
            ctx.redirect("/adminUserCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

    public static void showAllStaff(Context ctx, ConnectionPool connectionPool) {
        if (RoleValidator.hasRole(ctx, "admin")) {
            List<Customer> allCustomers = AdminMapper.getAllRegistered(connectionPool);
            //Sorts so we only sees customer, not admin or salesperson.
            allCustomers = allCustomers.stream()
                    .filter(u -> u.getRole().equalsIgnoreCase("admin")
                            || u.getRole().equalsIgnoreCase("sales"))
                    .sorted(Comparator.comparing(Customer::getEmail))
                    .toList();
            ctx.sessionAttribute("allCustomer", allCustomers);
            ctx.redirect("/adminUserCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

/// Material ///


}
