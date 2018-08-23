package com.example.common.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final String STR_DATE_WITH_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String STR_M_D_H_M = "MM-dd HH:mm";

    public static final String STR_Y = "yyyy";

    public static final long HOUR = 3600000L;

    public static final long DAY_IN_MILLIS = 86400000L;

    private static Calendar calendarInstance = Calendar.getInstance();

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_WITHOUT_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static final SimpleDateFormat MONTH_DAY = new SimpleDateFormat("MM月dd日", Locale.getDefault());
    public static final SimpleDateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());

    public static final long FIVEMINUTE = 300000;
    public static final SimpleDateFormat HOUR_MINUETE = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final SimpleDateFormat MONTH_DAY2 = new SimpleDateFormat("MM-dd EEE", Locale.getDefault());
    public static final SimpleDateFormat M_D_H_M = new SimpleDateFormat("MM-dd HH:mm");
    public static final SimpleDateFormat M_D_H_CN = new SimpleDateFormat("MM月dd日HH时");
    public static final SimpleDateFormat Y_M_D_H_M_CN = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
    public static final SimpleDateFormat Y_M_D_CN = new SimpleDateFormat("yyyy年MM月dd日");

    public static final SimpleDateFormat IM_DATE_TIME = new SimpleDateFormat("M月d日 HH:mm", Locale.getDefault());
    public static final SimpleDateFormat IM_YEAR_DATE_FORMAT = new SimpleDateFormat("yyyy年M月d日 HH:mm", Locale.getDefault());
    public static final SimpleDateFormat DATE_WITH_MILLIS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    public static final SimpleDateFormat UTC_WITH_MILLIS = new SimpleDateFormat("E MMM dd HH:mm:ssSSS ZZZZ yyyy", Locale.UK);

    private static long mFetchServiceTime;
    private static long mCurrentServiceTime;

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * 返回指定日期的格式化字符串
     *
     * @param date 日期
     * @return yyyy-MM-dd
     */
    public static String getDateStr(Date date) {
        if (Null.isNull(date)) {
            return StringUtils.EMPTY;
        }
        return DATE_FORMAT_DATE.format(date);
    }

    public static String getDateHM(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return StringUtils.EMPTY;
        }
        Date date = new Date(utcTime);
        return HOUR_MINUETE.format(date);
    }

    /**
     * 获取格式化时间 如10-12 星期一
     *
     * @param date
     * @return
     */
    public static String getDateMDW(Date date) {
        if (Null.isNull(date)) {
            return StringUtils.EMPTY;
        }
        return MONTH_DAY2.format(date);
    }

    /**
     * 获取格式化时间 如10-12 星期一
     *
     * @param utcTime
     * @return
     */
    public static String getDateMDW(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return StringUtils.EMPTY;
        }
        Date date = new Date(utcTime);
        return MONTH_DAY2.format(date);
    }

    public static String getMDHM(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return StringUtils.EMPTY;
        }
        Date date = new Date(utcTime);
        return M_D_H_M.format(date);
    }

    /**
     * <p>
     * Description: 根据给定时间，获取新格式的时间串
     * <p>
     *
     * @param strTime        待转格式的时间串
     * @param strSrcTimeType 原时间字符串描述，形如yyyy.MM.dd HH:mm:ss
     * @param strDesTimeType 新时间字符串描述，形如MM.dd.yyyy HH:mm:ss
     * @return 新格式的字符串 例如04.25.2013 02:35:28, 错误返回null
     * @date 2013-4-25
     * @author yijiangtao 10140151
     */
    public static String transformTimeFormat(String strTime, String strSrcTimeType,
                                             String strDesTimeType) {
        if (TextUtils.isEmpty(strSrcTimeType)
                || TextUtils.isEmpty(strDesTimeType)
                || TextUtils.isEmpty(strTime)) {
            return null;
        }
        if (!checkTimeFormatType(strTime, strSrcTimeType)) {
            return null;
        }

        String strNewTypeTime;
        Date dateTime = null;
        SimpleDateFormat sdfDateFormat = null;
        try {
            dateTime = getDateByStrDate(strTime, strSrcTimeType);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }

        sdfDateFormat = new SimpleDateFormat(strDesTimeType);
        strNewTypeTime = sdfDateFormat.format(dateTime);

        return strNewTypeTime;
    }

    /**
     * <p>
     * Description: 根据给定的字符串日期和对应的格式信息，解析出日期
     * <p>
     *
     * @param strDate       字符串日期，如"2013-4-24 09:12:32"
     * @param strFormatType 字符串日期对应的格式字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return 日期, 错误返回null
     * @throws ParseException 日期解析错误
     * @date 2017-8-22
     * @author yijiangtao
     */
    public static Date getDateByStrDate(String strDate, String strFormatType)
            throws ParseException {
        if (TextUtils.isEmpty(strDate) || TextUtils.isEmpty(strFormatType)) {
            return null;
        }

        if (!checkTimeFormatType(strDate, strFormatType)) {
            return null;
        }
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat(strFormatType);
        return sdfDateFormat.parse(strDate);
    }

    /**
     * <p>
     * Description: 判断时间是否为指定格式
     * <p>
     *
     * @param strDate     时间串
     * @param strTimeType 待识别时间格式
     * @return 格式相符返回true，错误或异常返回false
     * @date 2017-8-22
     * @author yijiangtao
     */
    public static boolean checkTimeFormatType(String strDate, String strTimeType) {
        if (TextUtils.isEmpty(strDate) || TextUtils.isEmpty(strTimeType)) {
            return false;
        }

        Date date = null;
        DateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(strTimeType);
            date = dateFormat.parse(strDate);
        } catch (Exception e) {
            //如果不能转换,肯定是错误格式
            return false;
        }
