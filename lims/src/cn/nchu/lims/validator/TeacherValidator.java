package cn.nchu.lims.validator;

import cn.nchu.lims.domain.Teacher;
import  cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class TeacherValidator {
	/**
	 * ��teacher��ȫУ�飬ÿһ���ֶζ�������Ӧ��У��
	 * @param teacher : Teacher
	 * @return message : String ����ÿһ���ֶεĴ�����Ϣ
	 */
	public static String validator(Teacher teacher) {
		String message = "";
		if(!StringUtil.inLength(2, teacher.getName(), 4)) {
			message += "��������:������2-4���������;";
		}	
		if(StringUtil.isNullOrEmpty(teacher.getMajor())) {
			message += "רҵ���ƴ���:����Ϊ��;";
		}
		if(StringUtil.isNullOrEmpty(teacher.getCollege())) {
			message += "����ѧԺ����:����Ϊ��;";
		}
		if(StringUtil.isNullOrEmpty(teacher.getGraduateCollege())) {
			message += "��ҵԺУ����:����Ϊ��;";
		}
		if(StringUtil.isNullOrEmpty(teacher.getResearchInterest())) {
			message += "�о��������:����Ϊ��;";
		}
		if(!StringUtil.inArray(
				teacher.getTitle(), Constant.TEACHER_TITLE)) {
			message += "ְ�ƴ���:��ѡֵΪ�����ڡ������ڡ���ʦ�����̣�;";
		}
		if(!StringUtil.inArray(
				teacher.getDirector(), Constant.TEACHER_DIRECTOR)) {
			message += "ʦ�ʵȼ�����:��ѡֵΪ��˶�����������ޣ�;";
		}
		if(!StringUtil.isEmail(teacher.getEmail())) {
			message += "�����������:��ʽ������;";
		}
		return message;
	}
	
	/**
	 * ��teacher���޸���Ϣʱ��У��
	 * @param teacher : Teacher
	 * @return message : String ����ÿһ���ֶεĴ�����Ϣ
	 */
	public static String updateValidator(Teacher teacher) {
		String message = "";
		if(!StringUtil.inArrayOrNull(
				teacher.getTitle(), Constant.TEACHER_TITLE)) {
			message += "ְ�ƴ���:��ѡֵΪ�����ڡ������ڡ���ʦ�����̣�;";
		}
		if(!StringUtil.inArrayOrNull(
				teacher.getDirector(), Constant.TEACHER_DIRECTOR)) {
			message += message += "ʦ�ʵȼ�����:��ѡֵΪ��˶�����������ޣ�;";
		}
		if(!StringUtil.isEmailOrNull(teacher.getEmail())) {
			message += "�����������:��ʽ������;";
		}
		
		return message;
	}
}
