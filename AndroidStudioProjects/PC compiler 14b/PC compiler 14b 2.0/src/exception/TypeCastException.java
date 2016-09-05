/**
 * 
 */
package exception;

/**
 * @author ThanhNhut
 *
 */
public class TypeCastException extends Exception {
	public static final String CANNOTCAST = "Can not cast from %s to %s";
	/**
	 * 
	 */
	private static final long serialVersionUID = 4137411139626161216L;

	public TypeCastException(String message) {
		super(message);			
	}
}
