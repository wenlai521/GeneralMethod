package com.tnnowu.android.generalmethod.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.currentTimeMillis;

/**
 * Created by Tnno Wu on 2017/04/27.
 */

public class DateUtil {

    private static SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
    private static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
    private static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
    private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

    /**
     * 获取当前年月日时分秒
     * <p>
     * yyyy-MM-dd HH:mm:ss
     * <p>
     * e.g. 2017-4-28 13:55:56
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate_YMDHMS() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取当前月日时分秒
     * <p>
     * MM-dd HH:mm:ss
     * <p>
     * e.g. 4-28 13:55:56
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate_MDHMS() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    /**
     * 获取当前年月日
     * yyyy-MM-dd
     * <p>
     * e.g. 2017-04-28
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate_YMD() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return mDateFormat.format(new Date());
    }

    public static String getYmd(long timeInMills) {
        return ymd.format(new java.util.Date(timeInMills));
    }

    /**
     * 年月日[2015.07.28]
     */
    public static String getYmdDot(long timeInMills) {
        return ymdDot.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhms(long timeInMills) {
        return ymdhms.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhmsS(long timeInMills) {
        return ymdhmss.format(new java.util.Date(timeInMills));
    }

    public static String getYmdhm(long timeInMills) {
        return ymdhm.format(new java.util.Date(timeInMills));
    }

    public static String getHm(long timeInMills) {
        return hm.format(new java.util.Date(timeInMills));
    }

    public static String getMd(long timeInMills) {
        return md.format(new java.util.Date(timeInMills));
    }

    public static String getMdhm(long timeInMills) {
        return mdhm.format(new java.util.Date(timeInMills));
    }

    public static String getMdhmLink(long timeInMills) {
        return mdhmLink.format(new java.util.Date(timeInMills));
    }

    public static String getM(long timeInMills) {
        return m.format(new java.util.Date(timeInMills));
    }

    public static String getD(long timeInMills) {
        return d.format(new java.util.Date(timeInMills));
    }

    /**
     * 获取当前月日
     * MM-dd
     * <p>
     * e.g. 4-28
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate_MD(Date date) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd");
        return mDateFormat.format(date);
    }

    /**
     * 获取明天日期
     * Get tomorrow date
     * <p>
     * e.g. 20170428
     */
    public static String getTomorrowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return String.valueOf(Integer.valueOf(df.format(new Date())) + 1);
    }

    /**
     * 获取当前日期字符串
     * Get current date to string
     * <p>
     * e.g. TODO
     */
    public static String getCurrentDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 获取当前年
     * Get current year
     * <p>
     * e.g. 2017
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentYear(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * Get current month
     * <p>
     * e.g. 04
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    public static int getCurrentMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日
     * Get current day
     * <p>
     * e.g. 27
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    /**
     * 切割标准时间
     * Sub standard time
     * <p>
     * e.g. TODO
     */
    public static String subStandardTime(String time) {
        int idx = time.indexOf(".");
        if (idx > 0) {
            return time.substring(0, idx).replace("T", " ");
        }
        return null;
    }

    /**
     * 将时间戳转化为字符串
     * Format time to string
     * <p>
     * e.g. TODO
     */
    public static String formatTimeToString(long showTime) {
        return formatTimeToString(showTime, false);
    }

    public static String formatTimeToString(long showTime, boolean haveYear) {
        String str = "";
        long distance = currentTimeMillis() / 1000 - showTime;
        if (distance < 300) {
            str = "刚刚";
        } else if (distance >= 300 && distance < 600) {
            str = "5分钟前";
        } else if (distance >= 600 && distance < 1200) {
            str = "10分钟前";
        } else if (distance >= 1200 && distance < 1800) {
            str = "20分钟前";
        } else if (distance >= 1800 && distance < 2700) {
            str = "半小时前";
        } else if (distance >= 2700) {
            Date date = new Date(showTime * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = formatDateTime(sdf.format(date), haveYear);
        }
        return str;
    }

    public static String formatDateTime(String time, boolean haveYear) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (time == null) {
            return "";
        }
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else {
            if (haveYear) {
                int index = time.indexOf(" ");
                return time.substring(0, index);
            } else {
                int yearIndex = time.indexOf("-") + 1;
                int index = time.indexOf(" ");
                return time.substring(yearIndex, time.length()).substring(0, index);
            }
        }
    }

    /**
     * 将日期转化成字符串
     * Format date to string
     * <p>
     * e.g. TODO
     */
    public static String formatDateToString(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (time == null) {
            return "未知";
        }
        try {
            long createTime = format.parse(time).getTime() / 1000;
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - createTime - 24 * 3600 > 0) { //超出一天
                return (currentTime - createTime) / (24 * 3600) + "天前";
            } else {
                return (currentTime - createTime) / 3600 + "小时前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "未知";
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 判断当前日期是星期几
     * 0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
     */
    public static int getWeek(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }


    /**
     * 是否是今天
     */
    public static boolean isToday(long timeInMills) {
        String dest = getYmd(timeInMills);
        String now = getYmd(Calendar.getInstance().getTimeInMillis());
        return dest.equals(now);
    }

    /**
     * 是否是同一天
     */
    public static boolean isSameDay(long aMills, long bMills) {
        String aDay = getYmd(aMills);
        String bDay = getYmd(bMills);
        return aDay.equals(bDay);
    }

    /**
     * 获取月份的天数
     */
    public static int getDaysInMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    /**
     * 获取当月第一天的时间（毫秒值）
     */
    public static long getFirstOfMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

}
