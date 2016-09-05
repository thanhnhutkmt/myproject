/**
 * 
 */
package nhutlt.studyapp.wordadder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import android.os.Environment;

/**
 * @author ThanhNhut
 *
 */
public class adder {
	private ArrayList<String> data;
	
	public adder(String appFolderName) throws IOException {
		File sdcard = Environment.getExternalStorageDirectory();
		String sdcardPath = sdcard.getPath();
		String dataPath = sdcardPath + "/" + appFolderName + "/data.txt";
		File wordFile = new File(dataPath);
		data = new ArrayList<String>();

		String line = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(wordFile), "UTF-16LE"));
		while ((line = in.readLine()) != null) {
			data.add(line);
		}
	}
	
	public ArrayList<String> getData() {
		return data;
	}
}
