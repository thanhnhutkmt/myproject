package util;

import android.app.Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import software.nhut.personalutilitiesforlife.constant.AppConstant;

/**
 * Created by Nhut on 6/20/2016.
 */
public class MyDateTime {
    public static long getTimeInMiliSeconds(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return d.getTime();
    }

    public static Date getTime(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return (new Date(d.getTime()));
    }

    public static Locale getAppLocale(Activity a) {
        Locale currentLocale = a.getResources().getConfiguration().locale;
        if (currentLocale == Locale.US || currentLocale == Locale.ENGLISH || currentLocale == Locale.UK)
            return Locale.US;
        else return currentLocale;
    }

    public static String getDateString(String miliSeconds, String format) {
        long ms = Long.parseLong(miliSeconds);
        Date d = new Date(ms);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static String getDateString(long miliSeconds, String format) {
        Date d = new Date(miliSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static long setTimeToZero(Date d) {
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        long temp = d.getTime()/1000;
        return temp * 1000;
    }
}
