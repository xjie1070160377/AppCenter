package cn.mooc.app.core.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间操作工具类
 * 
 * @author Taven
 *
 */
public class DateTimeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
	
	
	public static void main(String[] args) {
		logger.debug(getCurrentTimeNumStr());
		System.out.println(DateTimeUtil.after(new Date(), DateTimeUtil.toDateTime("2017-01-02 23:59:59")));
		System.out.println(DateTimeUtil.after(new Date(), DateTimeUtil.toDateTime("2017-01-02 23:59:59","yyyy-MM-dd HH:mm:ss")));
		System.out.println(calculateSeconds(new Date(), DateTimeUtil.toDateTime("2017-01-03 14:46:00")));
	}
	
	
	public static Date getCurrentTime() {

		return new Date();

	}
	
	public static Date getCreateTime() {

		return new Date();

	}
	
	public static Date getCurrentTime(int timeZone) {
		//暂未实现
		return new Date();

	}
	
	public static void setCurrentTimeZone(TimeZone timeZone) {

		TimeZone.setDefault(timeZone);

	}
	
	public static TimeZone getTimeZoneByID(String timeZoneID) {
		/**
		 * TimeZone.getTimeZone("Asia/Shanghai")
		 * 
		 * TimeZone.getTimeZone("GMT")
		 * 
		 * System.setProperty("user.timezone","Asia/Shanghai")
		 * 
		 * System.setProperty("user.timezone", "GMT+8");
		 * 
		 * TimeZone.getTimeZone(System.getProperty("user.timezone"));
		 * 
		 */
		return TimeZone.getTimeZone(timeZoneID);

	}
	
	
	
	public static TimeZone getCurrentTimeZone() {
		Calendar calendar = Calendar.getInstance();
		TimeZone timeZone = calendar.getTimeZone();
		//timeZone.getID();
		//timeZone.getDisplayName();
		return timeZone;

	}
	
	public static Date toTimeByZone(Date dateTime, int timeZone, String timeFormat) {

		//timeZone 中国是8
		SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
		ParsePosition pos = new ParsePosition(timeZone);
		Date resultDate = formatter.parse(formatter.format(dateTime), pos);

		return resultDate;

	}
	
	public static String getCurrentTimeNumStr() {

		return dateFormat(new Date(), "yyyyMMddHHmmss");

	}
	
	public static String getCurrentTimeStr() {

		return dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");

	}
	
	public static String dateFormat(Date currentTime, String timeFormat) {
		if(currentTime==null){
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
		String dateString = formatter.format(currentTime);

		return dateString;

	}
	
	/**
	 * 将当前时间转化为指定的格式字符串
	 * @param timeFormat
	 * @return
	 */
	public static String dateFormat(String timeFormat) {

		return dateFormat(getCurrentTime(), timeFormat);

	}
	
	public static Date toDateTime(String dateStr) {
		//以操作系统当前的时间格式来转
		try {
			return SimpleDateFormat.getDateTimeInstance().parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	
	public static Date toDateTime(String timeStr, String timeFormat) {
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
			Date resultDate = formatter.parse(timeStr);
			return resultDate;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	public static Date addYear(Date sDate, int years) {

		Calendar c = new GregorianCalendar();
		c.setTime(sDate);
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}
	
	public static Date addMonth(Date sDate, int months) {

		Calendar c = new GregorianCalendar();
		c.setTime(sDate);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}

	
	public static Date addDay(Date sDate, int days) {

		Calendar c = new GregorianCalendar();
		c.setTime(sDate);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	public static Date addMinute(Date sDate, int minute) {

		Calendar c = new GregorianCalendar();
		c.setTime(sDate);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}
	
	public static Date addSecond(Date sDate, int second) {

		Calendar c = new GregorianCalendar();
		c.setTime(sDate);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}
	
	public static Date addSecOnNowTime(Long second) {

		return addSecond(new Date(),second.intValue());
		
	}
	
	public static boolean after(Date aDate, Date bDate) {

		Calendar a = new GregorianCalendar();
		a.setTime(aDate);
		
		Calendar b = new GregorianCalendar();
		b.setTime(bDate);
		
		return a.after(b);
	}
	
	public static Date getDayStartTime(){
		//取今天的开始时间，到毫秒
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}
	
	public static Date getDayEndTime(){
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}
	
	public static Date getMonthStartTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getMonthEndTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	public static Date getLastDayOfMonth(Date date){
		//指定日期的本月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//calendar.setFirstDayOfWeek(Calendar.MONDAY); //默认是从星期一算起
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}
	
	
	public static Date getLastDayOfWeek(Date date){
		//指定日期的本周最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		return calendar.getTime();
	}
	
	public static int calculateDayInterval(Date first, Date second) {
		//计算两个时间时间相差多少天，相差不满24小时，则为0
		//	first.getTime() - second.getTime() 返回负数
		//	Math.abs(first.getTime() - second.getTime()) 只返回正数，也就是不分前后
		long milliseconds = Math.abs(first.getTime() - second.getTime());
		return new Long(milliseconds / (1000 * 60 * 60 * 24)).intValue();
	}
	
	public static long calculateMilliseconds(Date first, Date second) {
		//计算两个时间时间相差多少毫秒
		long milliseconds = Math.abs(first.getTime() - second.getTime());
		return milliseconds;
	}
	
	public static long calculateSeconds(Date first, Date second) {
		//计算两个时间时间相差多少秒
		return calculateMilliseconds(first, second)/1000;
	}


	/**
	 * 
	 * 
	 * 
	 * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前，如果大于一天则用format规定的格式显示
	 * 
	 * @author 吴智
	 * @param ctime
	 *            时间
	 * @param format
	 *            格式 格式描述:例如:yyyy-MM-dd yyyy-MM-dd HH:mm:ss ,<br/>
	 *            可为空，默认yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String showTime(Date ctime) {
		// System.out.println("当前时间是："+new
		// Timestamp(System.currentTimeMillis()));

		// System.out.println("发布时间是："+df.format(ctime).toString());
		String format = null;

		String r = "";
		if (ctime == null)
			return r;
		if (format == null)
			format = "yy-MM-dd";

		long nowtimelong = System.currentTimeMillis();

		long ctimelong = ctime.getTime();
		long result = Math.abs(nowtimelong - ctimelong);

		if (result < 60000) {// 一分钟内
			r = "刚刚";
			/*
			 * long seconds = result / 1000; if(seconds == 0){ r = "刚刚"; }else{
			 * r = seconds + "秒前"; }
			 */
		} else if (result >= 60000 && result < 3600000) {// 一小时内
			long seconds = result / 60000;
			r = seconds + "分钟前";
		} else if (result >= 3600000 && result < 86400000) {// 一天内
			long seconds = result / 3600000;
			r = seconds + "小时前";
		} else if (result >= 86400000 && result < Long.parseLong("2592000000")) {// 三十天内
			long seconds = result / 86400000;
			r = seconds + "天前";
		} else {// 日期格式
			SimpleDateFormat df = new SimpleDateFormat(format);
			r = df.format(ctime).toString();
		}
		return r;
	}
	
	public static Date parse(String s) {
		return parse(s, false);
	}
	
	public static Date parse(String input, String pattern) throws ParseException{
		if(StringUtil.isNotEmpty(input)){
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(input);
		}else{
			return null;
		}
	}

	public static Date parseEndDate(String s) {
		return parse(s, true);
	}

	public static Date parse(String s, boolean endDate) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		s = s.trim();
		DateTime dt;
		if (s.startsWith("now")) {
			dt = DateTime.now();
			dt = dt.withMillisOfSecond(0);
			dt = dt.withSecondOfMinute(0);
			if (!StringUtils.containsIgnoreCase(s, "minute")) {
				dt = dt.withMinuteOfHour(0);
			}
			String[] arr = StringUtils.split(s, ',');
			int num;
			boolean plus;
			String[] opts;
			for (int i = 1, len = arr.length; i < len; i++) {
				plus = true;
				opts = StringUtils.split(arr[i], '+');
				if (opts.length != 2) {
					plus = false;
					opts = StringUtils.split(arr[i], '-');
				}
				if (opts.length != 2) {
					continue;
				}
				num = NumberUtils.toInt(opts[1], 0);
				if (!plus) {
					num = -num;
				}
				if (opts[0].equalsIgnoreCase("year")) {
					dt = dt.plusYears(num);
				} else if (opts[0].equalsIgnoreCase("month")) {
					dt = dt.plusMonths(num);
				} else if (opts[0].equalsIgnoreCase("day")) {
					dt = dt.plusDays(num);
				} else if (opts[0].equalsIgnoreCase("hour")) {
					dt = dt.plusHours(num);
				} else if (opts[0].equalsIgnoreCase("minute")) {
					dt = dt.plusMinutes(num);
				}
				// else if (opts[0].equalsIgnoreCase("second")) {
				// dt = dt.plusSeconds(num);
				// }
			}
		} else {
			dt = DateTime.parse(s);
			if (endDate && s.length() <= 10) {
				dt = dt.plusDays(1);
				dt = dt.minusMillis(1);
			}
		}
		return dt.toDate();
	}
}
