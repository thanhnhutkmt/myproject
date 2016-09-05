/**
 * 
 */
package nhutlt.studyapp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import android.os.Environment;
import android.util.Log;

/**
 * @author ThanhNhut
 * 
 */
public class preparer {

	public static boolean checkAppFolder(String appFolderName) {
		boolean noError = true;
		File sdcard = Environment.getExternalStorageDirectory();
		String sdcardPath = sdcard.getPath();
		String appPath = sdcardPath + "/" + appFolderName;
		File appFolder = new File(appPath);
		if (!appFolder.exists() || !appFolder.isDirectory()) {
			noError = appFolder.mkdir();
		}
		if (!appFolder.canRead() || !appFolder.canWrite()) {
			noError = false;
		}
		return noError;
	}

	public static boolean checkDataFile(String appFolderName) {
		boolean noError = true;
		File sdcard = Environment.getExternalStorageDirectory();
		String sdcardPath = sdcard.getPath();
		String dataPath = sdcardPath + "/" + appFolderName + "/data.txt";
		File dataFile = new File(dataPath);
		if (!dataFile.exists() || !dataFile.canRead()) {
			noError = false;
		}
		return noError;
	}

	public static ArrayList<Object> extractData(ArrayList<String> dataBlock) {
		ArrayList<Object> data = new ArrayList<Object>();
		ArrayList<String> word = new ArrayList<String>();
		ArrayList<String> pronun = new ArrayList<String>();
		ArrayList<String> wordType = new ArrayList<String>();
		ArrayList<String> meaning = new ArrayList<String>();
		data.add(word);
		data.add(pronun);
		data.add(wordType);
		data.add(meaning);

		StringTokenizer token = null;
		long lineNumber = 0;
		String meaningSeparator = "//";
		for (String line : dataBlock) {
			lineNumber++;
			Log.d("NhutLT", "Process at line #" + lineNumber);
			try {
				String words = line.substring(0, line.indexOf("[") - 1);
				String pronuns = line.substring(line.indexOf("["), line.indexOf("]") + 1);
				word.add(words);
				pronun.add(pronuns);
				String type = "";
				String meanings = "";
				String meaninz = line.substring(line.indexOf("]") + 2);

				if (meaninz.contains(meaningSeparator)) {
					token = new StringTokenizer(meaninz, meaningSeparator);
					while (token.hasMoreTokens()) {
						String part = token.nextToken();
						type += part.substring(0, part.indexOf(" "))
								+ meaningSeparator;
						meanings += part.substring(part.indexOf(" ") + 1)
								+ meaningSeparator;
					}
				} else {
					type = meaninz.substring(0, meaninz.indexOf(" "));
					meanings = meaninz.substring(meaninz.indexOf(" ") + 1);
				}
				wordType.add(type);
				meaning.add(meanings);
			} catch (NoSuchElementException e) {
				e.printStackTrace();
				Log.d("NhutLT", "Data line #" + lineNumber + " is syntax error");
				// continue;
			}
		}
		return data;
	}

	public static int[] genRandomOrder(int size) {
		int[] ranOrder = new int[size];
		ArrayList<Integer> numberArray = new ArrayList<Integer>();
		int value = 0;
		while (value < size) {
			value++;
			numberArray.add(value);
		}

		value = size - 1;
		while (value > 0) {
			int ranValue = (int) (Math.random() * value - 1);
			ranOrder[value] = numberArray.remove(ranValue);
			value--;
		}
		return ranOrder;
	}

	public static List<String> genAnswerChoices(ArrayList<String> pronun,
			ArrayList<String> wordType, ArrayList<String> meaning,
			int currentWord) {
		ArrayList<String> answerChoices = new ArrayList<String>();
		int[] wrongChoices = new int[4];
		int[] temp = genRandomOrder(meaning.size());
		int index = 0;
		int value = 0;
		do {				
			if (temp[value] != currentWord) {
				wrongChoices[index] = temp[value];
				index++;
			}
			value++;
		} while (index < wrongChoices.length);

		for (int number : wrongChoices) {
			 answerChoices.add(pronun.get(number));
			answerChoices.add(wordType.get(number));
			answerChoices.add(meaning.get(number));
		}
		 answerChoices.add(pronun.get(currentWord));
		answerChoices.add(wordType.get(currentWord));
		answerChoices.add(meaning.get(currentWord));

		List<String> answers = new ArrayList<String>();
		int[] ranOrder = genRandomOrder(answerChoices.size());
		for (int ran : ranOrder) {
			answers.add(answerChoices.get(ran));
		}

		return answers;
	}

	/**
	 * 
	 * @param ansWordType
	 * @param ansMeaning
	 * @param ansPronun
	 * @param pronun
	 * @param wordType
	 * @param meaning
	 * @param currentWord
	 * @return result 
	 * 
	 * 0 000 correct
	 * 1 001 wrong type
	 * 2 010 wrong pronun
	 * 3 011 wrong typepronun
	 * 4 100 wrong meaning
	 * 5 101 wrong typemeaning
	 * 6 110 wrong pronunmeaning
	 * 7 111 all wrong
	 */
	public static int checkAnswer(String [] ans, ArrayList<String> pronun,
			ArrayList<String> wordType, ArrayList<String> meaning,
			int currentWord) {
		int result = 7;
		String correctWordType = wordType.get(currentWord);
		String correctPronun = pronun.get(currentWord);
		String correctMeaning = meaning.get(currentWord);

		for(String answer : ans) {
			if (correctWordType.equals(answer)) {
				result -= 1;
				break;
			}
		}
		for(String answer : ans) {
			if (correctMeaning.equals(answer)) {
				result -= 4;
				break;
			}
		}
		for(String answer : ans) {
			if (correctPronun.equals(answer)) {
				result -= 2;
				break;
			}
		}

		return result;
	}

}
