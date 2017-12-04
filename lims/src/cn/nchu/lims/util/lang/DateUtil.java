package cn.nchu.lims.util.lang;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtil {

	/**
	 * 判断日期是否符合yyyy-MM-dd规范
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		return Pattern.matches(rexp, str);
	};
	
	/**
	 * 判断当前时间是否为yyyy-MM
	 * @param str
	 * @return
	 */
	public static boolean isYearAndMonth(String str) {
		String rexp = "^[0-9]{4}\\-(0[1-9]|1[0-2])$";
		return Pattern.matches(rexp, str);
	}
	
	public static boolean isYearAndMonthOrEmpty(String str) {
		String rexp = "^[0-9]{4}\\-(0[1-9]|1[0-2])$";
		return Pattern.matches(rexp, str) || StringUtil.isNullOrEmpty(str);
	}
	
	public static boolean isYearAndMonthAgo(String str) {
		String[] dates = str.split("\\-");
		Calendar now = Calendar.getInstance();
		
		if(Integer.valueOf(dates[0]) >= 1976 &&Integer.valueOf(dates[0]) <= now.get(Calendar.YEAR) 
				&& Integer.valueOf(dates[1]) >=1 && Integer.valueOf(dates[1]) <=12) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当前时间
	 * @return Date
	 */
	public static Date currentTime() {
		return new java.sql.Date(new java.util.Date().getTime());
	}
	
	/**
	 * 得到当前时间的时间戳的String形式
	 * @return String
	 */
	public static String timeStamp() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}
	
	/**
	 * 判断String是否为yyyy-MM-dd
	 *   是返回加上 00:00:00，否返回null
	 * @param date : String
	 * @return String
	 */
	public static String dateAddStartHMS(String date) {
		if(!StringUtil.isNullOrEmpty(date) && isDate(date)) {
			return date += " 00:00:00";
		} else {
			return null;
		}
	}
	
	/**
	 * 判断String是否为yyyy-MM-dd
	 *   是返回加上 23:59:59，否返回null
	 * @param date : String
	 * @return String
	 */
	public static String dateAddEndHMS(String date) {
		if(!StringUtil.isNullOrEmpty(date) && isDate(date)) {
			return date += " 23:59:59";
		} else {
			return null;
		}
	}
	
}
