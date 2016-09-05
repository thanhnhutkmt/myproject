/**
 * 
 */
package exception;

/**
 * @author ThanhNhut
 *
 */
public class ArrayException extends Exception {
	public static final String OUTOFBOUND = "Index is out of bound";
	/**
	 * 
	 */
	private static final long serialVersionUID = 2118828739499773190L;

	public ArrayException(String message) {
		super(message);
	}
}
