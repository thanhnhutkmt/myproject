/**
 * 
 */
package MicroController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import exception.AssignException;

import util.constants;
import util.other;

/**
 * @author ThanhNhut
 *
 */
public class RAM {
	// RAM organization
	private final int RAMSIZE;
	public static final String INDEXMARK = "#_";
	public static final String ARRAYPREFIX = "array*";
	public static final String POINTERPREFIX = "*";
	public static final String POINTERVARPREFIX = "pointedby*";
	public static final String ARRAYSIZEMARK = "_";
    private Hashtable<String, Integer> varTable;      // variable, address
    private Hashtable<Integer, Integer> statusTable;  //  address, link 
    private Hashtable<String, String> varTypeTable;   // variable, type

	public RAM(int ramSize) {
		RAMSIZE = ramSize;
		varTable = new Hashtable<String, Integer>();
		statusTable = new Hashtable<Integer, Integer>(RAMSIZE);
		varTypeTable = new Hashtable<String, String>();
		setRAMAddress();
	}
	
	public void setRAMAddress() {
		int index = 0;
		do {							
		    if (((index >= 32) && (index < 128))             //96
		    		|| ((index >= 160) && (index < 240))     //80
		    		|| ((index >= 272) && (index < 368))     //96
		    		|| ((index >= 400) && (index < 496))) {  //96
			    statusTable.put(index, 0);
		    } else {
		    	statusTable.put(index, 1);
		    }
		    index++;
		} while (index < 512);			
	}
	
	public void releaseVar(String varName) {
		Integer address = varTable.remove(varName);
		if (address != null) {
			int link = statusTable.get(address);
			link = link - 1;
			statusTable.put(address, link);
		}
	}
	
	public void release(int address) {
		int link = statusTable.get(address);
		link = link - 1;
		statusTable.put(address, link);
	}
	
	/**
	 * Release declared pointer and free RAM
	 * @param ptrName
	 * 			ptrName is pointer name without pointerprefix
	 */
	public void releasePtr(String ptrName) {
		String ptrType = varTypeTable.get(POINTERPREFIX 
				+ ptrName);
		int sizeOfType = other.getSizeOf(ptrType);
		// remove pointer in varTable
		varTable.remove(POINTERPREFIX + ptrName + INDEXMARK + "1");
		varTable.remove(POINTERPREFIX + ptrName + INDEXMARK + "2");
		// release pointer's memory space
		if (sizeOfType == 1) {
			releaseVar(POINTERVARPREFIX + ptrName);
		} else if (sizeOfType == 2) {
			release2Bytes(POINTERVARPREFIX + ptrName);
		} else {
			release4Bytes(POINTERVARPREFIX + ptrName);
		}
	}
	
	public int getAvailableAddress() {
	    for (int index = 0; index < RAMSIZE; index++) {
	    	if (statusTable.get(index) == 0) {
	    		return index;
	    	}
	    }
	    return constants.NULL;
	}
	
	public boolean declareVar(String varName) {
		int address = getAvailableAddress(); 
		if (address == constants.NULL) {
			return false;				
		} else {
			varTable.put(varName, address);
			int link = statusTable.get(address) + 1;
			statusTable.put(address, link);
			return true;
		}
	}
	
	/**
	 * Declare a pointer and allocate a memory space based on its type.
	 * @param ptrName
	 * 			ptrName is pointer name without pointerprefix
	 * @param type
	 * @return
	 * 		true if and only if allocated sucessfully.
	 * 		false out of memory.
	 */
	public boolean declarePtr(String ptrName, String type) {	
		int size = other.getSizeOf(type);
		return declarePtr(ptrName, size);
	}
	
	/**
	 * Declare a pointer and allocate a memory space based on given size.
	 * @param ptrName
	 * 			ptrName is pointer name without pointerprefix
	 * @param sizeOfType size of type
	 * @return
	 * 		true if and only if allocated sucessfully.
	 * 		false out of memory.
	 */
	public boolean declarePtr(String ptrName, int sizeOfType) {
		int address = 0;
		if (sizeOfType == 1) {
			if (!declareVar(POINTERVARPREFIX + ptrName)) {
				return false;
			}
			address = varTable.get(POINTERVARPREFIX + ptrName);
		} else if (sizeOfType == 2) {
			if (!declareShort(POINTERVARPREFIX + ptrName)) {
				return false;
			}
			address = varTable.get(POINTERVARPREFIX + ptrName + INDEXMARK + "0");
		} else {
			if (!declareLong(POINTERVARPREFIX + ptrName)) {
				return false;
			}
			address = varTable.get(POINTERVARPREFIX + ptrName + INDEXMARK + "0");
		}
		varTable.put(POINTERPREFIX + ptrName + INDEXMARK + "1", address - (address / 256) * 256);
		varTable.put(POINTERPREFIX + ptrName + INDEXMARK + "2", address / 256);
		return true;
	}
	
