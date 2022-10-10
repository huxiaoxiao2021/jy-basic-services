package com.jdl.basic.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Auther: liuhaiming
 * @Date: 2019/11/15 22:38
 */
public class DateHelper {

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public final static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 一分钟的毫秒数
     */
    public static final long ONE_MINUTES_MILLI = 60 * 1000;

    /**
     * 五分钟毫秒数
     */
    public static final long FIVE_MINUTES_MILLI = 5 * ONE_MINUTES_MILLI;


    public static String chineseDateFormat() {
        return chineseDateFormat(Calendar.getInstance().getTime());
    }
    public static String chineseDateFormat(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

        return format.format(date) + " " + getWeekOfDate(date);
    }

    /**
     * 获取年月日 yyMMdd
     * @return
     */
    public static String getDateOfyyMMdd(){
        DateFormat format = new SimpleDateFormat("yyMMdd");

        return format.format(new Date());
    }

    /**
     * 获取年月日 时分秒 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateOfyyMMddHHmmss(Date date){
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);

        return format.format(new Date());
    }

    /**
     * 获取年月日 yyMMdd
     * @return
     */
    public static String getDateOfyyMMdd(Date date){
        DateFormat format = new SimpleDateFormat("yyMMdd");

        return format.format(date);
    }

    /**
     * 获取年月日 yyMMdd
     * @return
     */
    public static String getDateOfyyMMdd2(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(new Date());
    }

    /**
     * 获取年月日 yyMMdd
     * @return
     */
    public static String getDateOfyyMMdd2(Date date){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(date);
    }


    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int daysInWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (daysInWeek < 0) {
            daysInWeek = 0;
        }

        return weekDays[daysInWeek];
    }

    public static Date parse(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String dateStr,String formatStr){
        try {
        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static Date transTimeMinOfDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static Date transTimeMaxOfDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }

    public static Date addDays(Date date , int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
    /**
     * 计算小时数
     * @param startTime
     * @param endTime
     * @return
     */
	public static double betweenHours(Date startTime, Date endTime) {
		if(startTime != null && endTime!= null && endTime.after(startTime)) {
			return 1.0*(endTime.getTime() - startTime.getTime())/3600/1000;
		}
		return 0;
	}


}
