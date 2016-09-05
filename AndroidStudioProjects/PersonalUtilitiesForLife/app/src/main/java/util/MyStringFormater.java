package util;

import java.text.Normalizer;
import java.util.regex.Pattern;

import software.nhut.personalutilitiesforlife.R;

/**
 * Created by Nhut on 6/15/2016.
 */
public class MyStringFormater {
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String standardizePhoneNumber(String phoneNumber) {
        return phoneNumber.trim().replace(" ", "").replace("(", "").replace(")", "")
                .replace("+84", "0").replace(".", "").replace("-", "");
    }

    public static String convertSecondStringToTimeString(long numberOfseconds) {
        String result = convertSecondToTime(numberOfseconds);
        if (result.length() == 2) result = "00:" + result;
        return result;
    }

    public static String convertSecondToTime(long numberOfseconds) {
        if (numberOfseconds >= 31536000) {      // year 365 days
            return String.format("%02d:", numberOfseconds/31536000) + convertSecondToTime(numberOfseconds%31536000);
        } else if (numberOfseconds >= 2592000) {// month 30 days
            return String.format("%02d:", numberOfseconds/2592000) + convertSecondToTime(numberOfseconds%2592000);
        } else if (numberOfseconds >= 604800) { // week
            return String.format("%02d:", numberOfseconds/604800) + convertSecondToTime(numberOfseconds%604800);
        } else if (numberOfseconds >= 86400) {  // day
            return String.format("%02d ", numberOfseconds/86400) + convertSecondToTime(numberOfseconds%86400);
        } else if (numberOfseconds >= 3600) {   // hour
            return String.format("%02d:", numberOfseconds/3600) + convertSecondToTime(numberOfseconds%3600);
        } else if (numberOfseconds >= 60) {     // minute
            return String.format("%02d:", numberOfseconds/60) + convertSecondToTime(numberOfseconds%60);
        } else {                                // second
            return String.format("%02d", numberOfseconds);
        }
    }
}
