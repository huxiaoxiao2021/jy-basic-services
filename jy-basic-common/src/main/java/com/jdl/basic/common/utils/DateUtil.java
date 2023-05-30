package com.jdl.basic.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : xumigen
 * @date : 2019/5/15
 */
public class DateUtil {

    public static final String FORMAT_DATE_TIME_MILLI = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE_TIME_NO = "yyyyMMddHHmmss";
    public static final String FORMAT_DATE_TIME_MILLISECOND = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_MM_DD = "MM-dd";
    public static final String FORMAT_DATE_MM_dd_HH_mm = "MM-dd HH:mm";
    public static final String FORMAT_DATE_NO = "yyyyMMdd";
    public static final String FORMAT_DATE_MONTH = "yyyyMM";
    public static final String FORMAT_TIME_MILLI = "HH:mm:ss.SSS";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_HOUR_MIN = "HH:mm";
    public static final String FORMAT_HOUR_MINUTE = "HHmm";
    public static final String FORMAT_DAY_IN_WEEK = "u";
    /** 冒号 */
    private static final String COLON = ":";

    // Internal values for using in date/time calculations
    public static final long MILLISECOND_OF_SECOND = 1000;
    public static final long MILLISECOND_OF_MINUTE = MILLISECOND_OF_SECOND * 60;
    public static final long MILLISECOND_OF_HOUR = MILLISECOND_OF_MINUTE * 60;
    public static final long MILLISECOND_OF_DAY = MILLISECOND_OF_HOUR * 24;
    public static final long MILLISECOND_OF_WEEK = MILLISECOND_OF_DAY * 7;

    public static final Date LAST_DATE = DateUtil.parse("9999-12-31 23:59:59", DateUtil.FORMAT_DATE_TIME);

    private static final Pattern intervalTimePattern = Pattern.compile("^[0-9]{1,2}:[0-5][0-9]$");

