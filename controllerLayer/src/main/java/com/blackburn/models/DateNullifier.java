package com.blackburn.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateNullifier {
    public static Date NullifyTime(Date toNullify) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(toNullify);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
