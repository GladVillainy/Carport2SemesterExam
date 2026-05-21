package controller;

import entities.Customer;
import entities.Material;
import entities.Order;
import entities.User;
import exceptions.DatabaseException;
import io.javalin.Javalin;
import persistence.AdminMapper;
import persistence.ConnectionPool;
import persistence.SalesMapper;
import persistence.UserMapper;
import validators.InputValidator;
import io.javalin.http.Context;

import java.util.Comparator;
import java.util.List;


public class AdminController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/adminUserCRUD", ctx -> showAllRegistered(ctx, connectionPool));
        app.get("/adminMaterialCRUD", ctx -> seeAllMaterial(ctx, connectionPool));

        //User //
        app.post("/createUserAdmin", ctx -> createUserAdmin(ctx, connectionPool));
        app.post("/deleteUserByID", ctx -> deleteUserByID(ctx, connectionPool));

        app.post("/editUserView", ctx -> showOneUser(ctx, connectionPool));
        app.get("/editUserView", ctx -> showOneUser(ctx, connectionPool));

        app.post("/editUserByID", ctx -> editUserByID(ctx, connectionPool));
        //Materiale //
        app.post("/createMaterial", ctx -> createMaterial(ctx, connectionPool));
        app.post("/deleteMaterialByID", ctx -> deleteMaterialByID(ctx, connectionPool));
        app.post("/editMaterialByID", ctx -> editMaterialByID(ctx, connectionPool));
        app.post("/seeAllMaterial", ctx -> seeAllMaterial(ctx, connectionPool));
    }

    public static void createUserAdmin(Context ctx, ConnectionPool connectionPool) {
        //gets userinput
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String address = ctx.formParam("address");
        String role = ctx.formParam("role");

        //Verifyes if phone isnt empty and isnt a number, to prevent NumberFormatException
        String phoneInput = ctx.formParam("phone");
        if (!InputValidator.isItEmpty(phoneInput) && !InputValidator.isNumeric(phoneInput)) {
            ctx.attribute("msg", "Telefonnummer skal være et tal");
            ctx.render("createUser.html");
            return;
        }
        int phone = Integer.parseInt(phoneInput);

        //checks on user typed the right password
        String password = "";
        if (password1.equals(password2)) {
            password = password1;
            //creates user in DB
            try {
                UserMapper.createUser(email, password, address, phone, role, connectionPool);
                String createConfirm = email + " er nu blevet oprettet som bruger!";
                ctx.attribute("msg", createConfirm);
                //directs the user to the frontpage
                ctx.redirect("adminUserCRUD");
            } catch (DatabaseException e) {
                ctx.attribute("msg", e.getMessage());
                ctx.render("createUser.html");
            }
        } else {
            ctx.attribute("msg", "Your passwords do not match. Please try again");
            ctx.render("createUser.html");
        }
    }

    public static void deleteUserByID(Context ctx, ConnectionPool connectionPool) {
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
    }

    public static void showOneUser(Context ctx, ConnectionPool connectionPool) {
        //Gets user_id from form
        int userId = Integer.parseInt(ctx.formParam("user_id"));

        //Gets all users and finds the one with the right id
        List<Customer> customers = AdminMapper.getAllRegistered(connectionPool);
        Customer customer = customers.stream()
                .filter(o -> o.getId() == userId)
                .findFirst()
                .orElse(null);

        ctx.attribute("user", customer);
        ctx.render("editUserView.html");
    }

    public static void editUserByID(Context ctx, ConnectionPool connectionPool) {
        int userId = Integer.parseInt(ctx.formParam("user_id"));

        //Verifies if phone isnt empty and isnt a number, to prevent NumberFormatException
        String phoneNumber = ctx.formParam("phone");
        if (!InputValidator.isItEmpty(phoneNumber) && !InputValidator.isNumeric(phoneNumber)) {
            ctx.attribute("errorMessage", "Telefonnummer skal være et tal");
            ctx.redirect("/editUserView");
            return;
        }

        //Gets remaining data and sends to mapper for editing
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String address = ctx.formParam("address");
        String role = ctx.formParam("role");

        AdminMapper.editCustomerByID(userId, email, password, address, phoneNumber, role, connectionPool);
        ctx.redirect("/adminUserCRUD");
    }

    public static void showAllRegistered(Context ctx, ConnectionPool connectionPool) {
        List<Customer> allCustomers = AdminMapper.getAllRegistered(connectionPool);
        allCustomers = allCustomers.stream()
                .filter(u -> u.getRole().equalsIgnoreCase("kunde"))
                .sorted(Comparator.comparing(Customer::getEmail))
                .toList();
        ctx.attribute("allCustomer", allCustomers);

        List<Customer> allStaff = AdminMapper.getAllRegistered(connectionPool);
        allStaff = allStaff.stream()
                .filter(u -> u.getRole().equalsIgnoreCase("admin")
                        || u.getRole().equalsIgnoreCase("sælger"))
                .sorted(Comparator.comparing(Customer::getEmail))
                .toList();
        ctx.attribute("allStaff", allStaff);

        ctx.render("adminUserCRUD.html");
    }

    //Materiale //

    public static void createMaterial(Context ctx, ConnectionPool connectionPool) {
        //Gets data
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");

        //Verifies if price is either empty or isnt a number, to prevent NumberFormatException.
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

        //Verifies if length isnt empty and isnt a number, to prevent NumberFormatException.
        String lengthInput = ctx.formParam("length");
        if (!InputValidator.isItEmpty(lengthInput) && !InputValidator.isNumeric(lengthInput)) {
            ctx.attribute("errorMessage", "Længde skal være et tal");
            ctx.render("adminMaterialCRUD.html");
            return;
        }

        //Sends data to mapper for creation
        AdminMapper.createMaterial(name, priceInput, description, lengthInput, connectionPool);
        ctx.redirect("/adminMaterialCRUD");
    }

    public static void editMaterialByID(Context ctx, ConnectionPool connectionPool) {
        //Verifies if material ID is empty or not a number, to prevent NumberFormatException
        String idInput = ctx.formParam("material_id");
        if (InputValidator.isItEmpty(idInput) || !InputValidator.isNumeric(idInput)) {
            ctx.attribute("errorMessage", "Ugyldigt materiale ID");
            ctx.redirect("/adminMaterialCRUD");
            return;
        }

        //Verifies if price isnt empty and isnt a number, to prevent NumberFormatException
        String priceInput = ctx.formParam("price");
        if (!InputValidator.isItEmpty(priceInput) && !InputValidator.isNumeric(priceInput)) {
            ctx.attribute("errorMessage", "Pris skal være et tal");
            ctx.redirect("/adminMaterialCRUD");
            return;
        }

        //Verifies if length isnt empty and isnt a number, to prevent NumberFormatException
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

    }

    public static void deleteMaterialByID(Context ctx, ConnectionPool connectionPool) {
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

    }

    public static void seeAllMaterial(Context ctx, ConnectionPool connectionPool) {
        //Gets all materials and sends them to session
        List<Material> allMaterial = AdminMapper.seeAllMaterial(connectionPool);
        ctx.sessionAttribute("allMaterial", allMaterial);
        ctx.redirect("/adminUserMaterial");

    }
}
