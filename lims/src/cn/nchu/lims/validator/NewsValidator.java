package cn.nchu.lims.validator;

import cn.nchu.lims.domain.News;
import cn.nchu.lims.util.lang.StringUtil;

public class NewsValidator {

	/**
	 * 用于Notice的向数据库插入数据的校验
	 * @param notice : Notice
	 * @return message : String
	 */
	public static String validator(News news) {
		String message = "";
		if(StringUtil.isNullOrEmpty(news.getTitle())) {
			message += "标题错误:标题不可为空;";
		}
		if(StringUtil.isNullOrEmpty(news.getKeyword())) {
			message += "关键字错误:关键字不可为空;";
		}
		if(news.getSeq() != 0 && news.getSeq() != 1) {
			message += "置顶状态错误:取值为0或1;";
		}
		return message;
	}
}
