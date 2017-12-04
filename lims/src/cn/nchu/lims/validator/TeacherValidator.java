package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Teacher;
import  cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class TeacherValidator {
	/**
	 * 对teacher的全校验，每一个字段都进行相应的校验
	 * @param teacher : Teacher
	 * @return message : String 包含每一个字段的错误信息
	 */
	public static String validator(Teacher teacher) {
		String message = "";
		if(!StringUtil.inLength(2, teacher.getName(), 4)) {
			message += "姓名错误:姓名由2-4个汉字组成;";
		}	
		if(StringUtil.isNullOrEmpty(teacher.getMajor())) {
			message += "专业名称错误:不可为空;";
		}
		if(StringUtil.isNullOrEmpty(teacher.getCollege())) {
			message += "所属学院错误:不可为空;";
		}
		if(StringUtil.isNullOrEmpty(teacher.getGraduateCollege())) {
			message += "毕业院校错误:不可为空;";
		}
		if(StringUtil.isNullOrEmpty(teacher.getResearchInterest())) {
			message += "研究方向错误:不可为空;";
		}
		if(!StringUtil.inArray(
				teacher.getTitle(), Constant.TEACHER_TITLE)) {
			message += "职称错误:可选值为（教授、副教授、讲师、助教）;";
		}
		if(!StringUtil.inArray(
				teacher.getDirector(), Constant.TEACHER_DIRECTOR)) {
			message += "师资等级错误:可选值为（硕导、博导、无）;";
		}
		if(!StringUtil.isEmail(teacher.getEmail())) {
			message += "电子邮箱错误:格式不不符;";
		}
		return message;
	}
	
	/**
	 * 对teacher的修改信息时的校验
	 * @param teacher : Teacher
	 * @return message : String 包含每一个字段的错误信息
	 */
	public static String updateValidator(Teacher teacher) {
		String message = "";
		if(!StringUtil.inArrayOrNull(
				teacher.getTitle(), Constant.TEACHER_TITLE)) {
			message += "职称错误:可选值为（教授、副教授、讲师、助教）;";
		}
		if(!StringUtil.inArrayOrNull(
				teacher.getDirector(), Constant.TEACHER_DIRECTOR)) {
			message += message += "师资等级错误:可选值为（硕导、博导、无）;";
		}
		if(!StringUtil.isEmailOrNull(teacher.getEmail())) {
			message += "电子邮箱错误:格式不不符;";
		}
		
		return message;
	}
}
