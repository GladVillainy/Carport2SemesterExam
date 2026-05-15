package controller;

import entities.Customer;
import io.javalin.Javalin;
import persistence.AdminMapper;
import persistence.ConnectionPool;
import validators.InputValidator;
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
        app.post("/showAllCustomers", ctx -> showAllCustomers(ctx, connectionPool));
        app.post("/showAllStaff", ctx -> showAllStaff(ctx, connectionPool));


    }

    //TODO check if mikkel already made this
    public static void createUser(Context ctx, ConnectionPool connectionPool) {

    }

    public static void deleteUserByID(Context ctx, ConnectionPool connectionPool) {
        if (RoleValidator.hasRole(ctx, "admin")) {
            String idInput = ctx.formParam("user_id");
            if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
                ctx.attribute("errorMessage", "Ugyldigt bruger ID");
                ctx.redirect("/adminUserCRUD");
                return;
            }
            int id = Integer.parseInt(idInput);
            AdminMapper.deleteCustomerByID(id, connectionPool);
            ctx.redirect("/adminUserCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

    public static void editUserByID(Context ctx, ConnectionPool connectionPool) {
        if (RoleValidator.hasRole(ctx, "admin")) {
            String idInput = ctx.formParam("user_id");
            if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
                ctx.attribute("errorMessage", "Ugyldigt bruger ID");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            String phoneNumber = ctx.formParam("phoneNumber");
            if (!InputValidator.isItEmpty(phoneNumber) && !InputValidator.isNumeric(phoneNumber)) {
                ctx.attribute("errorMessage", "Telefonnummer skal være et tal");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            String email = ctx.formParam("email");
            String password = ctx.formParam("password");
            String address = ctx.formParam("address");
            String role = ctx.formParam("role");
            int id = Integer.parseInt(idInput);

            AdminMapper.editCustomerByID(id, email, password, address, phoneNumber, role, connectionPool);
            ctx.redirect("/adminUserCRUD");
        } else {
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

    public static void createMaterial(Context ctx) {
        if (RoleValidator.hasRole(ctx, "admin")) {
            //Get data
            String name = ctx.formParam("name");
            String description = ctx.formParam("description");

            String priceInput = ctx.formParam("price");
            if (InputValidator.isItEmpty(priceInput)) {
                ctx.attribute("errorMessage", "Pris skal udfyldes");
                ctx.render("adminUserCRUD.html");
                return;
            }
            if (!InputValidator.isNumeric(priceInput)) {
                ctx.attribute("errorMessage", "Pris skal være et tal");
                ctx.render("adminUserCRUD.html");
                return;
            }
            double price = Double.parseDouble(priceInput);

            String lengthInput = ctx.formParam("length");
            int length = 0;
            if (!InputValidator.isItEmpty(lengthInput)) {
                if (!InputValidator.isNumeric(lengthInput)) {
                    ctx.attribute("errorMessage", "Længde skal være et tal");
                    ctx.render("adminUserCRUD.html");
                    return;
                }
                length = Integer.parseInt(lengthInput);
            }
        } else {
            ctx.redirect("/index");
        }

    }

    public static void editMaterialByID(Context ctx, ConnectionPool connectionPool) {
        if (RoleValidator.hasRole(ctx, "admin")) {
            String idInput = ctx.formParam("material_id");
            if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
                ctx.attribute("errorMessage", "Ugyldigt materiale ID");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            String priceInput = ctx.formParam("price");
            if (!InputValidator.isItEmpty(priceInput) && !InputValidator.isNumeric(priceInput)) {
                ctx.attribute("errorMessage", "Pris skal være et tal");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            String lengthInput = ctx.formParam("length");
            if (!InputValidator.isItEmpty(lengthInput) && !InputValidator.isNumeric(lengthInput)) {
                ctx.attribute("errorMessage", "Længde skal være et tal");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            String name = ctx.formParam("name");
            String description = ctx.formParam("description");
            int id = Integer.parseInt(idInput);

            AdminMapper.editMaterialByID(name, priceInput, description, lengthInput, id, connectionPool);
            ctx.redirect("/adminUserCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

    public static void deleteMaterialByID() {

    }

    public static void seeAllMaterial() {

    }

}
