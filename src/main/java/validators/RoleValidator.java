package validators;
import io.javalin.http.Context;

public class RoleValidator {
    public static boolean checkAdmin(Context ctx) {
        entities.Customer customer = ctx.sessionAttribute("currentUser");
        if (customer.getRole().equalsIgnoreCase("admin")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkSalesman(Context ctx) {
        entities.Customer customer = ctx.sessionAttribute("currentUser");
        if (customer.getRole().equalsIgnoreCase("sales")) {
            return true;
        } else {
            return false;
        }
    }
}
