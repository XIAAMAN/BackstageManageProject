package cn.nchu.lims.util.lang;

import java.util.regex.Pattern;

public class FloatUtil {

	/**
	 * �ж��ǲ��Ǹ�������ʽ���ַ���
	 * @param str : String
	 * @return blooean
	 */
	public static boolean isFloat(String str) {
		return Pattern.matches("^\\d+(\\.\\d+)?$", str);
	}
	
	/**
	 * �жϸ������ǲ���null ������ 0
	 * @param f : Float
	 * @return blooean
	 */
	public static boolean isNullOrZero(Float f) {
		return (f == null || f == 0);
	}
}
