package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Paper;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class PaperValidator {

	public static String validator(Paper paper) {
		String message = "";
		
		if (StringUtil.isNullOrEmpty(paper.getName())) {
			message += "�������ƴ���:���Ʋ���Ϊ��;";
		}
		
		if (!StringUtil.inLength(2, paper.getAuthor(), 4)) {
			message += "���ĵ�һ���ߴ���:�����������淶;";
		}
		
		if (paper.getPublishTime() == null) {
			message += "����ʱ�����:����Ϊ��,��ʽΪ yyyy-MM-dd;";
		}
		
		if (!StringUtil.inLength(0, paper.getAuthorComm(), 4)) {
			message += "����ͨѶ���ߴ���:�����������淶;";
		}
		
		if (!StringUtil.inArray(paper.getPaperType(), Constant.PAPER_TYPE)) {
			message += "�������ʹ���:��ѡֵ(JA��CA��JG);";
		}
		
		String[] params = StringUtil.split(paper.getIndexType(), ","); 
		for (String param : params) {
			if (!StringUtil.inArray(param, Constant.PAPER_INDEX_TYPE)) {
				message += "�������ʹ���:��ѡֵ(SCI��EI��CSCI��ISTP��CPCI;";
				break;
			}
		}
		
		if (!StringUtil.inLengthOrNull(0, paper.getAbstracts(), 2000)) {
			System.out.println(paper.getAbstracts().length());
			message += "ժҪ����:���ݳ���Ϊ2000����;";
		}
		
		return message;
	}

	public static String updateValidator(Paper paper) {
String message = "";

		if (!StringUtil.inLengthOrNull(2, paper.getAuthor(), 4)) {
			message += "���ĵ�һ���ߴ���:�����������淶;";
		}

		if (!StringUtil.inLengthOrNull(2, paper.getAuthorComm(), 4)) {
			message += "����ͨѶ���ߴ���:�����������淶;";
		}
		
		if (!StringUtil.isNullOrEmpty(paper.getIndexType())) {		
			String[] params = StringUtil.split(paper.getIndexType(), ","); 
			for (String param : params) {
				if (!StringUtil.inArray(param, Constant.PAPER_INDEX_TYPE)) {
					message += "�������ʹ���:��ѡֵ(SCI��EI��CSCI��ISTP��CPCI;";
					break;
				}
			}
		}
		if(!StringUtil.inArrayOrNull(
				paper.getPaperType(), Constant.PAPER_TYPE)) {
			message += "�������ʹ���:��ѡֵ(JA��CA��JG);";
		}
		
		return message;
	}
}
