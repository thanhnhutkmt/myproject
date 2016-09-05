/**
 * 
 */
package exception;

/**
 * @author ThanhNhut
 * 
 */
public class AssignException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1851927455732288526L;
	public static final String CANNOTASSIGN = "Can not assign %s to %s";
	public static final String LARGENUMBER = "Can not assign %s to type %s";
	public static final String TOOMUCHARRAYELEMENT = "Assign more elements than array capacity";
	public static final String ARRAYSTRINGSYNTAXERROR = "Assign array has syntax error";
	public static final String POINTERPOINTERROR = "Point outside the bound of array";
	public static final String POINTERPOINTTYPEERROR = "Pointer type is different from pointed object";
	
	/**
	 * 
	 */
	public AssignException(String message) {
		super(message);
	}
}