    private static final Pattern datePattern = Pattern.compile("^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
    private static final Pattern timeSecondPattern = Pattern.compile("^((([0-1]\\d|2[0-4]):[0-5]\\d):[0-5]\\d)$");
    private static final Pattern timeMinutePattern = Pattern.compile("^(([0-1]\\d|2[0-4]):[0-5]\\d)$");

    private static Integer FAST = 1;
    private static Integer SLOW = 2;
    private static Integer EQUAL = 3;

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public DateUtil() {
    }

    /**
     * 添加或减少周
     */
    public static Date addWeek(Date date, int weeks) {
        if (date != null) {
            return new Date(date.getTime() + weeks * MILLISECOND_OF_WEEK);
        }
        return null;
    }

    /**
     * 添加或减少天数
     */
    public static Date addDay(Date date, int days) {
        if (date != null) {
            return new Date(date.getTime() + days * MILLISECOND_OF_DAY);
        }
        return null;
    }

    /**
     * 添加或减少小时
     */
    public static Date addHour(Date date, int hours) {
        if (date != null) {
            return new Date(date.getTime() + hours * MILLISECOND_OF_HOUR);
        }
        return null;
    }

    /**
     * 添加或减少分钟
     */
    public static Date addMinutes(Date date, int minutes) {
        if (date != null) {
            return new Date(date.getTime() + minutes * MILLISECOND_OF_MINUTE);
        }
        return null;
    }

    /**
     * 添加或减少分钟
     */
    public static String addMinutes(String date, int minutes, String format) {
        Date date1 = parse(date, format);
        Date result = addMinutes(date1, minutes);
        return format(result, format);
    }

    public static String format(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parse(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (Exception e) {
            logger.error(date,e);
            return null;
        }
    }

    /**
     * 计算日期减几天
     * @param date 日期
     * @param days 天
     * @return 结果
     */
    public static Date subtractDay(Date date,int days){
        if(date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    public static Date parseDateByStr(String date, String format)throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获得某个月的第一天0时0分0秒的时间
     *
     * @param year 年
     * @param month 月
     * @return java.util.Date
     */
    public static Date getFirstDateOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取某一天的0时0分0秒的时间
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return Date
     */
    public static Date getFirstTimeOfDay(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取某一天的0时0分0秒的时间
     *
     * @param date 时间
     * @return Date
     */
    public static Date getFirstTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }



    /**
     * 获取某一天的23时59分59秒的时间
     *
     * @param date 时间
     * @return Date
     */
    public static Date getLastTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    /**
     * 获得Date型对象，根据时间点
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return Date
     */
    public static Date getDateFromTime(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        return cal.getTime();
    }

    /**
     * 根据日期字符串 获取date
     *
     * @param date 9999-12-31
     * @return 日期date
     */
    public static Date getDateFromStr(String date) {
        String[] strings = date.split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(strings[0]));
        cal.set(Calendar.MONTH, Integer.valueOf(strings[1]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(strings[2]));
        return cal.getTime();
    }

    /**
     * 将util型的日期型的数据转换为sql型的日期数据
     *
     * @param date java.util.Date
     * @return java.sql.Date
     */
    public static java.sql.Date utilToSql(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 将sql型的日期型的数据转换为util型的日期数据
     *
     * @param sqlDate java.sql.Date
     * @return java.util.Date
     */
    public static Date sqlToUtil(java.sql.Date sqlDate) {
        return new Date(sqlDate.getTime());
    }

    /**
     * 将时间字符串进行相加后操作后格式化输出
     *
     * @param timeStr 格式
     * @param hour    时
     * @param minute  分
     * @param second  秒
     * @return 格式
     */
    public static String timeOperate(String timeStr, int hour, int minute, int second) {
        final SimpleDateFormat df = new SimpleDateFormat(FORMAT_TIME);
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        if (times.length == 3) {
            calendar.set(Calendar.SECOND, Integer.valueOf(times[2]));
        }
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return df.format(calendar.getTime());
    }

    /**
     * 根据指定string生成当天时间
     *
     * @param timeStr 格式
     * @return Date
     */
    public static Date getCurrentDayTimeByStr(String timeStr) {
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        if (times.length == 3) {
            calendar.set(Calendar.SECOND, Integer.valueOf(times[2]));
        }else {
            calendar.set(Calendar.SECOND, 0);
        }
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取两个日期间所跨0点次数
     *
     * @param d1 时间1
     * @param d2 时间2
     * @return Integer
     */
    public static Integer getDaysBetweenTwoDate(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        try {
            Date d11 = sdf.parse(sdf.format(d1));
            Date d21 = sdf.parse(sdf.format(d2));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d11);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d21);
            long time2 = cal.getTimeInMillis();
            long betweenDays = Math.abs((time2 - time1) / (1000 * 3600 * 24));

            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 比较时间点确认时效是否跨天
     *
     * @param startTime 开始时间
     * @param endTime   截止时间
     * @return Integer
     */
    public static Integer getAging(String startTime, String endTime) {
        if (null == startTime || null == endTime || startTime.trim().equals("") || endTime.trim().equals("")) {
            return 0;
        }else {
            String[] startTimes = startTime.split(":");
            String[] endTimes = endTime.split(":");

            String startHour = startTimes[0];
            String endHour = endTimes[0];
            if(!startHour.equals(endHour)){
                return Integer.parseInt(startHour) > Integer.parseInt(endHour) ? 1 : 0;
            }

            String startMinute = startTimes[1];
            String endMinute = endTimes[1];
            if(!startMinute.equals(endMinute)){
                return Integer.parseInt(startMinute) > Integer.parseInt(endMinute) ? 1 : 0;
            }

            String startSecond = startTimes[2];
            String endSecond = endTimes[2];
            return Integer.parseInt(startSecond) > Integer.parseInt(endSecond) ? 1 : 0;
        }
    }

    /**
     * 计算两个时间间隔
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return 时间差小时
     */
    public static Double getTimeDiff(String startTime,String endTime,Integer totalAging){
        //跨天
        Date curr = new Date();
        Date startDate = DateUtil.getSpecialDayTimeByStr(curr,startTime);

        Date endDate = DateUtil.getSpecialDayTimeByStr(DateUtil.addDay(curr,totalAging),endTime);
        Double diff = (double) (endDate.getTime()-startDate.getTime())/(1000*60*60);
        DecimalFormat df   = new DecimalFormat("######0.00");
        String timsStr = df.format(diff);
        return Double.parseDouble(timsStr);
    }

    /**
     * hh:mm 格式时间 转换成分钟
     * @param time 12:00 or 12:00:00
     * @return 分钟
     */
    public static int getHhMmTimeToMinute(String time){
        if(StringUtils.isEmpty(time)){
            return 0;
        }
        Matcher hhmm = timeMinutePattern.matcher(time);
        Matcher hhmmss = timeSecondPattern.matcher(time);
        if(!hhmm.matches() && !hhmmss.matches()){
            return 0;
        }
        String[] timeArr = time.split(":");
        return Integer.parseInt(timeArr[0]) * 60 + Integer.parseInt(timeArr[1]);
    }

    /**
     * 根据指定string生成指定日期时间
     *
     * @param timeStr 格式
     * @param date 时间
     * @return Date
     */
    public static Date getSpecialDayTimeByStr(Date date, String timeStr) {
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        if (times.length == 3) {
            calendar.set(Calendar.SECOND, Integer.valueOf(times[2]));
        }else {
            calendar.set(Calendar.SECOND, 0);
        }
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getDayDiff(Date startTime,Date endTime){
        Long diff = (endTime.getTime()-startTime.getTime())/(1000*60*60*24);
        return diff.intValue();
    }


    /**
     * 通过毫秒获取 时分
     * @param time 毫秒时间
     * @return String
     */
    public static String getHourAndMinute(long time){
        if(0==time) return null;
        long second = time/1000;
        int minute = (int) (second/60);
        int hour = minute/60;
        int tmp = minute%60;
        return (hour%24)+""+(tmp==0?"00":tmp);
    }

    /**
     * 获取两个时间间隔
     * @param start 开始
     * @param end 结束
     * @return 返回 小时，保留两位
     */
    public static Integer getIntervalHour(Date start,Date end){
        if(null==start||null==end){
            return null;
        }
        Double interval = Double.valueOf(end.getTime() - start.getTime());
        Double hour = interval/1000d/60d/60d;
        return hour.intValue();
    }

    /**
     * 获取两个时间间隔
     * @param start 开始
     * @param end 结束
     * @return 返回 小时，保留两位
     */
    public static String getIntervalTime(Date start,Date end){
        if(null==start||null==end){
            return null;
        }
        long interval = end.getTime() - start.getTime();
        DecimalFormat df   = new DecimalFormat("######0.00");
        double hour = interval/1000d/60d/60d;
        String hourStr = df.format(hour);
        return hourStr;
    }

    /**
     * 获取两个时间间隔
     * @param start 开始
     * @param end 结束
     * @return 返回 分钟，保留两位
     */
    public static Long getIntervalTimeMinute(Date start,Date end){
        if(null==start||null==end){
            return null;
        }
        long interval = end.getTime() - start.getTime();
        DecimalFormat df   = new DecimalFormat("######0.00");
        long minute = interval/1000/60;
        return minute;
    }

    /**
     * 将传入日期格式化为 “yyyy-MM-dd HH:mm:ss” 的字符串
     * @param date 日期
     * @return String
     */
    public static String formatDateTime(Date date){
        return format(date,FORMAT_DATE_TIME);
    }

    /**
     * 将传入日期格式化为 “HH:mm:ss” 的字符串
     * @param date 日期
     * @return String
     */
    public static String formatTime(Date date){
        return format(date,FORMAT_TIME);
    }

    /**
     * 将传入日期格式化为 “yyyy-MM-dd” 的字符串
     * @param date 日期
     * @return String
     */
    public static String formatDate(Date date){
        return format(date,FORMAT_DATE);
    }

    public static boolean isSameDay(Date one,Date another){
        return getDaysBetweenTwoDate(one,another)==0;
    }

    public static Integer timeStrToMinute(String hmm){
        if(StringUtils.isEmpty(hmm)){
            return null;
        }
        Matcher matcher = intervalTimePattern.matcher(hmm);
        if(!matcher.matches()){
            return null;
        }
        String[] timesArray = hmm.split(":",-1);
        String hoursStr = timesArray[0];
        Integer minutes;
        if(hoursStr.length()>1){
            minutes = (Integer.valueOf(hoursStr.charAt(0)+"")*10+Integer.valueOf(hoursStr.charAt(1)+""))*60;
        }else {
            minutes = Integer.valueOf(hoursStr.charAt(0)+"")*60;
        }
        String minutesStr = timesArray[1];
        minutes=minutes+Integer.valueOf(minutesStr.charAt(0)+"")*10+Integer.valueOf(minutesStr.charAt(1)+"");
        return minutes;
    }

    public static Date renderTimeInDate(String timeStr,Date date) {
        String dateStr = format(date,FORMAT_DATE);
        String completeTime = dateStr+" "+timeStr;
        return parse(completeTime,FORMAT_DATE_TIME);
    }

    public static String getDayInWeek(Date date) {
        if(date==null){
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY_IN_WEEK);
            return df.format(date);

        }catch (Exception e){
            return null;
        }
    }

    /**
     *  格式化时分秒成时分
     * @param timeSecond 24:59:59 or 24:59
     * @return 24:59
     */
    public static String timeSecondToTimeMinute(String timeSecond){
        if(StringUtils.isEmpty(timeSecond)){
            return "";
        }
        Matcher matcher = timeSecondPattern.matcher(timeSecond);
        if(matcher.matches()){
            return matcher.group(2);
        }
        matcher = timeMinutePattern.matcher(timeSecond);
        if(matcher.matches()){
            return timeSecond;
        }
        return timeSecond;
    }

    /**
     * 字符串是否HH:mm格式
     * @param times 时间字符串
     * @return true false
     */
    public static boolean isTimehhmm(String times){
        if(StringUtils.isEmpty(times)){
            return false;
        }
        return timeMinutePattern.matcher(times).matches();
    }

    /**
     * 匹配是否yyyy-MM—dd 并不严格校验，只是在字符表面校验格式
     * @param date 字符串
     * @return true 符合 false 不符合
     */
    public static boolean isDateYMd(String date){
        if(StringUtils.isEmpty(date)){
            return false;
        }
        return datePattern.matcher(date).matches();
    }

    /**
     *  获取日期 如果有跨天的日期加一天
     *
     * @param hhMmss 时间格式
     * @param  day  跨天为1 否则为0
     * @Author: zhangwentao
     * @Date: 21:53 2017/10/26
     */
    public static Date getDateContainAddDay(String hhMmss, Integer day) {
        Date now = new Date();
        Date date = DateUtil.getSpecialDayTimeByStr(now, hhMmss);
        if (day.intValue() == 1) {
            date = DateUtil.addDay(date, 1);
        }
        return date;
    }

    public static SimpleDateFormat getWeekDateFormat(){
        return new SimpleDateFormat(FORMAT_DAY_IN_WEEK);
    }

    /**
     *
     * @param one 时间
     * @param other 时间
     * @param isCeiling 是否向上取整(相差毫秒数)
     * @return 两个时间相差的小时数
     */

    public static Integer getDistantHours(Date one, Date other,Boolean isCeiling){
        if(one==null || other==null){
            return null;
        }

        Long difference = Math.abs(one.getTime()-other.getTime());

        Long hours = difference/(1000*1*60*60);

        if(isCeiling && difference%(1000*1*60*60)>0){
            hours +=1;
        }
        return hours.intValue();
    }

    /**
     *
     * @param timeStr 时间字符串，格式： "HH:mm:ss"
     * @param other 时间，格式： "HH:mm:ss"
     * @param isCeiling 是否向上取整
     * @return 两个时间相差的小时数
     */
    public static Integer getDistantHours(String timeStr, String other,boolean isCeiling){
        if(timeStr==null || "".equals(timeStr)){
            return null;
        }

        if(other==null || "".equals(other)){
            return null;
        }


        if(!timeSecondPattern.matcher(timeStr).matches() || !timeSecondPattern.matcher(other).matches()){
            return null;
        }

        String bigger = timeStr;
        String smaller = other;
        if(bigger.compareTo(smaller)<0){
            bigger = other;
            smaller = timeStr;
        }

        String[] biggerArr = bigger.split(":",2);
        String[] smallerArr = smaller.split(":",2);
        Integer hours = Integer.parseInt(biggerArr[0])-Integer.parseInt(smallerArr[0]);
        if(biggerArr[1].equals(smallerArr[1])){
            return hours;

        }else if(biggerArr[1].compareTo(smallerArr[1])>0){

            if(isCeiling){
                hours+=1;
            }

        }else if(!isCeiling){

            hours-=1;
        }

        return hours;
    }

    public static void main(String[] args){
        System.out.println(DateUtil.getIntervalHour(parse("2017-12-26 15:00:00",FORMAT_DATE_TIME),parse("2017-12-25 15:00:00",FORMAT_DATE_TIME)));
    }

}
