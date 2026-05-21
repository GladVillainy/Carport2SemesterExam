package controller;

import entities.Customer;
import entities.Material;
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
        app.post("/deleteUserByID", ctx -> deleteUserByID(ctx, connectionPool));
        app.post("/editUserByID", ctx -> editUserByID(ctx, connectionPool));
        app.post("/showAllCustomers", ctx -> showAllCustomers(ctx, connectionPool));
        app.post("/showAllStaff", ctx -> showAllStaff(ctx, connectionPool));

        //Materiale //
        app.post("/createMaterial", ctx -> createMaterial(ctx, connectionPool));
        app.post("/deleteMaterialByID", ctx -> deleteMaterialByID(ctx, connectionPool));
        app.post("/editMaterialByID", ctx -> editMaterialByID(ctx, connectionPool));
        app.post("/seeAllMaterial", ctx -> seeAllMaterial(ctx, connectionPool));

    }

    //TODO check if mikkel already made this
    public static void createUser(Context ctx, ConnectionPool connectionPool) {

    }

    public static void deleteUserByID(Context ctx, ConnectionPool connectionPool) {
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Verifyes if ID is empty or not a number, to prevent NumberFormatException
            String idInput = ctx.formParam("user_id");
            if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
                ctx.attribute("errorMessage", "Ugyldigt bruger ID");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            //Parses ID and sends to mapper for delettion
            int id = Integer.parseInt(idInput);
            AdminMapper.deleteCustomerByID(id, connectionPool);
            ctx.redirect("/adminUserCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

    public static void editUserByID(Context ctx, ConnectionPool connectionPool) {
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Verifyes if ID is empty or not a number, to prevent NumberFormatException
            String idInput = ctx.formParam("user_id");
            if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
                ctx.attribute("errorMessage", "Ugyldigt bruger ID");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            //Verifyes if phone isnt empty and isnt a number, to prevent NumberFormatException
            String phoneNumber = ctx.formParam("phoneNumber");
            if (!InputValidator.isItEmpty(phoneNumber) && !InputValidator.isNumeric(phoneNumber)) {
                ctx.attribute("errorMessage", "Telefonnummer skal være et tal");
                ctx.redirect("/adminUserCRUD");
                return;
            }

            //Gets remaining data and sends to mapper for editing
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
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Gets all users and filters so we only see customers
            List<Customer> allCustomers = AdminMapper.getAllRegistered(connectionPool);
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
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Gets all users and filters so we only see admins and sales
            List<Customer> allCustomers = AdminMapper.getAllRegistered(connectionPool);
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

    //Materiale //

    public static void createMaterial(Context ctx, ConnectionPool connectionPool) {
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Gets data
            String name = ctx.formParam("name");
            String description = ctx.formParam("description");

            //Verifyes if price is either empty or isnt a number, to prevent NumberFormatException.
            String priceInput = ctx.formParam("price");
            if (InputValidator.isItEmpty(priceInput)) {
                ctx.attribute("errorMessage", "Pris skal udfyldes, og være et tal");
                ctx.render("adminMaterialCRUD.html");
                return;
            } else if (!InputValidator.isNumeric(priceInput)) {
                ctx.attribute("errorMessage", "Pris skal være et tal");
                ctx.render("adminMaterialCRUD.html");
                return;
            }

            //Verifyes if length isnt empty and isnt a number, to prevent NumberFormatException.
            String lengthInput = ctx.formParam("length");
            if (!InputValidator.isItEmpty(lengthInput) && !InputValidator.isNumeric(lengthInput)) {
                ctx.attribute("errorMessage", "Længde skal være et tal");
                ctx.render("adminMaterialCRUD.html");
                return;
            }

            //Sends data to mapper for creation
            AdminMapper.createMaterial(name, priceInput, description, lengthInput, connectionPool);
            ctx.redirect("/adminMaterialCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

    public static void editMaterialByID(Context ctx, ConnectionPool connectionPool) {
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Verifyes if material ID is empty or not a number, to prevent NumberFormatException
            String idInput = ctx.formParam("material_id");
            if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
                ctx.attribute("errorMessage", "Ugyldigt materiale ID");
                ctx.redirect("/adminMaterialCRUD");
                return;
            }

            //Verifyes if price isnt empty and isnt a number, to prevent NumberFormatException
            String priceInput = ctx.formParam("price");
            if (!InputValidator.isItEmpty(priceInput) && !InputValidator.isNumeric(priceInput)) {
                ctx.attribute("errorMessage", "Pris skal være et tal");
                ctx.redirect("/adminMaterialCRUD");
                return;
            }

            //Verifyes if length isnt empty and isnt a number, to prevent NumberFormatException
            String lengthInput = ctx.formParam("length");
            if (!InputValidator.isItEmpty(lengthInput) && !InputValidator.isNumeric(lengthInput)) {
                ctx.attribute("errorMessage", "Længde skal være et tal");
                ctx.redirect("/adminMaterialCRUD");
                return;
            }

            //Gets remaining data and sends to mapper for editing
            String name = ctx.formParam("name");
            String description = ctx.formParam("description");
            int id = Integer.parseInt(idInput);

            AdminMapper.editMaterialByID(name, priceInput, description, lengthInput, id, connectionPool);
            ctx.redirect("/adminMaterialCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

    public static void deleteMaterialByID(Context ctx, ConnectionPool connectionPool) {
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Verifyes if material ID is empty or not a number, to prevent NumberFormatException
            String idInput = ctx.formParam("material_id");
            if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
                ctx.attribute("errorMessage", "Ugyldigt materiale ID");
                ctx.redirect("/adminMaterialCRUD");
                return;
            }

            //Parses ID and sends to mapper for delettion
            int id = Integer.parseInt(idInput);
            AdminMapper.deleteMaterialByID(id, connectionPool);
            ctx.redirect("/adminMaterialCRUD");
        } else {
            ctx.redirect("/index");
        }
    }

    public static void seeAllMaterial(Context ctx, ConnectionPool connectionPool) {
        //Checks if user is admin
        if (RoleValidator.hasRole(ctx, "admin")) {

            //Gets all materials and sends them to session
            List<Material> allMaterial = AdminMapper.seeAllMaterial(connectionPool);
            ctx.sessionAttribute("allMaterial", allMaterial);
            ctx.redirect("/adminUserMaterial");
        } else {
            ctx.redirect("/index");
        }
    }
}
