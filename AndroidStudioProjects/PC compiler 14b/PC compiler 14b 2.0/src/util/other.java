/**
 * 
 */
package util;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author ThanhNhut
 * 
 */
public class other {
	public static String[] toArray(ArrayList<String> data) {
		String temp[] = new String[data.size()];
		int index = 0;
		for (String line : data) {
			temp[index] = line;
			index++;
		}
		return temp;
	}

	public static final int CENTER = 2;
	public static final int LEFT = 1;
	public static final int RIGHT = 3;

	public static String createFixedLengthString(int alignment, String data,
			int stringLength) {
		String result = "";
		StringBuilder sb = new StringBuilder();
		if (alignment == CENTER) {
			sb.append(data);
		} else if (alignment == LEFT) {
			sb.append(data);
			int numberOfSpaces = stringLength - data.length();
			do {
				sb.append(" ");
				numberOfSpaces--;
			} while (numberOfSpaces > 0);
		} else {
			int numberOfSpaces = stringLength - data.length();
			do {
				sb.append(" ");
				numberOfSpaces--;
			} while (numberOfSpaces > 0);
			sb.append(data);
		}
		result = sb.toString();
		return result;
	}

	public static String randomString(int Length) {
		StringBuilder sb = new StringBuilder();
		int loop = Length;
		char ranChar;
		do {
			ranChar = (char) ((Math.random() + 2.6) * 25);
			sb.append(ranChar);
			loop--;
		} while (loop > 0);
		return sb.toString();
	}

	public static String getPICNumber(String processor) {
		if (processor.startsWith("PIC")) {
			return processor.substring(3);
		} else if (processor.startsWith("dsPIC")) {
			return processor.substring(5);
		} else {
			return processor;
		}
	}

	public static String getStringFileSize(long size) {
		long temp = size;
		int unit = 0;
		String fileSize = "";
		do {
			temp = temp / 1024;
			unit++;
		} while (temp > 1024);

		switch (unit) {
		case 0:
			fileSize = Integer.toString((int) temp) + " Bytes";
			break;
		case 1:
			fileSize = Integer.toString((int) temp) + " KB";
			break;
		case 2:
			fileSize = Integer.toString((int) temp) + " MB";
			break;
		case 3:
			fileSize = Integer.toString((int) temp) + " GB";
			break;
		case 4:
			fileSize = Integer.toString((int) temp) + " TB";
			break;
		default:
			fileSize = "unknown";
			break;
		}
		return fileSize;
	}

