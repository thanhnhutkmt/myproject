/**
 * 
 */
package exception;

/**
 * @author ThanhNhut
 *
 */
public class PreProcessorException extends Exception {
	public static final String INCLUDENOSPACEERROR = "Line %d #Include has " +
			"no space.";
	public static final String INCLUDESYNTAXERROR = "Line %d Included file name " +
			"must be started with '<' or '\"' and ended with '>' or '\"'";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7571562674304824858L;

	public PreProcessorException(String message, int lineNumber) {
		super(String.format(message, lineNumber));
	}

	public PreProcessorException(String message) {
		super(message);
	}
}
