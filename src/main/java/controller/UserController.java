package controller;

import exceptions.DatabaseException;
import io.javalin.http.Context;
import persistence.ConnectionPool;
import persistence.UserMapper;

public class UserController {

    public static void createUser(Context ctx, ConnectionPool connectionPool){
        //hent data user/pass
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        String password = "";
        if(password1.equals(password2)){
            password1 = password;
            //opret user i DB
            try {
                UserMapper.createUser(email, password2, connectionPool);
                //alert bruger om at user er blevet lavet
                String createConfirm = email+" er nu blevet oprettet som bruger!";
                ctx.attribute("msg", createConfirm);
                //tilbage til forside
                ctx.render("index.html");
            } catch (DatabaseException e) {
                ctx.attribute("msg", e.getMessage());
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("msg", "Your passwords do not match. Please try again");
            ctx.render("createUser.html");
        }
    }


}
