/**
 * 
 */
package MicroController;

/**
 * @author ThanhNhut
 *
 */
public class memory {
	// Memory organization	
	private PM pm;
	private RAM ram;
	//private EEROM eerom;
	private final int RAMSIZEINDEX = 0;
	private final int NUMBEROFPAGEINDEX = 1;
	private final int PAGESIZE = 2;
	//private final int EEROMSIZE = 3;	
	/**
	 * Memory organiztion constants
	 * index |   Value                |
	 *   0   | RAM size               |
	 *   1   | number of Page         |
	 *   2   | page size              |
	 *   4   | eerom size             |
	 */
	private int moc[]; 
		
	public memory() {
		moc = readMemoryOrg();
		ram = new RAM(moc[RAMSIZEINDEX]);
		pm = new PM(moc[NUMBEROFPAGEINDEX], moc[PAGESIZE]);
		// eerom = ...
	}
	
	public int [] readMemoryOrg() {		
		// read from file ...
		int moc[] = {512, 4, 2048};
		return moc;
	}
	
	public RAM getRAM() {
		return ram;
	}
	
	public PM getPM() {
		return pm;
	}  
	
}

