package validators;
import io.javalin.http.Context;

public class RoleValidator {
    public static boolean checkAdmin(Context ctx) {
        //gets customer from session
        entities.Customer customer = ctx.sessionAttribute("currentUser");

        //check if customer is a guest customer
        if(customer == null){
            return false;
        }
        //Check if customer is an admin
        if (customer.getRole().equalsIgnoreCase("admin")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkSalesman(Context ctx) {
        //gets customer from session
        entities.Customer customer = ctx.sessionAttribute("currentUser");

        //check if customer is a guest customer
        if(customer == null){
            return false;
        }

        //Check if customer is a sales
        if (customer.getRole().equalsIgnoreCase("sales")) {
            return true;
        } else {
            return false;
        }
    }
}
