package cn.nchu.lims.validator;

import cn.nchu.lims.domain.News;
import cn.nchu.lims.util.lang.StringUtil;

public class NewsValidator {

	/**
	 * ����Notice�������ݿ�������ݵ�У��
	 * @param notice : Notice
	 * @return message : String
	 */
	public static String validator(News news) {
		String message = "";
		if(StringUtil.isNullOrEmpty(news.getTitle())) {
			message += "�������:���ⲻ��Ϊ��;";
		}
		if(StringUtil.isNullOrEmpty(news.getKeyword())) {
			message += "�ؼ��ִ���:�ؼ��ֲ���Ϊ��;";
		}
		if(news.getSeq() != 0 && news.getSeq() != 1) {
			message += "�ö�״̬����:ȡֵΪ0��1;";
		}
		return message;
	}
}
