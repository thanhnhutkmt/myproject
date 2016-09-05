/**
 * 
 */
package nhutlt.androidapp.filerevealer.util;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nhutlt
 *
 */
public class other {
	public static <E> List convertArray(E[] array) {
		List convertedList = new ArrayList<E>();
		int size = array.length;
		for (int index = 0; index < size; index++) {
				convertedList.add((E) array[index]);
		}
		return convertedList;
	}

	public static File[] convertList(List<File> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		int size = list.size();
		File[] convertedArray = new File[size];
		
		for (int index = 0; index < size; index++) {
			convertedArray[index] = list.get(index);
		}
		return convertedArray;
	}
}
