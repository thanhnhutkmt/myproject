/**
 * 
 */
package nhutlt.androidapp.filerevealer.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * @author nhutlt
 * 
 */
public class FileInfoUtility {
	public static List<String[]> getFolderInfo(File folder, Method... method) {
		
		int numberOfInfoType = method.length;
		List<String[]> info = new ArrayList<String[]>();
		String[] arrayFilesOfFolder = folder.list();
		if (arrayFilesOfFolder == null) {
			return null;
		}
		info.add(arrayFilesOfFolder);
		info.add(getFileType(folder));
		if (numberOfInfoType == 0) {
			return info;
		}
		int numberOfFile = info.get(0).length;
		File[] fileArray = folder.listFiles();
		for (int indexOfInfoType = 0; indexOfInfoType < numberOfInfoType; indexOfInfoType++) {
//			Log.v("NhutLT", method[indexOfInfoType].getName());
			String[] tempInfo = new String[numberOfFile];
			for (int indexOfFile = 0; indexOfFile < numberOfFile; indexOfFile++) {
				try {
//					Log.v("NhutLT", "method index " + indexOfInfoType + ", file index : " + indexOfFile);
					tempInfo[indexOfFile] = method[indexOfInfoType].invoke(
							fileArray[indexOfFile], new Object[] {}).toString();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} 
			}
			// Change this to process info format for your data
			int index = 0;
			String[] arrayInfo = new String[tempInfo.length];
			switch(indexOfInfoType) {
				case 0: // date info
					index = 0;
					for (String date : tempInfo) {
						arrayInfo[index] = dateUtility.longToDate(date);
						index++;
					}
					break;
				case 1: // size info				
					index = 0;
					for (String size : tempInfo) {
						arrayInfo[index] = changeSizeFormat(size);
						index++;
					}
					break;
			}
			// end process
			
			info.add(arrayInfo);
		}

		return info;
	}
	
public static List<String[]> getFolderInfo(List<File> listFile, Method... method) {
		if (listFile == null || listFile.size() == 0) {
			return null;
		}
		int numberOfInfoType = method.length;
		List<String[]> info = new ArrayList<String[]>();
		int sizeOfListFile = listFile.size();
		String[] arrayFilesOfFolder = new String[sizeOfListFile];
		for (int index = 0; index < sizeOfListFile; index++) {
			arrayFilesOfFolder[index] = listFile.get(index).getName();
		}
				
		info.add(arrayFilesOfFolder);
		info.add(getFileType(listFile));
		if (numberOfInfoType == 0) {
			return info;
		}
		int numberOfFile = info.get(0).length;
		File[] fileArray = other.convertList(listFile);
		for (int indexOfInfoType = 0; indexOfInfoType < numberOfInfoType; indexOfInfoType++) {
//			Log.v("NhutLT", method[indexOfInfoType].getName());
			String[] tempInfo = new String[numberOfFile];
			for (int indexOfFile = 0; indexOfFile < numberOfFile; indexOfFile++) {
				try {
//					Log.v("NhutLT", "method index " + indexOfInfoType + ", file index : " + indexOfFile);
					tempInfo[indexOfFile] = method[indexOfInfoType].invoke(
							fileArray[indexOfFile], new Object[] {}).toString();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} 
			}
			// Change this to process info format for your data
			int index = 0;
			String[] arrayInfo = new String[tempInfo.length];
			switch(indexOfInfoType) {
				case 0: // date info
					index = 0;
					for (String date : tempInfo) {
						arrayInfo[index] = dateUtility.longToDate(date);
						index++;
					}
					break;
				case 1: // size info				
					index = 0;
					for (String size : tempInfo) {
						arrayInfo[index] = changeSizeFormat(size);
						index++;
					}
					break;
			}
			// end process
			
			info.add(arrayInfo);
		}

		return info;
	}

	private static String[] getFileType(File folder) {
		if (folder.isDirectory()) {
			File[] folderFiles = folder.listFiles();
			String[] arrayType = new String[folderFiles.length];
			int index = 0;
			for (File file : folderFiles) {
				if (file.isFile()) {
					String name = file.getName();
					if (name.contains(".")) {
						String extension = name.substring(name.lastIndexOf('.') + 1, name.length());						
						arrayType[index] = extension;
					} else {
						arrayType[index] = "unknown";
					}
				} else {// if (file.isDirectory()) {
					arrayType[index] = "Folder";
				}			
				index++;
			}
			return arrayType;
		} else {
			return null;
		}
	}
	
	private static String[] getFileType(List<File> listFile) {
		if (listFile != null && listFile.size() > 0) {
			File[] folderFiles = other.convertList(listFile);
			String[] arrayType = new String[folderFiles.length];
			int index = 0;
			for (File file : folderFiles) {
				if (file.isFile()) {
					String name = file.getName();
					if (name.contains(".")) {
						String extension = name.substring(name.lastIndexOf('.') + 1, name.length());						
						arrayType[index] = extension;
					} else {
						arrayType[index] = "unknown";
					}
				} else {// if (file.isDirectory()) {
					arrayType[index] = "Folder";
				}			
				index++;
			}
			return arrayType;
		} else {
			return null;
		}
	}
	
	public static String changeSizeFormat(String size) {
		Long sizeInByte = Long.parseLong(size);
		int unitSelector = 0;
		while (sizeInByte > 1024) {
			sizeInByte /= 1024;
			unitSelector++;
		}
		switch(unitSelector) {
			default :
				return sizeInByte.toString() + "B";
			case 1 :
				return sizeInByte.toString() + "KB";
			case 2 :
				return sizeInByte.toString() + "MB";
			case 3 :
				return sizeInByte.toString() + "GB";
		}
	}
	
	public static String getOneLevelUpFolderPath(String path) {
		String previousFolderPath = path.substring(0, path.lastIndexOf("/"));
		return previousFolderPath;
	}

	public static String changeDurationFormat(int milliseconds) {
		int hour = milliseconds / 3600000;
		milliseconds = milliseconds % 3600000;
		int minute = milliseconds / 60000;
		milliseconds = milliseconds % 60000;
		int second = milliseconds / 1000;
		return String.format("%2d:%2d:%2d", hour, minute, second);
	}
}
