package nhutlt.soft.util;

public class generalUtils {
    /**
     * Checks that a string is not null or empty
     * 
     * @param string
     *            any string
     * @param errorMsg
     *            error message
     * 
     * @throws IllegalArgumentException
     *             if the string is null or empty
     */
    public static void checkEmptyString(String string, String error) {
        String errorMessage = "string is null or empty";
        if (string == null || string.trim().length() <= 0 || error == null
                || error.trim().length() <= 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
    
}