	public static DefaultMutableTreeNode getFolderStructure(File f) {
		myfile folder = new myfile(f.getPath());
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				folder);
		if (folder.isFile()) {
			return root;
		} else {
			DefaultMutableTreeNode rootChild = null;
			File content[] = folder.listFiles();
			if ((content == null) || (content.length == 0)) {
				return root;
			} else {
				for (int index = 0; index < content.length; index++) {				
					rootChild = getFolderStructure(content[index]);	
					root.add(rootChild);
				}
			}
		}
		return root;
	}
	
	public static String getFileName(String filePath) {
		String name = "";
		int namePos = filePath.lastIndexOf("\\");
		name = filePath.substring(namePos + 1);
		return name;
	}
	
	public static int getSizeOf(String type) {
		switch(type) {
			case "char" :	
				return constants.SIZEOFCHAR;
			case "int" :
				return constants.SIZEOFINT;
			case "ushort" :
				return constants.SIZEOFUSHORT;
			case "short" :
				return constants.SIZEOFSHORT;
			case "ulong" :
				return constants.SIZEOFULONG;
			case "long" :
				return constants.SIZEOFLONG;
			case "float" :
				return constants.SIZEOFFLOAT;
			default :
				return 0;
		}
	}	
	
	public static String getFitType(int value) {
		String fixedType = "";
		if ((value <= constants.MAXFLOAT) && (value >= constants.MINFLOAT)) {
			fixedType += "float"; 
		}
		if ((value <= constants.MAXLONG) && (value >= constants.MINLONG)) {
			fixedType += "long";
		}
		if ((value <= constants.MAXULONG) && (value >= constants.MINULONG)) {
			fixedType += "ulong";
		}
		if ((value <= constants.MAXSHORT) && (value >= constants.MINSHORT)) {
			fixedType += "short";
		}
		if ((value <= constants.MAXUSHORT) && (value >= constants.MINUSHORT)) {
			fixedType += "ushort";
		} 
		if ((value <= constants.MAXINT) && (value >= constants.MININT)) {
			fixedType += "int";
		}
		if ((value <= constants.MAXCHAR) && (value >= constants.MINCHAR)) {
			fixedType += "char";
		}
		return fixedType;
	}
	
	public static ArrayList<String> splitValue(long value, int sizeOfType) {
		long temp = value;
		int splitedValue = 0;
		ArrayList<String> values = new ArrayList<String>();
		for (int index = 0; index < sizeOfType; index++) {
			splitedValue = (int) (temp - ((temp / 256) * 256));
			values.add(other.toHexString(splitedValue));
			temp /= 256;
		}		
		return values;
	}
	
	public static ArrayList<Long> splitValueToLong(long value, int sizeOfType) {
		long temp = value;
		long splitedValue = 0;
		ArrayList<Long> values = new ArrayList<Long>();
		for (int index = 0; index < sizeOfType; index++) {
			splitedValue = (temp - ((temp / 256) * 256));
			values.add(new Long(splitedValue));
			temp /= 256;
		}		
		return values;
	}
	
	public static String toHexString(int value) {
		String hexString = Integer.toHexString(value);
		if (hexString.length() < 2) {
			hexString = "0" + hexString;
		}
		return hexString.toUpperCase();
	}
	
	/**
	 * Split string by given delimiter named delimit with specified output Type.
	 * @param Ccode
	 * 			String to split
	 * @param delimit
	 * 			delimiter for StringTokenizer
	 * @param outputType 
	 * 			output type : 
	 * 			<br>1 : String array
	 * 			<br>2 : ArrayList<String>
	 * @return
	 *  	array of Strings splitted.
	 */
//	public static Object splitCode(String Ccode, String delimit, int outputType) {
//		Ccode = Ccode.trim();
//		StringTokenizer token = new StringTokenizer(Ccode, delimit);
//		ArrayList<String> code = new ArrayList<String>();
//		int index = 0;
//		int numberOfparts = token.countTokens();
//		while(token.hasMoreTokens()) {
//			if ((index == numberOfparts - 1) 
//					&& (!Ccode.endsWith(delimit))) {
//				code.add(index, token.nextToken().trim());
//			} else {
//				code.add(index, token.nextToken().trim() + delimit);
//			}
//			index++;
//		}
//
//		if (outputType == 1) {
//			return other.toArray(code);
//		} else {
//			return code;
//		}
//	}
	
	public static Object splitCode(String Ccode, String delimit, int outputType) {
		Ccode = Ccode.trim();
		ArrayList<String> code = new ArrayList<String>();
		String temp = new String(Ccode);
		int index = 0;
		if (temp.contains(delimit)) {
			do {
				System.out.println("temp : " + temp + "\nindex : " 
							+ index + "\ndelimit : " + delimit);
				int splittedPos = temp.indexOf(delimit);
				String splitedString = temp.substring(0, splittedPos + 1);
				code.add(index, splitedString.trim());
				if (splittedPos == temp.length()) {
					break;
				} else {
					temp = new String(temp.substring(temp.indexOf(delimit) + 1));
					index++;
				}
			} while (temp.contains(delimit));
			if (temp.length() > 0) {
				code.add(index, temp.trim());
			}
		} else {
			code.add(temp);
		}
		if (outputType == 1) {
			return other.toArray(code);
		} else {
			return code;
		}	
	}
	
}
