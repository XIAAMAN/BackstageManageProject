package cn.nchu.lims.util.lang;

import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判读字符串是否为null 或为""
	 * @param title
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.trim().equals(""));
	}
	
	/**
	 * 判断是否符合邮箱格式
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
		return (!StringUtil.isNullOrEmpty(str) 
				&& Pattern.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$", str.trim()));
	}
	
	/**
	 * 判断是否符合邮箱格式，或者是否为空
	 *   用户修改信息时的校验
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isEmailOrNull(String str) {
		return (StringUtil.isNullOrEmpty(str) 
				|| Pattern.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$", str.trim()));
	}
	
	/**
	 * 判断字符串是否符合长度要求
	 * @param min : int
	 * @param str : Stirng
	 * @param max : int
	 * @return boolean
	 */
	public static boolean inLength(int min, String str, int max) {
		return (!StringUtil.isNullOrEmpty(str.trim()) 
				&& str.trim().length() >= min 
				&& str.trim().length() <= max);
	}
	
	/**
	 * 判断字符串是否符合长度要求，或者是否为空
	 *   用户修改信息时的校验
	 * @param min : int
	 * @param str : Stirng
	 * @param max : int
	 * @return boolean
	 */
	public static boolean inLengthOrNull(int min, String str, int max) {
		return (StringUtil.isNullOrEmpty(str.trim()) 
				|| str.trim().length() >= min 
				&& str.trim().length() <= max);
	}
	
	/**
	 * 判断字符串是否存在数组中
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean inArray(String str, String[] array) {
		boolean empty = false, in = false;
		empty = StringUtil.isNullOrEmpty(str.trim());  // 判断测试字符是否为空或为""
		for(String string : array) {  // 判断str是否在array里面
			if(str.trim().equals(string)) {  
				in = true;
				break;
			}
		}
		return (!empty && in);
	}
	
	/**
	 * 判断字符串是否存在数组中，或者是否为空
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean inArrayOrNull(String str, String[] array) {
		boolean empty = false, in = false;
		empty = StringUtil.isNullOrEmpty(str.trim());  // 判断测试字符是否为空或为""
		for(String string : array) {  // 判断str是否在array里面
			if(str.trim().equals(string)) {  
				in = true;
				break;
			}
		}
		return (empty || in);
	}
	
	/**
	 * 将一个字符串通过特定的字符分割成数组
	 * @param param String 要分割的字符串
	 * @param split String 用于分割的字符
	 * @return
	 */
	public static String[] split(String param, String split) {
		String[] params = null;
		params = param.split(split);
		return params;
	}
	
	/**
	 * 字符串首字母转换成大写
	 * @param param String
	 * @return String
	 */
	public static String FirstToUp(String param) {
		return param.replaceFirst(param.substring(0, 1),param.substring(0, 1).toUpperCase()) ;
	}
}