	/**
	 * Point declared pointer to variable named varName.
	 * Release previously pointed memory space.
	 * @param ptrName
	 * 			ptrName is pointer started with pointerprefix.
	 * @param varName
	 * 			varName is <br>
	 * 			+ "variableName" for variable.<br>
	 * 			+ "arrayName + [index]" for array element.<br>
	 * 			+ "arrayName" for array. ptr points to first element.<br>
	 * 			+ "pointerprefix + ptrName" for pointer.<br> 
	 * @throws AssignException 
	 */
	public void pointPtrToVar(String ptrName, String varName) throws AssignException {
		int address = 0;
		releasePtr(ptrName.substring(1));
		if (varName.startsWith(RAM.POINTERPREFIX)) {
			// varName is pointer
			varTable.put(ptrName + INDEXMARK + "1", 
					varTable.get(varName + INDEXMARK + "1"));
			varTable.put(ptrName + INDEXMARK + "2", 
					varTable.get(varName + INDEXMARK + "2"));
			return;
		} else if (varName.startsWith(RAM.ARRAYPREFIX)) {
			String regArrayName = RAM.ARRAYPREFIX + 
					varName.substring(0, varName.indexOf("["));
			String indexString = varName.substring(varName.indexOf("[") + 1, varName.indexOf("]"));
			String type_size = varTypeTable.get(regArrayName);
			String sizeString = type_size.substring(type_size.indexOf("_") + 1);
			int size = Integer.parseInt(sizeString);
			int index = Integer.parseInt(indexString);
			if (index >= size) {
				throw new AssignException(AssignException.POINTERPOINTERROR);
			}
			if (varName.contains("[")) {
				// varName is array element
				if (size == 1) {
					address = varTable.get(varName + INDEXMARK + index);						
				} else {
					address = varTable.get(varName + INDEXMARK + index + INDEXMARK + "0");
				}
			} else {
				// varName is array
				if (size == 1) {
					address = varTable.get(varName + INDEXMARK + "0");						
				} else {
					address = varTable.get(varName + INDEXMARK + "0" + INDEXMARK + "0");
				}
			}
		} else {
			// varName is variable
			address = varTable.get(varName);
		}		
		varTable.put(POINTERPREFIX + ptrName + INDEXMARK + "1", address - (address / 256) * 256);
		varTable.put(POINTERPREFIX + ptrName + INDEXMARK + "2", address / 256);
	}
	
	public boolean declareArray(String arrayName, int arraySize, int sizeOfType) {
		int index = 0;
		int address = 32;
		int link = 0;
		do {
			for (index = 0; index < (arraySize * sizeOfType); index++) {
			    if (statusTable.get(address + index) != 0) {
			    	break;
			    }
			}
			if (index == (arraySize * sizeOfType)) {
				for (index = 0; index < arraySize; index++) {
					if (sizeOfType == 1) {
						varTable.put(arrayName + INDEXMARK + index, address + index);
						link = statusTable.get(address + index) + 1;
						statusTable.put(address + index, link);
					} else {
						for (int indexType = 0; indexType < sizeOfType; indexType++) {
							varTable.put(arrayName + INDEXMARK + index + INDEXMARK + indexType, address + (index * sizeOfType) + indexType);
							link = statusTable.get(address + index) + 1;
							statusTable.put(address + index, link);
						}
					}
				}
				return true;
			} else {
				address += index + 1;
			}
		} while (address < 512); 			
		return false;
	}
	
	public void releaseArray(String arrayName, int arraySize) {
		for (int index = 0; index < arraySize; index++) {
			releaseVar(arrayName + INDEXMARK + index);
		}
	}
	
	// declare short
	public boolean declareShort(String varName) {
		return declareArray(varName, 2, 1);
	}
	
	public void release2Bytes(String varName) {
		releaseArray(varName, 2);
	}
	
