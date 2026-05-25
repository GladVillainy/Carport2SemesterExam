package validators;
import io.javalin.http.Context;

public class RoleValidator {
    public static boolean hasRole(Context ctx, String role) {
        //gets customer from session
        entities.Customer customer = ctx.sessionAttribute("currentUser");

        //check if customer is a guest customer
        if(customer == null){
            return false;
        }
        //Check if a user has the given role
        if (customer.getRole().equalsIgnoreCase(role)) {
            return true;
        } else {
            return false;
        }
    }
}
