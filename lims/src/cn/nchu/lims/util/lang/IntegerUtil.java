package cn.nchu.lims.util.lang;

import java.util.regex.Pattern;

public class IntegerUtil {

	/**
	 * �ж��ַ����Ƿ�Ϊ������һ������У��id�Ƿ�Ϊ����������
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isDigit(String str) {
		return (!StringUtil.isNullOrEmpty(str) && 
				Pattern.matches("^[1-9]\\d*$", str.trim()));
	}
	
	/**
	 * �ж�int�Ƿ�Ϊnull ����Ϊ0
	 * @param integer : Integer
	 * @return blooean
	 */
	public static boolean isNull(Integer integer) {
		return (integer == null);
	}
	
	/**
	 * �ж�int�Ƿ�Ϊnull ����Ϊ0��һ������id���ж�
	 * @param integer : Integer
	 * @return blooean
	 */
	public static boolean isNullOrZero(Integer integer) {
		return (integer == null || integer == 0);
	}
	
	/**
	 * �ж��Ƿ�����ֻ��Ÿ�ʽ
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isMobilePhone(String str) {
		return (!StringUtil.isNullOrEmpty(str) 
				&& Pattern.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", str.trim()));
	}
	
	/**
	 * �ж��Ƿ�����ֻ��Ÿ�ʽ�������Ƿ�Ϊ��
	 *   �����޸���Ϣʱ��У��
	 * @param str : String
	 * @return boolean
	 */
	public static boolean isMobilePhoneOrNull(String str) {
		return (StringUtil.isNullOrEmpty(str) 
				|| Pattern.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", str.trim()));
	}
}
