package controller;

import entities.User;
import exceptions.DatabaseException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import persistence.ConnectionPool;
import persistence.UserMapper;
import validators.InputValidator;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("/createUser", ctx -> ctx.render("createuser.html"));
        app.post("/createUser", ctx -> createUser(ctx, connectionPool));

        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> loginUser(ctx, connectionPool));

        app.get("/logout", ctx -> logoutUser(ctx));
    }

    public static void createUser(Context ctx, ConnectionPool connectionPool){
        //gets userinput
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String address = ctx.formParam("address");
        String role = "kunde";

        //Verifyes if phone isnt empty and isnt a number, to prevent NumberFormatException
        String phoneInput = ctx.formParam("phone");
        if(!InputValidator.isItEmpty(phoneInput) && !InputValidator.isNumeric(phoneInput)){
            ctx.attribute("msg", "Telefonnummer skal være et tal");
            ctx.render("createUser.html");
            return;
        }
        int phone = Integer.parseInt(phoneInput);

        //checks on user typed the right password
        String password = "";
        if(password1.equals(password2)){
            password = password1;
            //creates user in DB
            try {
                UserMapper.createUser(email, password, address, phone, role, connectionPool);
                String createConfirm = email+" er nu blevet oprettet som bruger!";
                ctx.attribute("msg", createConfirm);
                //directs the user to the frontpage
                ctx.render("index.html");
            } catch (DatabaseException e) {
                ctx.attribute("msg", e.getMessage());
                ctx.render("createUser.html");
            }
        } else {
            ctx.attribute("msg", "Your passwords do not match. Please try again");
            ctx.render("createUser.html");
        }
    }

    public static void loginUser(Context ctx, ConnectionPool connectionPool){
        // gets data
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        //logs user in
        try {
            User user = UserMapper.login(email, password, connectionPool);
            // puts session to the current user
            ctx.sessionAttribute("currentUser", user);

            //sets currentUserActive to true
            ctx.sessionAttribute("currentUserActive", "true");

            //directs the user to the frontpage
            ctx.render("index.html");
        } catch (DatabaseException e) {
            //if a mistakes happends
            ctx.attribute("msg", e.getMessage());
            //directs the user back to login
            ctx.render("login.html");
        }
    }

    public static void logoutUser(Context ctx){
        // stops session
        ctx.sessionAttribute("currentUser", null);

        ctx.sessionAttribute("currentUserActive", "false");

        // alert when user is successfully logged out
        String logoutConfirm = "Du er nu logget ud";
        ctx.attribute("msg", logoutConfirm);
        ctx.render("index.html");
    }


}
