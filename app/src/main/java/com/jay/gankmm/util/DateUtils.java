package com.jay.gankmm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jay on 16/2/24.
 */
public class DateUtils {

  public static String toDate(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    return dateFormat.format(date);
  }

  public static String toDate(Date date, int add) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, add);
    return toDate(calendar.getTime());
  }

}

