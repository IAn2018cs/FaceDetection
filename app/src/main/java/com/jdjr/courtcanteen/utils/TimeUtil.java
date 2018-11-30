package com.jdjr.courtcanteen.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class TimeUtil {
    public static final int CODE_BREAKFAST = 0;
    public static final int CODE_DINNER = 2;
    public static final int CODE_LUNCH = 1;
    public static final int CODE_OTHER = 3;

    public static long getDayLong() {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = localSimpleDateFormat.format(new Date());
        try {
            long l = localSimpleDateFormat.parse(str).getTime();
            return l;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return 0L;
    }

    public static String getDayString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static long getDayTimeLong() {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = localSimpleDateFormat.format(new Date());
        try {
            long l = localSimpleDateFormat.parse(str).getTime();
            return l;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return 0L;
    }

    public static String getDayTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static int getEatTime() {
        long l1 = parseTime("07:30:00");
        long l2 = parseTime("09:00:00");
        long l3 = parseTime("12:00:00");
        long l4 = parseTime("13:30:00");
        long l5 = parseTime("18:00:00");
        long l6 = parseTime("19:00:00");
        long l7 = getTimeLong();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("dinnerStart:");
        localStringBuilder.append(l5);
        Log.d("getEatTime", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("nowTime:");
        localStringBuilder.append(l7);
        Log.d("getEatTime", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("dinnerEnd:");
        localStringBuilder.append(l6);
        Log.d("getEatTime", localStringBuilder.toString());
        int k = 1;
        int i;
        if ((l7 >= l1) && (l7 <= l2))
            i = 1;
        else
            i = 0;
        int j;
        if ((l7 >= l3) && (l7 <= l4))
            j = 1;
        else
            j = 0;
        if ((l7 < l5) || (l7 > l6))
            k = 0;
        if (i != 0)
            return 0;
        if (j != 0)
            return 1;
        if (k != 0)
            return 2;
        return 3;
    }

    public static int getFormalEatTime() {
        long l1 = parseTime("07:30:00");
        long l2 = parseTime("09:00:00");
        long l3 = parseTime("12:00:00");
        long l4 = parseTime("13:30:00");
        long l5 = parseTime("18:00:00");
        long l6 = parseTime("19:00:00");
        long l7 = getTimeLong();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("dinnerStart:");
        localStringBuilder.append(l5);
        Log.d("getEatTime", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("nowTime:");
        localStringBuilder.append(l7);
        Log.d("getEatTime", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("dinnerEnd:");
        localStringBuilder.append(l6);
        Log.d("getEatTime", localStringBuilder.toString());
        int k = 1;
        int i;
        if ((l7 >= l1) && (l7 <= l2))
            i = 1;
        else
            i = 0;
        int j;
        if ((l7 >= l3) && (l7 <= l4))
            j = 1;
        else
            j = 0;
        if ((l7 < l5) || (l7 > l6))
            k = 0;
        if (i != 0)
            return 0;
        if (j != 0)
            return 1;
        if (k != 0)
            return 2;
        return 3;
    }

    public static long getIntervalEatTime() {
        long l1 = parseTime("07:30:00");
        long l6 = parseTime("09:00:00");
        long l5 = parseTime("12:00:00");
        long l7 = parseTime("13:30:00");
        long l4 = parseTime("18:00:00");
        long l8 = parseTime("19:00:00");
        long l2 = parseTime("23:59:59");
        long l3 = getTimeLong();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("dinnerStart:");
        localStringBuilder.append(l4);
        Log.d("getEatTime", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("nowTime:");
        localStringBuilder.append(l3);
        Log.d("getEatTime", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("dinnerEnd:");
        localStringBuilder.append(l8);
        Log.d("getEatTime", localStringBuilder.toString());
        if (((l3 < l1) || (l3 <= l6)) || (((l3 < l5) || (l3 <= l7)) || ((l3 >= l4) && (l3 <= l8))))
            ;
        l6 = l1 - l3;
        l5 -= l3;
        l4 -= l3;
        if (l6 > 0L)
            return l6;
        if (l5 > 0L)
            return l5;
        if (l4 > 0L)
            return l4;
        return l2 - l3 + l1;
    }

    public static long getTimeLong() {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String str = localSimpleDateFormat.format(new Date());
        try {
            long l = localSimpleDateFormat.parse(str).getTime();
            return l;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return 0L;
    }

    public static String getTimeString() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static long parseDay(String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long l = localSimpleDateFormat.parse(paramString).getTime();
            return l;
        } catch (ParseException paramString) {
            paramString.printStackTrace();
        }
        return 0L;
    }

    public static long parseDayTime(String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long l = localSimpleDateFormat.parse(paramString).getTime();
            return l;
        } catch (ParseException paramString) {
            paramString.printStackTrace();
        }
        return 0L;
    }

    public static long parseTime(String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            long l = localSimpleDateFormat.parse(paramString).getTime();
            return l;
        } catch (ParseException paramString) {
            paramString.printStackTrace();
        }
        return 0L;
    }
}
