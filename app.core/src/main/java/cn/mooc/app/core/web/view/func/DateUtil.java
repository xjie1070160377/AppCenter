package cn.mooc.app.core.web.view.func;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import cn.mooc.app.core.utils.Beetls;

public class DateUtil implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		Map<String, Object> params = (Map<String, Object>) paras[0];
		long oneMinute = 60;
		long oneHour = oneMinute * 60;
		long oneDay = oneHour * 24;
		long oneMon = oneDay * 30;
		long oneYear = oneMon * 365;

		Date date = Beetls.getDate(params, "date");

		long publishTime = date.getTime();
		long now = System.currentTimeMillis();
		long t = (now - publishTime) / 1000;
		String Result = "";
		if (t <= oneMinute) {
			Result = "刚刚";
		} else if (t > oneMinute && t <= oneMinute * 10) {
			Result = t / oneMinute + "分钟前";
		} // 0-10分钟
		else if (t >= oneMinute * 10 && t < oneHour) {
			Result = t / (oneMinute * 10) + "0分钟前";
		} // 10-50分钟
		else if (t >= oneHour && t < oneHour * 10) {
			Result = t / oneHour + "小时前";
		} // 1-9小时
		else if (t >= oneHour * 10 && t < oneDay) {
			Result = "1天前";
		} // 10小时-23小时
		else if (t >= oneDay && t < oneMon) {
			Result = t / oneDay + "天前";
		} // 1天-29天
		else if (t >= oneMon && t < oneYear) {
			Result = t / oneMon + "个月前";
		} // 1个月-11个月
		else if (t >= oneYear) {
			Result = t / oneYear + "年前";
		} // 1-n年

		return Result;
	}

	public static Date getMondayOfThisWeek(int year, int month) {
		Date date = new Date(year - 1900, month - 1, 1);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return c.getTime();
	}

	public static List<Map<String, Object>> getWeekDrop(int year, int month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> weekDrop = new ArrayList();
		Date thisMonth = new Date(year - 1900, month - 1, 1);
		Date thisMonthEnd = DateUtils.addMonths(thisMonth, 1);
		Date start = DateUtil.getMondayOfThisWeek(year, month);
		for (int i = 1; i <= 6; i++) {
			if ((start.after(thisMonth) || start.equals(thisMonth)) && start.before(thisMonthEnd)) {
				Map<String, Object> map = new HashMap<>();
				map.put("Text", String.format("%1$s -- %2$s", sdf.format(start), sdf.format(DateUtils.addDays(start, 6))));
				map.put("Value", i);
				weekDrop.add(map);
			}
			start = DateUtils.addDays(start, 7);
		}
		return weekDrop;
	}

	public static Date getStartDayOfWeeks(int year, int month, int weekIndex) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> weekDrop = new ArrayList();
		Date thisMonth = new Date(year - 1900, month - 1, 1);
		Date thisMonthEnd = DateUtils.addMonths(thisMonth, 1);
		Date start = DateUtil.getMondayOfThisWeek(year, month);
		for (int i = 1; i <= 6; i++) {
			if (weekIndex == i) {
				return start;
			}
			start = DateUtils.addDays(start, 7);
		}
		return null;
	}

}
