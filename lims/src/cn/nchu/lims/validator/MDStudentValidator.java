package cn.nchu.lims.validator;

import cn.nchu.lims.domain.MDStudent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class MDStudentValidator {
	/**
	 * 对MDStudent的全校验，每一个字段都进行相应的校验
	 * @param mdStudent : MDStudent
	 * @return message : String 包含每一个字段的错误信息
	 */
	public static String validator(MDStudent mdStudent) {
		String message = "";
		if(!StringUtil.inLength(2, mdStudent.getName(), 4)) {
			message += "姓名错误:姓名由2-4个汉字组成;";
		}	
		if(StringUtil.isNullOrEmpty(mdStudent.getMajor())) {
			message += "专业名称错误:不可为空;";
		}
		if(StringUtil.isNullOrEmpty(mdStudent.getCollege())) {
			message += "所属学院错误:不可为空;";
		}
		if(!StringUtil.inArray(mdStudent.getType(), Constant.MDSTUDENT_TYPE)) {
			message += "学生类型错误:可选值(硕士、博士);";
		}
		if(StringUtil.isNullOrEmpty(mdStudent.getResearchInterest())) {
			message += "研究方向错误:不可为空;";
		}
		if(!StringUtil.isEmail(mdStudent.getEmail())) {
			message += "电子邮箱错误:格式不不符;";
		}
		return message;
	}
	
	/**
	 * 对MDStudent的修改信息时的校验
	 * @param mdStudent : MDStudent
	 * @return message : String 包含每一个字段的错误信息
	 */
	public static String updateValidator(MDStudent mdStudent) {
		String message = "";
		if(!StringUtil.inLengthOrNull(2, mdStudent.getName(), 4)) {
			message += "姓名错误:姓名由2-4个汉字组成;";
		}
		if (!StringUtil.inArrayOrNull(mdStudent.getType(), Constant.MDSTUDENT_TYPE)) {
			message += "学生类型错误:可选值(硕士、博士);";
		}
		return message;
	}
}