//        String strNewTime = dateFormat.format(date);
//        // 转换后的日期再转换回String,如果不等,逻辑错误.如format为"yyyy-MM-dd",date为
//        // "2013-04-24",转换为日期后再转换回字符串为"2013-03-03",说明格式虽然对,但日期
//        // 逻辑上不对.
//        return strDate.equals(strNewTime);
        return true;
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string
     *
     * @param utcTime
     * @param dateFormat
     * @return
     */
    public static String getTime(String utcTime, SimpleDateFormat dateFormat) {
        Date date = null;
        try {
            date = DATE_WITH_MILLIS_FORMAT.parse(utcTime);
        } catch (Exception e) {
            date = new Date(System.currentTimeMillis());
        }
        return dateFormat.format(date);
    }

    public static String getFormatTime(String dateTime, SimpleDateFormat dateFormat) {
        String result = null;
        try {
            result = dateFormat.format(DEFAULT_DATE_FORMAT.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
            result = getCurrentServiceTime();
        }
        return result;
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static String getTimeInStrByFormater(Date date, SimpleDateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return StringUtils.EMPTY;
        }
        return dateFormat.format(date);
    }

    public static String getTimeInStrByFormater(String utcTimeStr, SimpleDateFormat dateFormat) {
        if (StringUtils.isEmpty(utcTimeStr)) {
            return StringUtils.EMPTY;
        }
        try {
            Date date = new Date(utcTimeStr);
            return dateFormat.format(date);
        } catch (Exception e) {
            Logger.e("convert time by utcTimeStr:%s error:%s", utcTimeStr, e);
        }
        return StringUtils.EMPTY;
    }

    public static long getDayInMillis(String utcTimeStr) {
        if (StringUtils.isEmpty(utcTimeStr)) {
            return System.currentTimeMillis();
        }
        try {
            Date date = new Date(utcTimeStr);
            return date.getTime();
        } catch (Exception e) {
            Logger.e("convert utc time: %s error:%s", utcTimeStr, e);
        }
        return System.currentTimeMillis();
    }

    /**
     * 获取时间毫秒值
     *
     * @param utcTimeStr utc格式时间
     * @param timeAdd    时间偏移量 毫秒值
     * @return
     */
    public static long getDayINMillis(String utcTimeStr, long timeAdd) {
        return getDayInMillis(utcTimeStr) + timeAdd;
    }

    /**
     * @param time
     * @return
     */
    public static boolean isOccured(long time) {
        return time <= System.currentTimeMillis();
    }

    /**
     * 判断时间是否已到达
     *
     * @param utcTimeStr
     * @return
     */
    public static boolean isOccured(String utcTimeStr) {
        if (StringUtils.isEmpty(utcTimeStr)) {
            return true;
        }
        try {
            Date date = new Date(utcTimeStr);
            return date.getTime() <= System.currentTimeMillis();
        } catch (Exception e) {
            Logger.e("convert time by utcTimeStr:%s error:%s", utcTimeStr, e);
        }
        return false;
    }

    /**
     * 判断时间是否已到达
     *
     * @param time        时间毫秒值
     * @param timeAdvance 时间提前量 millisecond
     * @return
     */
    public static boolean isOccured(long time, long timeAdvance) {
        return time <= System.currentTimeMillis() + timeAdvance;
    }

    /**
     * 判断时间是否已到达
     *
     * @param utcTimeStr  UTC格式时间
     * @param timeAdvance 时间提前量 millisecond
     * @return
     */
    public static boolean isOccured(String utcTimeStr, long timeAdvance) {
        if (StringUtils.isEmpty(utcTimeStr)) {
            return true;
        }
        try {
            Date date = new Date(utcTimeStr);
            return date.getTime() <= System.currentTimeMillis() + timeAdvance;
        } catch (Exception e) {
            Logger.e("convert time by utcTimeStr:%s error:%s", utcTimeStr, e);
        }
        return false;
    }

    /**
     * 是否为同一天
     *
     * @param utcTime1
     * @param utcTime2
     * @return
     */
    public static boolean isSameDay(String utcTime1, String utcTime2) {
        if (StringUtils.isEmpty(utcTime1) || StringUtils.isEmpty(utcTime2)) {
            return false;
        }

        if (utcTime1.equals(utcTime2)) {
            return true;
        }

        Date d1 = new Date(utcTime1);
        Date d2 = new Date(utcTime2);
        return String.format("%s-%s-%s", d1.getYear(), d1.getMonth(), d1.getDay()).equals(String.format("%s-%s-%s", d2.getYear(), d2.getMonth(), d2.getDay()));
    }

    /**
     * 是否为同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameDay(long time1, long time2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTimeInMillis(time1);
        c2.setTimeInMillis(time2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 是否为同一天
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean isSameDay(Calendar c1, Calendar c2) {
        if (Null.isNull(c1) || Null.isNull(c2)) {
            return false;
        }
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 是否为同一天
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameDay(Date d1, Date d2) {
        if (Null.isNull(d1) || Null.isNull(d2)) {
            return false;
        }
        return d1.getYear() == d2.getYear()
                && d1.getMonth() == d2.getMonth()
                && d1.getDate() == d2.getDate();
    }

    public static String getTimeHumanShow(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return "";
        }
        Date date = null;
        Date dateToday = new Date(System.currentTimeMillis());
        try {
            date = new Date(utcTime);
        } catch (Exception e) {
            //若时间转换出错，则使用当天时间
            date = dateToday;
        }
        if (date.getYear() != dateToday.getYear()) { // 跨年
            return getTimeInStrByFormater(date, YEAR_MONTH_DAY);
        } else if (date.getMonth() != dateToday.getMonth()
                || date.getDate() != dateToday.getDate()) { // 非同一天

            long minYesterday = getYesterdayMinTimeMillis();
            long maxYesterday = minYesterday + DAY_IN_MILLIS;

            long targetTime = date.getTime();

            if (targetTime > minYesterday && targetTime < maxYesterday) {
                return getTimeInStrByFormater(date, MONTH_DAY) + "(昨天)";
            }

            return getTimeInStrByFormater(date, MONTH_DAY);
        } else { // 当天
            return getTimeInStrByFormater(date, MONTH_DAY) + "(今天)";
        }
    }

    public static String getDateInHumanShow(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return "";
        }

        Date date = null;
        Date dateToday = new Date(System.currentTimeMillis());
        try {
            date = new Date(utcTime);
        } catch (Exception e) {
            //若时间转换出错，则使用当天时间
            date = dateToday;
        }

        if (date.getYear() != dateToday.getYear()) { // 跨年
            return getTimeInStrByFormater(date, YEAR_MONTH_DAY);
        } else {
            return getTimeInStrByFormater(date, MONTH_DAY);
        }

    }

    /**
     * 给定一个时间日期，能得出是周几
     */
    public static String getTimeWeekShow(String utcTime) {
        if (StringUtils.isEmpty(utcTime)) {
            return "";
        }

        String week = "";

        Calendar c = Calendar.getInstance(Locale.getDefault());

        Calendar cToday = Calendar.getInstance(Locale.getDefault());
        cToday.setTimeInMillis(System.currentTimeMillis());

        try {
            c.setTime(new Date(utcTime));
        } catch (Exception e) {
            //若时间转换出错，则使用当天时间
            c.setTimeInMillis(System.currentTimeMillis());
            Logger.d("time change error");
        }

        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week = "周日";
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            week = "周一";
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            week = "周二";
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            week = "周三";
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            week = "周四";
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            week = "周五";
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            week = "周六";
        }

        if (c.get(Calendar.YEAR) != cToday.get(Calendar.YEAR)) { // 跨年
            return getTimeInStrByFormater(c.getTime(), YEAR_MONTH_DAY) + "(" + week + ")";
        } else {
            return getTimeInStrByFormater(c.getTime(), MONTH_DAY) + "(" + week + ")";
        }
    }

    /**
     * 根据服务器时间 判断给的日期是当前日期的今天，明天，后天
     * @param utcTime 格式"yyyy-MM-dd HH:mm:ss.SSS"
     * @return
     */
    public static String getDateShow(String utcTime){
        Logger.d("时间：" + utcTime);
        Date date = null;
        try {
            date = DATE_WITH_MILLIS_FORMAT.parse(utcTime);
//            date = new Date(utcTime);
        } catch (Exception e) {
            date = new Date(System.currentTimeMillis());
        }

        long targetTime = date.getTime();

        long maxTomorrow = getTomorrowMaxTimeMillisInService();

        long minTomorrow = maxTomorrow - DAY_IN_MILLIS;
        long houtian = maxTomorrow + DAY_IN_MILLIS;

        Date dateToday = new Date(getCurrentServiceTimetamp());

        String dayFlag = StringUtils.EMPTY;

        if (isSameDay(targetTime, dateToday.getTime())) {
            dayFlag = "今天";
        } else if (targetTime <= maxTomorrow && targetTime > minTomorrow) {
            dayFlag = "明天";
        } else if (targetTime <= houtian && targetTime > maxTomorrow) {
            dayFlag = "后天";
        } else {
            dayFlag = "";
        }
        return dayFlag;
    }

    /**
     * 判断给的日期是当前日期的今天，明天，后天
     *
     * @param utcTime
     * @return
     */
    public static String getTomorrowShow(String utcTime) {

        Logger.d("时间：" + utcTime);
        Date date = null;
        try {
            date = DATE_WITH_MILLIS_FORMAT.parse(utcTime);
//            date = new Date(utcTime);
        } catch (Exception e) {
            date = new Date(System.currentTimeMillis());
        }

        long targetTime = date.getTime();

        long maxTomorrow = getTomorrowMaxTimeMillis();

        long minTomorrow = maxTomorrow - DAY_IN_MILLIS;
        long houtian = maxTomorrow + DAY_IN_MILLIS;

        Date dateToday = new Date(System.currentTimeMillis());

        String dayFlag = StringUtils.EMPTY;

        if (isSameDay(targetTime, dateToday.getTime())) {
            dayFlag = "今天";
        } else if (targetTime <= maxTomorrow && targetTime > minTomorrow) {
            dayFlag = "明天";
        } else if (targetTime <= houtian && targetTime > maxTomorrow) {
            dayFlag = "后天";
        } else {
            dayFlag = "";
        }
        return dayFlag;
    }

    /**
     * 获取明天最迟时间的毫秒数
     *
     * @return
     */
    public static long getTomorrowMaxTimeMillisInService() {
        long currentTime = getCurrentServiceTimetamp();
        calendarInstance.setTimeInMillis(currentTime);
        int year = calendarInstance.get(Calendar.YEAR);
        int month = calendarInstance.get(Calendar.MONTH);
        int day = calendarInstance.get(Calendar.DAY_OF_MONTH);
        calendarInstance.set(year, month, day, 23, 59, 59);
        return calendarInstance.getTimeInMillis() + DAY_IN_MILLIS;
    }

    /**
     * 获取昨天最早时间的毫秒数
     *
     * @return
     */
    public static long getYesterdayMinTimeMillis() {
        long currentTime = System.currentTimeMillis();
        calendarInstance.setTimeInMillis(currentTime);
        int year = calendarInstance.get(Calendar.YEAR);
        int month = calendarInstance.get(Calendar.MONTH);
        int day = calendarInstance.get(Calendar.DAY_OF_MONTH);
        calendarInstance.set(year, month, day, 0, 0, 0);
        return calendarInstance.getTimeInMillis() - DAY_IN_MILLIS;
    }

    /**
     * 获取明天最迟时间的毫秒数
     *
     * @return
     */
    public static long getTomorrowMaxTimeMillis(long currentTime) {
        calendarInstance.setTimeInMillis(currentTime);
        int year = calendarInstance.get(Calendar.YEAR);
        int month = calendarInstance.get(Calendar.MONTH);
        int day = calendarInstance.get(Calendar.DAY_OF_MONTH);
        calendarInstance.set(year, month, day, 23, 59, 59);
        return calendarInstance.getTimeInMillis() + DAY_IN_MILLIS;
    }

    /**
     * 获取明天最迟时间的毫秒数
     *
     * @return
     */
    public static long getTomorrowMaxTimeMillis() {
        return getTomorrowMaxTimeMillis(System.currentTimeMillis());
    }

    /**
     * 获取昨天最晚时间的毫秒数
     *
     * @return
     */
    public static long getYesterdayMaxTimeMillis() {
//        long currentTime = System.currentTimeMillis();
//        calendarInstance.setTimeInMillis(currentTime);
//        int year = calendarInstance.get(Calendar.YEAR);
//        int month = calendarInstance.get(Calendar.MONTH);
//        int day = calendarInstance.get(Calendar.DAY_OF_MONTH);
//        calendarInstance.set(year, month, day, 23, 59, 59);
//        return calendarInstance.getTimeInMillis() - DAY_IN_MILLIS;

        return getYesterdayMinTimeMillis() + DAY_IN_MILLIS - 1;
    }

    /**
     * 计算两个时间之间的组合差值！
     *
     * @param lastDate
     * @return 昨天22:08 | 06-20 15:36
     */
    public static String getTimeDiff(Date lastDate) {
        if (lastDate == null) return "刚刚";
        Calendar currCalendar = Calendar.getInstance();
        currCalendar.setTimeInMillis(System.currentTimeMillis());
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(lastDate);
        if (currCalendar.before(lastCalendar)) {
            return "刚刚";
        }
        int currYear = currCalendar.get(Calendar.YEAR);
        int lastYear = lastCalendar.get(Calendar.YEAR);
        String tmp = "";

        if (currYear == lastYear) {
            long t1 = currCalendar.getTimeInMillis();
            long t2 = lastDate.getTime();
            int hours = (int) ((t1 - t2) / 3600000);
            if (hours > 47) {
                tmp = MONTH_DAY.format(lastDate);
            } else if (hours > 23 && hours <= 47) {
                tmp = "昨天";
            } else if (hours > 0 && hours <= 23) {
                tmp = hours + "小时之前";
            } else if (hours == 0) {
                int minutes = (int) ((t1 - t2) / 21600000);
                if (minutes > 1) {
                    tmp = minutes + "分钟之前";
                } else {
                    tmp = "刚刚";
                }
            }
        } else {
            tmp = YEAR_MONTH_DAY.format(lastDate);
        }
        return tmp;
    }

    /**
     * 时间间隔
     *
     * @param msgTime
     * @param msgTime1
     * @param i
     * @return
     */
    public static boolean timeInterval(long msgTime, long msgTime1, int i) {
        return Math.abs(msgTime - msgTime1) > i;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param dateBase
     * @param date
     * @return
     */
    public static int daysBetween(Date dateBase, Date date) {
        Calendar calendarBase = Calendar.getInstance();
        calendarBase.setTime(dateBase);
        int dayBase = calendarBase.get(Calendar.DAY_OF_YEAR);
        calendarBase.setTime(date);
        int dayTarget = calendarBase.get(Calendar.DAY_OF_YEAR);
        return dayBase - dayTarget;
    }

    /**
     * 日期标签
     */
    public static String[] dayLabel = new String[]{"明", "今", "昨"};

    public static Calendar setCalendarTodayStart(Calendar c) {
        if (Null.isNull(c)) {
            c = Calendar.getInstance();
        }
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c;
    }

    public static Calendar setCalendarTodayEnd(Calendar c) {
        if (Null.isNull(c)) {
            c = Calendar.getInstance();
        }
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c;
    }

    public static String imTime(long time, long lastTime) {

        String imTime = "";
        long nowTime = System.currentTimeMillis();
        if (nowTime - time <= 60000) {
            if (time - lastTime <= 60000) {
                imTime = "";
            } else {
                imTime = "刚刚";
            }
        } else if (isSameDay(time, nowTime)) {
            if (time - lastTime <= 60000) {
                imTime = "";
            } else {
                imTime = getTime(time, HOUR_MINUETE);
            }
        } else if (!isSameDay(time, nowTime)) {
            Date nowDate = new Date(nowTime);
            Date date = new Date(time);
            if (date.getYear() == nowDate.getYear()) {
                if (time - lastTime <= 60000) {
                    imTime = "";
                } else {
                    long minYesterday = getYesterdayMinTimeMillis();
                    long maxYesterday = minYesterday + DAY_IN_MILLIS;
                    if (time > minYesterday && time < maxYesterday) {
                        imTime = "昨天 " + getTime(time, HOUR_MINUETE);
                    } else {
                        imTime = getTime(time, IM_DATE_TIME);
                    }
                }
            } else {
                if (time - lastTime <= 60000) {
                    imTime = "";
                } else {
                    imTime = getTime(time, IM_YEAR_DATE_FORMAT);
                }
            }

        }
        return imTime;
    }

    public static Date parseUtcWithMillis(String utc, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(utc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param millis
     * @return
     */
    public static String getTimeDiffForLoginTime(long millis) {
        String tmp;
        int minutes = (int) (millis / 60000);
        int hours = minutes / 60;
        if (hours > 0) {
            int leftMinutes = minutes - (hours * 60);
            if (leftMinutes > 0) {
                tmp = hours + "小时" + leftMinutes + "分钟";
            } else {
                tmp = hours + "小时";
            }
        } else {
            if (minutes == 0) {
                minutes = 1;
            }
            tmp = minutes + "分钟";
        }

        return tmp;
    }

    /**
     * 记录获取服务器时间的时间差
     */
    public static void setFetchServiceTime(String currentServiceTime) {
        mFetchServiceTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(currentServiceTime))
            return;
        try {
            mCurrentServiceTime = DATE_WITH_MILLIS_FORMAT.parse(currentServiceTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            mCurrentServiceTime = new Date().getTime();
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentServiceTime() {
        long currentTime = System.currentTimeMillis();
        return DATE_WITH_MILLIS_FORMAT.format(new Date(mCurrentServiceTime + (currentTime - mFetchServiceTime)));
    }

    /**
     * 获取当前服务器时间
     *
     * @return
     */
    public static String getCurrentServiceTimeWithoutMillis() {
        long currentTime = System.currentTimeMillis();
        return DATE_FORMAT_DATE.format(new Date(mCurrentServiceTime + (currentTime - mFetchServiceTime)));
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getCurrentServiceTimetamp() {
        long currentTime = System.currentTimeMillis();
        return mCurrentServiceTime + (currentTime - mFetchServiceTime);
    }

    /**
     * 判断时间是否已到达
     *
     * @param utcTimeStr
     * @return
     */
    public static boolean isOccuredServeice(String utcTimeStr) {
        if (StringUtils.isEmpty(utcTimeStr)) {
            return true;
        }
        try {
            return DATE_FORMAT_WITHOUT_SECOND.parse(utcTimeStr).getTime() <= getCurrentServiceTimetamp();
        } catch (Exception e) {
            Logger.e("convert time by utcTimeStr:%s error:%s", utcTimeStr, e);
        }
        return false;
    }


    /**
     * 获取今天最早时间
     *
     * @return
     */
    public static String getTodayMinTimeMillis() {
        long currentTime = System.currentTimeMillis();
        calendarInstance.setTimeInMillis(currentTime);
        int year = calendarInstance.get(Calendar.YEAR);
        int month = calendarInstance.get(Calendar.MONTH);
        int day = calendarInstance.get(Calendar.DAY_OF_MONTH);
        calendarInstance.set(year, month, day, 0, 0, 0);
        return DATE_WITH_MILLIS_FORMAT.format(new Date(calendarInstance.getTimeInMillis() + 1000));
    }

    /**
     * 获取今天最晚时间
     *
     * @return
     */
    public static String getTodayMaxTimeMillis() {
        long currentTime = System.currentTimeMillis();
        calendarInstance.setTimeInMillis(currentTime);
        int year = calendarInstance.get(Calendar.YEAR);
        int month = calendarInstance.get(Calendar.MONTH);
        int day = calendarInstance.get(Calendar.DAY_OF_MONTH);
        calendarInstance.set(year, month, day, 0, 0, 0);
        return DATE_WITH_MILLIS_FORMAT.format(new Date(calendarInstance.getTimeInMillis() + DAY_IN_MILLIS - 1000));
    }

    /**
     * 判断是否在时间范围内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isTimeRange(String startTime, String endTime) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int minuteOfDay = hour * 60 + minute;

//        Logger.i(startTime.substring(0, 2));
//        Logger.i(startTime.substring(3, 5));
//        Logger.i(endTime.substring(0, 2));
//        Logger.i(endTime.substring(3, 5));

        final int start = Integer.parseInt(startTime.substring(0, 2)) * 60 + Integer.parseInt(startTime.substring(3, 5));
        final int end = Integer.parseInt(endTime.substring(0, 2)) * 60 + Integer.parseInt(endTime.substring(3, 5));
        boolean result = false;
        if (minuteOfDay >= start && minuteOfDay <= end) {
            result = true;
        } else {
            result = false;
        }
        return result;

//        if (minuteOfDay >= start && minuteOfDay <= end) {
//           return true;
//        } else {
//           return false;
//        }
    }

    /**
     * 判断time是否在时间范围(startTime, endTime)内
     *
     * @param time      需要判断的时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isTimeRange(long time, String startTime, String endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int minuteOfDay = hour * 60 + minute;

//        Logger.i(startTime.substring(0, 2));
//        Logger.i(startTime.substring(3, 5));
//        Logger.i(endTime.substring(0, 2));
//        Logger.i(endTime.substring(3, 5));

        final int start = Integer.parseInt(startTime.substring(0, 2)) * 60 + Integer.parseInt(startTime.substring(3, 5));
        final int end = Integer.parseInt(endTime.substring(0, 2)) * 60 + Integer.parseInt(endTime.substring(3, 5));
        boolean result = false;
        if (minuteOfDay >= start && minuteOfDay <= end) {
            result = true;
        } else {
            result = false;
        }
        return result;

//        if (minuteOfDay >= start && minuteOfDay <= end) {
//           return true;
//        } else {
//           return false;
//        }
    }

    public static long string2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long string2Millis(String time, SimpleDateFormat pattern) {
        try {
            return pattern.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long string2MillisThrowException(String time, SimpleDateFormat pattern) throws ParseException {
        long timetamp = -1;
        timetamp = pattern.parse(time).getTime();
        return timetamp;
    }
}
