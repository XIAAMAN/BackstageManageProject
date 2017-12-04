package cn.nchu.lims.util.lang;

import java.util.regex.Pattern;

public class FloatUtil {

	/**
	 * 判断是不是浮点数格式的字符串
	 * @param str : String
	 * @return blooean
	 */
	public static boolean isFloat(String str) {
		return Pattern.matches("^\\d+(\\.\\d+)?$", str);
	}
	
	/**
	 * 判断浮点数是不是null 或者是 0
	 * @param f : Float
	 * @return blooean
	 */
	public static boolean isNullOrZero(Float f) {
		return (f == null || f == 0);
	}
}
