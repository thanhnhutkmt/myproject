/**
 * 
 */
package util;

import java.io.File;

/**
 * @author ThanhNhut
 *
 */
public class myfile extends File{
	/**
	 * 
	 */
	private static final long serialVersionUID = -405218349995559968L;
	
	public myfile(String pathname) {
		super(pathname);
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
}
