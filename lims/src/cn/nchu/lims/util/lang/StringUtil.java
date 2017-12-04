package cn.nchu.lims.util.lang;

import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * �ж��ַ����Ƿ�Ϊnull ��Ϊ""
	 * @param title
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.trim().equals(""));
	}
	
	/**
	 * �ж��Ƿ���������ʽ
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
		return (!StringUtil.isNullOrEmpty(str) 
				&& Pattern.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$", str.trim()));
	}
	
	/**
	 * �ж��Ƿ���������ʽ�������Ƿ�Ϊ��
	 *   �û��޸���Ϣʱ��У��
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isEmailOrNull(String str) {
		return (StringUtil.isNullOrEmpty(str) 
				|| Pattern.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$", str.trim()));
	}
	
	/**
	 * �ж��ַ����Ƿ���ϳ���Ҫ��
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
	 * �ж��ַ����Ƿ���ϳ���Ҫ�󣬻����Ƿ�Ϊ��
	 *   �û��޸���Ϣʱ��У��
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
	 * �ж��ַ����Ƿ����������
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean inArray(String str, String[] array) {
		boolean empty = false, in = false;
		empty = StringUtil.isNullOrEmpty(str.trim());  // �жϲ����ַ��Ƿ�Ϊ�ջ�Ϊ""
		for(String string : array) {  // �ж�str�Ƿ���array����
			if(str.trim().equals(string)) {  
				in = true;
				break;
			}
		}
		return (!empty && in);
	}
	
	/**
	 * �ж��ַ����Ƿ���������У������Ƿ�Ϊ��
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean inArrayOrNull(String str, String[] array) {
		boolean empty = false, in = false;
		empty = StringUtil.isNullOrEmpty(str.trim());  // �жϲ����ַ��Ƿ�Ϊ�ջ�Ϊ""
		for(String string : array) {  // �ж�str�Ƿ���array����
			if(str.trim().equals(string)) {  
				in = true;
				break;
			}
		}
		return (empty || in);
	}
	
	/**
	 * ��һ���ַ���ͨ���ض����ַ��ָ������
	 * @param param String Ҫ�ָ���ַ���
	 * @param split String ���ڷָ���ַ�
	 * @return
	 */
	public static String[] split(String param, String split) {
		String[] params = null;
		params = param.split(split);
		return params;
	}
	
	/**
	 * �ַ�������ĸת���ɴ�д
	 * @param param String
	 * @return String
	 */
	public static String FirstToUp(String param) {
		return param.replaceFirst(param.substring(0, 1),param.substring(0, 1).toUpperCase()) ;
	}
}
