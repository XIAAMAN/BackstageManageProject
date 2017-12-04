package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Paper;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class PaperValidator {

	public static String validator(Paper paper) {
		String message = "";
		
		if (StringUtil.isNullOrEmpty(paper.getName())) {
			message += "论文名称错误:名称不可为空;";
		}
		
		if (!StringUtil.inLength(2, paper.getAuthor(), 4)) {
			message += "论文第一作者错误:不符合命名规范;";
		}
		
		if (paper.getPublishTime() == null) {
			message += "发表时间错误:不可为空,格式为 yyyy-MM-dd;";
		}
		
		if (!StringUtil.inLength(0, paper.getAuthorComm(), 4)) {
			message += "论文通讯作者错误:不符合命名规范;";
		}
		
		if (!StringUtil.inArray(paper.getPaperType(), Constant.PAPER_TYPE)) {
			message += "论文类型错误:可选值(JA、CA、JG);";
		}
		
		String[] params = StringUtil.split(paper.getIndexType(), ","); 
		for (String param : params) {
			if (!StringUtil.inArray(param, Constant.PAPER_INDEX_TYPE)) {
				message += "索引类型错误:可选值(SCI、EI、CSCI、ISTP、CPCI;";
				break;
			}
		}
		
		if (!StringUtil.inLengthOrNull(0, paper.getAbstracts(), 2000)) {
			System.out.println(paper.getAbstracts().length());
			message += "摘要错误:内容长度为2000以内;";
		}
		
		return message;
	}

	public static String updateValidator(Paper paper) {
String message = "";

		if (!StringUtil.inLengthOrNull(2, paper.getAuthor(), 4)) {
			message += "论文第一作者错误:不符合命名规范;";
		}

		if (!StringUtil.inLengthOrNull(2, paper.getAuthorComm(), 4)) {
			message += "论文通讯作者错误:不符合命名规范;";
		}
		
		if (!StringUtil.isNullOrEmpty(paper.getIndexType())) {		
			String[] params = StringUtil.split(paper.getIndexType(), ","); 
			for (String param : params) {
				if (!StringUtil.inArray(param, Constant.PAPER_INDEX_TYPE)) {
					message += "索引类型错误:可选值(SCI、EI、CSCI、ISTP、CPCI;";
					break;
				}
			}
		}
		if(!StringUtil.inArrayOrNull(
				paper.getPaperType(), Constant.PAPER_TYPE)) {
			message += "论文类型错误:可选值(JA、CA、JG);";
		}
		
		return message;
	}
}
