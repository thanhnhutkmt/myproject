/**
 * 
 */
package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ThanhNhut
 * 
 */
public class FHandler {
	public static boolean createNewFile(String contentString, File oldfile,
			File newfile, long currentPos, boolean save) {
		boolean result = true;
		long newFilePos = 0;
		long oldFilePos = currentPos;
		byte[] data;
		if (oldfile.length() > constants.BYTEBUFFER) {
			// write contentString newfile
			data = contentString.getBytes();
			newFilePos += writeDataByte(data, newfile, newFilePos);
			// read oldfile (buffer size pos)
			data = getNextData(oldFilePos, oldfile);
			while (data != null) {
				oldFilePos += data.length;
				// write to new file
				newFilePos += writeDataByte(data, newfile, newFilePos);
				// read oldfile (buffer size pos)
				data = getNextData(oldFilePos, oldfile);
			}
		} else {
			// write ContentString newfile
			writeDataByte(contentString.getBytes(), newfile, 0);
		}

		if (save) {
			// delete oldfile
			String filePath = oldfile.getPath();
			result = oldfile.delete();
			// rename newfile to old file
			result = newfile.renameTo(new File(filePath));
		}
		return result;
	}

	public static byte[] getNextData(long currentPos, File file) {
		FileChannel fch = null;
		int readBytes = 0;
		ByteBuffer buffer = ByteBuffer.allocate(constants.BYTEBUFFER);
		byte data[] = null;

		if (currentPos >= (file.length() - 1)) {
			return data;
		}

		try {
			fch = new RandomAccessFile(file, "rw").getChannel();
			fch.position(currentPos);
			readBytes = fch.read(buffer);
			fch.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (readBytes > 0) {
			data = new byte[readBytes];
			for (int index = 0; index < readBytes; index++) {
				data[index] = buffer.get(index);
			}
		}

		return data;
	}

	@SuppressWarnings("resource")
	public static int writeDataByte(byte[] data, File file, long filePos) {
		FileChannel fch;
		int writtenBytes = 0;
		try {
			fch = new RandomAccessFile(file, "rw").getChannel();
			ByteBuffer buffer = ByteBuffer.wrap(data);
			writtenBytes = fch.write(buffer, filePos);
			fch.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writtenBytes;
	}

	/**
	 * Read input file into array of String
	 * 
	 * @param input
	 *            input file
	 * @param limit
	 *            number of line to read limit < 1 read all line
	 * @param returnType
	 * 			  1   : String array
	 * 			  != 1: ArrayList String
	 * @return array of string contains lines of file.
	 */
	public static Object readFile(File input, long limit, int returnType) {
		Scanner in = null;
		if (limit < 1) {
			limit = input.length();
		}
		try {
			in = new Scanner(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> Inc = new ArrayList<String>();
		int index = 0;
		do {
			Inc.add(in.nextLine());
			index++;
		} while ((index < limit) && (in.hasNextLine()));

		in.close();
		if (returnType == 1) {
			return other.toArray(Inc);
		} else {
			return Inc;
		}
	}

	// asm [0] : intro
	// asm [1] : config
	// asm [2] : define mem
	// asm [3] : define constant
	// asm [4] : main
	// asm [5] : sub program
	public static boolean outputAsm(String asm[], File output) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(output)));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		for (String code : asm) {
			out.write(code);
		}
		out.flush();
		out.close();
		return true;
	}

	/**
	 * Copy file 
	 * @param sFilePath path of source
	 * @param oFilePath path of destination 
	 * @return
	 */
	public static boolean copyFile(String sFilePath, String oFilePath) {
		boolean result = true;
		byte[] buffer = new byte[constants.BYTEBUFFER];
		FileInputStream reader = null;
		FileOutputStream writer = null;
		try {
			reader = new FileInputStream(sFilePath);
			writer = new FileOutputStream(oFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result = false;
		}

		int numberOfBytes = 0;
		try {
			numberOfBytes = reader.read(buffer);
			while (numberOfBytes != -1) {
				writer.write(buffer, 0, numberOfBytes);
				numberOfBytes = reader.read(buffer);
			}
			reader.close();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	public static boolean createProjectFolders(String projectPath,
			Object[] filePath, ArrayList<String> movingFile) {
		boolean result = true;
		//String[] groupName = { "Header file", "Source file", "Lib",
		//"Other file", "Doc file" };
		String[] folderStructure = { projectPath,
				projectPath + constants.HEADERFOLDER,
				projectPath + constants.SOURCEFOLDER,				
				projectPath + constants.LIBFOLDER,
				projectPath + constants.OTHERFOLDER,
				projectPath + constants.DOCFOLDER };
		File projectFolder;
		for (int index = 0; index < folderStructure.length; index++) {
			projectFolder = new File(folderStructure[index]);
			if (!((projectFolder.exists()) && (projectFolder.isDirectory()))) {
				result = projectFolder.mkdir();
			} else {
				return false;
			}
			if (!result) {
				return result;
			}
		}
		for (int index = 0; index < folderStructure.length - 1; index++) {			
			@SuppressWarnings("unchecked")
			ArrayList<String> listFile = (ArrayList<String>) filePath[index];
			for (String pathString : listFile) {
				copyFile(pathString,
						folderStructure[index + 1] + "\\"
								+ other.getFileName(pathString));
			}
		}
		deleteCopiedFile(movingFile);
		return true;
	}
	
	public static int deleteCopiedFile(ArrayList<String> movingFile) {
		int result = 0;
		if (movingFile == null || movingFile.size() == 0) {
			return result;
		}
		for (String filePath : movingFile) {
			File f = new File(filePath);
			if (f.delete()) {
				result++;
			}
		}
		return result;
	}
	
	/**
	 * Return the file Path of file f.
	 * @param f
	 * @param withSeparator
	 * 			The return path has path separator or not
	 * 			<br> True : with path separator. False : without
	 * 			<br> Ex : "c:\folder" instead of "c:\folder\".
	 * @return
	 * 		file path without its name.
	 */
	public static String getFilePath(File f, boolean withSeparator) {
		String filePath = f.getParent();
		if (withSeparator) {
			filePath = filePath + System.getProperty("file.separator");
		}
		
		return filePath;
	}
}
