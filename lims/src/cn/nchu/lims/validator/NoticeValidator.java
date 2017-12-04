package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Notice;
import cn.nchu.lims.util.lang.StringUtil;

public class NoticeValidator {

	/**
	 * 用于Notice的向数据库插入数据的校验
	 * @param notice : Notice
	 * @return message : String
	 */
	public static String validator(Notice notice) {
		String message = "";
		if(StringUtil.isNullOrEmpty(notice.getTitle())) {
			message += "标题错误:标题不可为空;";
		}
		if(notice.getSeq() != 0 && notice.getSeq() != 1) {
			message += "置顶状态错误:取值为0或1;";
		}
		return message;
	}
}
