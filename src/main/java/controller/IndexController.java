package controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import persistence.ConnectionPool;

public class IndexController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/index", ctx -> ctx.render("index.html"));
    }
}
