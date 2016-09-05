/**
 * 
 */
package exception;

/**
 * @author ThanhNhut
 *
 */
public class CompareException extends Exception {
	public static final String WRONGOPERATOR = "Compare operator %s is not supported";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7142007435599597977L;

	public CompareException(String message) {
		super(message);
	}
}
