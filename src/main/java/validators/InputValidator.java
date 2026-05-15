package validators;

public class InputValidator {
    public static boolean isItEmpty(Object checking){
        if(checking == null || checking.toString().isEmpty()){
            return true;
        }
        return false;
    }
}
