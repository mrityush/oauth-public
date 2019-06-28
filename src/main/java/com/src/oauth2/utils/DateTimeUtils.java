package com.src.oauth2.utils;

import com.src.oauth2.constants.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    static final long ONE_MINUTE_IN_MILLIS = 60000;

    public static Map<Integer, String> days = new HashMap<Integer, String>() {{
        put(0, "Sunday");
        put(1, "Monday");
        put(2, "Tuesday");
        put(3, "Wednesday");
        put(4, "Thursday");
        put(5, "Wednesday");
        put(6, "Friday");
    }};
    private static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String fetchDateStringFromDate(Date date, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static String fetchDateStringFromDate(Date date, String dateFormat, String timeZoneOffSet) {
        SimpleDateFormat df = new SimpleDateFormat();
        try {
            df.setTimeZone(TimeZone.getTimeZone("GMT" + timeZoneOffSet));
        } catch (Exception ex) {
            logger.error("Error in fetching timezone. " + ex);
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static Date clearTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date convertToDate(String dateFormat, String dateValue) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            date = simpleDateFormat.parse(dateValue);
        } catch (Exception exc) {
            logger.error("Exception in parsing date. Setting date to null: " + exc);
        }
        return date;
    }

    public static Date addMinutesToDate(int minutes) {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        return new Date(t + (minutes * ONE_MINUTE_IN_MILLIS));
    }

    public static Date getTodaysDateOnly() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getTimeFromSeconds(int endTime) {
        int day = (int) TimeUnit.SECONDS.toDays(endTime);
        long hours = TimeUnit.SECONDS.toHours(endTime) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(endTime) - (TimeUnit.SECONDS.toHours(endTime) * 60);
        String hoursStr = getCorrectedHourMinuteString(hours);
        String minuteStr = getCorrectedHourMinuteString(minute);
        //NOT REQUIRED ACTUAllY
        long second = TimeUnit.SECONDS.toSeconds(endTime) - (TimeUnit.SECONDS.toMinutes(endTime) * 60);
        return hoursStr + ":" + minuteStr;
    }

    private static String getCorrectedHourMinuteString(long hours) {
        String hoursStr = String.valueOf(hours);
        if (hoursStr.length() == 1) {
            hoursStr = 0 + hoursStr;
        }
        return hoursStr;
    }

    public static int getSecondsFromTime(String timeDiv) {
        int hours = Integer.valueOf(timeDiv.split(":")[0]);
        int minutes = Integer.valueOf(timeDiv.split(":")[1]);
        return (hours * 3600 + minutes * 60);
    }

    public static Date getNextDayDateOnly() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 1);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.AM_PM, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        return calendar.getTime();
        return getDateOnlyAheadOrBehind(1, true);

    }

    public static Date getPrevDayDateOnly() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.AM_PM, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        return calendar.getTime();
        return getDateOnlyAheadOrBehind(1, false);
    }

    //false for behind
    //true for ahead
    public static Date getDateOnlyAheadOrBehind(int numDays, boolean positive) {
        Date now = getCurrentDate();
        return getDateOnlyAheadOrBehind(now, numDays, positive);
    }

    public static long getDifferenceInSeconds(Date date, Date timeStart) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        long dateInMilliSeconds = calendar1.getTimeInMillis();
        calendar1.setTime(timeStart);
        long timeStartInMills = calendar1.getTimeInMillis();
        return (dateInMilliSeconds - timeStartInMills) / 1000;
    }

    public static int getTimeLapsed(Date heldOnDate, Date todayDate) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(heldOnDate);
        calendar2.setTime(todayDate);
        int numDays = calendar2.get(Calendar.DAY_OF_YEAR) - calendar1.get(Calendar.DAY_OF_YEAR);
        return numDays;
    }

    public static Date getDateOnlyAheadOrBehind(Date date, int numDays, boolean positive) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (positive)
            calendar.add(Calendar.DATE, numDays);
        else
            calendar.add(Calendar.DATE, (0 - numDays));
        return calendar.getTime();
    }

    public static Date getDateByReqParam(HttpServletRequest request, String param) {
        String dateParamStr = request.getParameter(param);
        if (dateParamStr == null) {
            logger.error("Null Param passed for date ... ");
            return getCurrentDate();
        } else {
            return convertToDate(ApplicationConstant.YYYYMMDD_hyphen, dateParamStr);
        }
    }
}
