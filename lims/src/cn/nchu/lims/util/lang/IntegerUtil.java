package cn.nchu.lims.util.lang;

import java.util.regex.Pattern;

public class IntegerUtil {

	/**
	 * 判断字符串是否为整数，一般用于校验id是否为正常的整数
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isDigit(String str) {
		return (!StringUtil.isNullOrEmpty(str) && 
				Pattern.matches("^[1-9]\\d*$", str.trim()));
	}
	
	/**
	 * 判断int是否为null 或者为0
	 * @param integer : Integer
	 * @return blooean
	 */
	public static boolean isNull(Integer integer) {
		return (integer == null);
	}
	
	/**
	 * 判断int是否为null 或者为0，一般用于id的判断
	 * @param integer : Integer
	 * @return blooean
	 */
	public static boolean isNullOrZero(Integer integer) {
		return (integer == null || integer == 0);
	}
	
	/**
	 * 判断是否符合手机号格式
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isMobilePhone(String str) {
		return (!StringUtil.isNullOrEmpty(str) 
				&& Pattern.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", str.trim()));
	}
	
	/**
	 * 判断是否符合手机号格式，或者是否为空
	 *   用于修改信息时的校验
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isMobilePhoneOrNull(String str) {
		return (StringUtil.isNullOrEmpty(str) 
				|| Pattern.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", str.trim()));
	}
}