	// declare long
	public boolean declareLong(String varName) {
		return declareArray(varName, 4, 1);			
	}
			
	public void release4Bytes(String varName) {
		releaseArray(varName, 4);
	}
	
	// declare float
	public boolean declareFloat(String varName) {
		return declareArray(varName, 4, 1);
	}
	
	// declare pointer with length *ptr[length]
	public boolean pointArrayPointer(String ptrName, int arraySize, int sizeOfType) {
		boolean result = true;
		result = declareArray("array" + ptrName, arraySize, sizeOfType);
		int address = varTable.get("array" + ptrName + INDEXMARK + "0");
		if ((address + arraySize) > 255) {
			if (!declareVar(ptrName + "1") || !declareVar(ptrName + "2")) {
				result = false; 
			}
		} else {
			if (!declareVar(ptrName)) {
				result = false;
			}
		}

		return result;
	}
	
	public void releaseArrayPointer(String ptrName, int arraySize) {
	    releaseArray("array" + ptrName, arraySize);
	    int address = varTable.get("array" + ptrName + INDEXMARK + "0");
	    releasePtr(ptrName);//, address + arraySize);
	}
	
	public Hashtable<String, Integer> getVarTable() {
		return varTable;
	}
	
	public int getPtrAddress(String ptrName) {
		return (varTable.get(ptrName + INDEXMARK + "1") + 256 * varTable.get(ptrName + INDEXMARK + "2"));
	}

	public int getArrayAdress(String regArrayName, int index) {
		String type_Size = getVarType(regArrayName);
		String type = type_Size.substring(0, type_Size.indexOf("_"));
		int sizeOfType = other.getSizeOf(type);
		String arrayName = regArrayName.substring(ARRAYPREFIX.length());
		int address = 0;
		if (sizeOfType == 1) {
			address = varTable.get(arrayName + INDEXMARK + index);
		} else {
			address = varTable.get(arrayName + INDEXMARK + index + INDEXMARK + "0");
		}		
		return address;
	}
	
	public boolean regVarType(String varName, String varType) {
		varTypeTable.put(varName, varType);
		return (varTypeTable.containsKey(varName) 
					&& (varTypeTable.get(varName) != null) 
						&& (varTypeTable.get(varName).equals(varType)));
	}
	
	public String getVarType(String varName) {
		return varTypeTable.get(varName);		
	}
	
	/**
	 * Tests if the specified variable exists in this hashtable.
	 * @param varName <br>
	 * 			varName is <br>+ "variableName" for variable<br>
	 *                     + "pointerprefix + pointerName" for pointer<br>
	 *                     + "arrayName + [index]" for array element.<br>
	 *                     + "arrayprefix + arrayName" for array.<br>
	 * @param type
	 * 			type of varName.
	 * @return true if and only if the specified variable 
	 * 		    is in this hashtable, 
	 *          as determined by the equals method; 
	 *          false otherwise. 
	 */
	public boolean isExist(String varName, String type) {
		boolean result = true;
		if (varName.contains(RAM.POINTERPREFIX)) {
			// varName is pointer
			result = varTypeTable.containsKey(varName);
		} else if (varName.contains(RAM.ARRAYPREFIX)) {
			if (varName.contains("[")) {
				// varName is array element
				String regArrayName = RAM.ARRAYPREFIX + 
						varName.substring(0, varName.indexOf("["));
				String indexString = varName.substring(varName.indexOf("[") + 1, varName.indexOf("]"));
				result = varTypeTable.containsKey(regArrayName);
				String type_size = varTypeTable.get(regArrayName);
				String sizeString = type_size.substring(type_size.indexOf("_") + 1);
				int size = Integer.parseInt(sizeString);
				int index = Integer.parseInt(indexString);
				result = (size > index) ? true : false;				
			} else {
				// varName is array
				result = varTypeTable.containsKey(varName);
			}
		} else {
			// varName is variable
			switch(type) {
				case "char" :
				case "int" :
					result = varTable.containsKey(varName);
				case "ushort" :
				case "short" :
					for (int index = 0; index < 2; index++) {
						result = varTable.containsKey(varName + INDEXMARK + index);
					}
					break;
				case "ulong" :
				case "long" :
				case "float" :
					for (int index = 0; index < 4; index++) {
						result = varTable.containsKey(varName + INDEXMARK + index);
					}
					break;
			}
		}
		return result;
	}
}	
