package validators;

public class InputValidator {
    public static boolean isItEmpty(Object checking){
        if(checking == null || checking.toString().isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String checking) {
        try {
            Integer.parseInt(checking);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDanishPhoneNumber(String checking) {
        if(!isItEmpty(checking)) {
            if (checking.length() == 8) {
                if (isNumeric(checking)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
