/**
 * 
 */
package exception;

/**
 * @author ThanhNhut
 *
 */
public class DeclareException extends Exception {
	public static final String NOTDECLARE = "%s has not been declared yet.";
	public static final String NOARRAYSIZE = "%s has no array size.";
	public static final String ARRAYSIZEERROR = "Array size must be a number.";
	public static final String FUNCTIONHEADERERROR = "Function header error.";
	public static final String FUNCTIONFOOTERERROR = "Function footer error.";
	/**
	 * 
	 */
	private static final long serialVersionUID = -3435683625740514250L;

	public DeclareException(String message) {
		super(message);
	}
}
