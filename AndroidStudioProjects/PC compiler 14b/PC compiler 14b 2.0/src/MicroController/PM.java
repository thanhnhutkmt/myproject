/**
 * 
 */
package MicroController;

import java.util.ArrayList;

/**
 * @author ThanhNhut
 *
 */
public class PM {
	// constants
	public static final String NOTCMDMARK[] = {"#", ";", "@", "list"};
	
	// PM organization
	private final int NUMBEROFPAGE;
	private final int PAGESIZE;
	private final int FIRSTPAGE = 0;
	
	private ArrayList<PMPage> PM;
	private final int totalSize;
	private int usedSpace;

	public PM(int numberOfPage, int pageSize) {
		NUMBEROFPAGE = numberOfPage;
		PAGESIZE = pageSize;
		usedSpace = 0;
		totalSize = NUMBEROFPAGE * PAGESIZE;
		
		PM = new ArrayList<PMPage>();
		for (int index = 0; index < NUMBEROFPAGE; index++) {
			PM.add(new PMPage(PAGESIZE, index));
		}
	}
	
	/**
	 * Return used space in percent or number Of asm code lines.
	 * @param inPercent true return is percent, false return is number of asm code lines.
	 * @return
	 */
	public int getUsedSpace(boolean inPercent) {
		return inPercent ? usedSpace/totalSize : usedSpace; 
	}
	
	/**
	 * Check whether asm line is cmd line or not.
	 * @param asmLine
	 * @return true if is cmd line, false if not
	 */
	public static boolean isCMDLine(String asmLine) {
		boolean isCMD = true;
		for (String mark : NOTCMDMARK) {
			if (asmLine.startsWith(mark)) {					
				isCMD = false;
				break;
			}
		}
		return isCMD;
	}
	
	class PMPage {
    	private ArrayList<String> PMSpace;
    	private int available;
    	
    	public PMPage(int pageSize, int pageParam) {
    		available = pageSize;
    		PMSpace = new ArrayList<String>();
    		if (pageParam == FIRSTPAGE) {
    			makePageLayout();
    		} else {
    			// do some thing for other pages.
    		}
    	}
    	
    	/**
    	 * Write functions to PM
    	 * @param codeLines asm code lines of 1 function
    	 * @return number of written lines or 
    	 *         -1 if write error
    	 *          0 if codeLines null or empty 
    	 *            or not enough space for whole function
    	 */
    	public int write(ArrayList<String> codeLines) {
    		int index = 0;
    		int length;
    		if ((codeLines == null) || (codeLines.size() == 0)) {
    			return 0;
    		}
    		length = codeLines.size();
    		if (length > available) {
    			return 0;
    		}
    		
    		for (; index < length; index++) {
    			String asmCodeLine = codeLines.get(index);
    			boolean isCMDLine = isCMDLine(asmCodeLine);
    			if (!writeLine(asmCodeLine, isCMDLine)) {
    				return -1;
    			}
    		}
    		
    		return index;
    	}
    	
    	/**
    	 * Write asm code line to PM
    	 * @param codeLine asm code line String
    	 * @param isCMDLine true if string is command line, false if others
    	 * @return true if write success, false if out of page-space
    	 */
    	private boolean writeLine(String codeLine, boolean isCMDLine) {
    		if (isCMDLine) {
    			available--;
    		}
    		if (available > 0) {
    			PMSpace.add(PAGESIZE - available, codeLine);
    			return true;
    		} else {
    			return false;
    		}    		    	
    	}
    	    	
    	public void makePageLayout() {
    		writeLine("ORG 0x0", false);
    		writeLine("GOTO @MAIN", true);
    		writeLine("ORG 0x4", false);
//    		writeLine("GOTO @INTERRUPT", true);
    		writeLine("ORG 0x5", false);
    		writeLine("@MAIN", false);
//    		writeLine("@INTERRUPT", true);
    	}   	    	
    }	    
}
