package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Patent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.DateUtil;
import cn.nchu.lims.util.lang.StringUtil;

public class PatentValidator {
	
	/**
	 * 对将要封装成Patent 的字段参数进行校验
	 * @param patent : Patent
	 * @return message : String
	 */
	public static String validator(Patent patent) {
		String message = "";
		
		if(StringUtil.isNullOrEmpty(patent.getName())) {
			message += "论文题目错误:,论文题目不可为空";
		}
		
		if(StringUtil.isNullOrEmpty(patent.getAuthor())) {
			message += "第一作者信息错误:第一作者必填,";
		}
		
		if(patent.getPublishTime() == null) {
			message += "发表时间错误:发表时间不可为空,";
		}
		
		if(!DateUtil.isYearAndMonth(patent.getProcessingTime())) {
			message += "授权时间错误:格式应为（yyyy-MM）,";
		}
		
		if(!StringUtil.inArray(
				patent.getPatentType(), Constant.PATENT_TYPE)) {
			message += "专利类型错误:可选值(发明专利、外观专利、实用新型、著作权);";
		}
		
		if(!StringUtil.inArray(
				patent.getStatus(), Constant.PATENT_STATUS)) {
			message += "专利类态错误:可选值(已受理、已授权);";
		}
		
		return message;
	}
	
	/**
	 * 更新Project时的参数校验
	 * @param project ： Project
	 * @return message : String
	 */
	public static String updateValidator(Patent patent) {
		String message = "";
		
		if(!DateUtil.isYearAndMonthOrEmpty(patent.getProcessingTime())) {
			message += "授权时间错误:格式应为（yyyy-MM）,";
		}
		
		if(!StringUtil.inArrayOrNull(
				patent.getPatentType(), Constant.PATENT_TYPE)) {
			message += "专利类型错误:可选值(发明专利、外观专利、实用新型);";
		}
		
		if(!StringUtil.inArrayOrNull(
				patent.getStatus(), Constant.PATENT_STATUS)) {
			message += "专利类态错误:可选值(已受理、已授权);";
		}
		return message;
	}
	
}
