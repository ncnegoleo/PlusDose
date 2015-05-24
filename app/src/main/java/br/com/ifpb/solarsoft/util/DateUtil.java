package br.com.ifpb.solarsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leonardo on 18/05/2015.
 */
public class DateUtil {

    public static Date parseStringToDate(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date parseCalendarToDate(Calendar cal) {
        return cal.getTime();
    }

    public static Calendar parseDateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static int compareDates(Date d1, Date d2) {
        if(d1.getTime() == d2.getTime()) {
            return 0;
        } else if(d1.getTime() > d2.getTime()) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int compareDates(Date d1, Calendar d2) {
        if(d1.getTime() == d2.getTimeInMillis()) {
            return 0;
        } else if(d1.getTime() > d2.getTimeInMillis()) {
            return 1;
        } else {
            return -1;
        }
    }
    public static int compareDates(Calendar d1, Date d2) {
        if(d1.getTimeInMillis() == d2.getTime()) {
            return 0;
        } else if(d1.getTimeInMillis() > d2.getTime()) {
            return 1;
        } else {
            return -1;
        }
    }
    public static int compareDates(Calendar d1, Calendar d2) {
        if(d1.getTime() == d2.getTime()) {
            return 0;
        } else if(d1.getTimeInMillis() > d2.getTimeInMillis()) {
            return 1;
        } else {
            return -1;
        }
    }
}
