package controller;

import io.javalin.Javalin;
import persistence.ConnectionPool;

public class RequestController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/carportRequest", ctx -> ctx.render("carportRequest.html"));
        app.post("/carportRequest", ctx -> ctx.render("carportRequest.html"));
    }
}