package controller;

import io.javalin.Javalin;
import persistence.ConnectionPool;
import validators.RoleValidator;


public class AdminController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        //Gatekeeps admin page, if user without permission somehow try to inter
        // it will return it to index
        app.get("/admin", ctx -> {if (!RoleValidator.hasRole(ctx, "admin")){
            ctx.redirect("/index");
            return;
        }
            ctx.render("admin.html");
        });



    }

}
