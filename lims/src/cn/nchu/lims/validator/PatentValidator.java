package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Patent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.DateUtil;
import cn.nchu.lims.util.lang.StringUtil;

public class PatentValidator {
	
	/**
	 * �Խ�Ҫ��װ��Patent ���ֶβ�������У��
	 * @param patent : Patent
	 * @return message : String
	 */
	public static String validator(Patent patent) {
		String message = "";
		
		if(StringUtil.isNullOrEmpty(patent.getName())) {
			message += "������Ŀ����:,������Ŀ����Ϊ��";
		}
		
		if(StringUtil.isNullOrEmpty(patent.getAuthor())) {
			message += "��һ������Ϣ����:��һ���߱���,";
		}
		
		if(patent.getPublishTime() == null) {
			message += "����ʱ�����:����ʱ�䲻��Ϊ��,";
		}
		
		if(!DateUtil.isYearAndMonth(patent.getProcessingTime())) {
			message += "��Ȩʱ�����:��ʽӦΪ��yyyy-MM��,";
		}
		
		if(!StringUtil.inArray(
				patent.getPatentType(), Constant.PATENT_TYPE)) {
			message += "ר�����ʹ���:��ѡֵ(����ר�������ר����ʵ�����͡�����Ȩ);";
		}
		
		if(!StringUtil.inArray(
				patent.getStatus(), Constant.PATENT_STATUS)) {
			message += "ר����̬����:��ѡֵ(����������Ȩ);";
		}
		
		return message;
	}
	
	/**
	 * ����Projectʱ�Ĳ���У��
	 * @param project �� Project
	 * @return message : String
	 */
	public static String updateValidator(Patent patent) {
		String message = "";
		
		if(!DateUtil.isYearAndMonthOrEmpty(patent.getProcessingTime())) {
			message += "��Ȩʱ�����:��ʽӦΪ��yyyy-MM��,";
		}
		
		if(!StringUtil.inArrayOrNull(
				patent.getPatentType(), Constant.PATENT_TYPE)) {
			message += "ר�����ʹ���:��ѡֵ(����ר�������ר����ʵ������);";
		}
		
		if(!StringUtil.inArrayOrNull(
				patent.getStatus(), Constant.PATENT_STATUS)) {
			message += "ר����̬����:��ѡֵ(����������Ȩ);";
		}
		return message;
	}
	
}
