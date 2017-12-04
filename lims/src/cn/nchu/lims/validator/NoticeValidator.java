package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Notice;
import cn.nchu.lims.util.lang.StringUtil;

public class NoticeValidator {

	/**
	 * ����Notice�������ݿ�������ݵ�У��
	 * @param notice : Notice
	 * @return message : String
	 */
	public static String validator(Notice notice) {
		String message = "";
		if(StringUtil.isNullOrEmpty(notice.getTitle())) {
			message += "�������:���ⲻ��Ϊ��;";
		}
		if(notice.getSeq() != 0 && notice.getSeq() != 1) {
			message += "�ö�״̬����:ȡֵΪ0��1;";
		}
		return message;
	}
}
