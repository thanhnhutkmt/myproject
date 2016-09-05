/**
 * 
 */
package nhutlt.androidapp.filerevealer.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nhutlt
 * 
 */
/*
 * 
 * Letter Date or Time Component Presentation Examples G Era designator Text AD
 * y Year Year 1996; 96 M Month in year Month July; Jul; 07 w Week in year
 * Number 27 W Week in month Number 2 D Day in year Number 189 d Day in month
 * Number 10 F Day of week in month Number 2 E Day in week Text Tuesday; Tue a
 * Am/pm marker Text PM H Hour in day (0-23) Number 0 k Hour in day (1-24)
 * Number 24 K Hour in am/pm (0-11) Number 0 h Hour in am/pm (1-12) Number 12 m
 * Minute in hour Number 30 s Second in minute Number 55 S Millisecond Number
 * 978 z Time zone General time zone Pacific Standard Time; PST; GMT-08:00 Z
 * Time zone RFC 822 time zone -0800
 */
public class dateUtility {
	
	public static String longToDate(String time) {
		String DATEFORMAT = "HH:mm:ss dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		long t = 0;
		try {
			t = Long.parseLong(time);
		} catch (NumberFormatException e) {
			return "";
		}
		Date d = new Date(t);
		return sdf.format(d);
	}

	public static String dateToLong(String time) {
		try {
			Date d = myParse(time);
			return Long.toString(d.getTime());
		} catch(NumberFormatException e) {
			return "";
		}
	}

	private static Date myParse(String dateString) throws NumberFormatException {
		int year = Integer.parseInt(dateString.substring(0, 4));
		int month = Integer.parseInt(dateString.substring(5, 7));
		int day = Integer.parseInt(dateString.substring(8, 10));
		int hour = Integer.parseInt(dateString.substring(11, 13));
		int minute = Integer.parseInt(dateString.substring(14, 16));
		int second = Integer.parseInt(dateString.substring(17, 19));
		Date d = new Date(year - 1900, month - 1, day, hour, minute, second);
		return d;
	}
}
