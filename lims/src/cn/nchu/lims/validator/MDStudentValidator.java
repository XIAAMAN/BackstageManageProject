package cn.nchu.lims.validator;

import cn.nchu.lims.domain.MDStudent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class MDStudentValidator {
	/**
	 * ��MDStudent��ȫУ�飬ÿһ���ֶζ�������Ӧ��У��
	 * @param mdStudent : MDStudent
	 * @return message : String ����ÿһ���ֶεĴ�����Ϣ
	 */
	public static String validator(MDStudent mdStudent) {
		String message = "";
		if(!StringUtil.inLength(2, mdStudent.getName(), 4)) {
			message += "��������:������2-4���������;";
		}	
		if(StringUtil.isNullOrEmpty(mdStudent.getMajor())) {
			message += "רҵ���ƴ���:����Ϊ��;";
		}
		if(StringUtil.isNullOrEmpty(mdStudent.getCollege())) {
			message += "����ѧԺ����:����Ϊ��;";
		}
		if(!StringUtil.inArray(mdStudent.getType(), Constant.MDSTUDENT_TYPE)) {
			message += "ѧ�����ʹ���:��ѡֵ(˶ʿ����ʿ);";
		}
		if(StringUtil.isNullOrEmpty(mdStudent.getResearchInterest())) {
			message += "�о��������:����Ϊ��;";
		}
		if(!StringUtil.isEmail(mdStudent.getEmail())) {
			message += "�����������:��ʽ������;";
		}
		return message;
	}
	
	/**
	 * ��MDStudent���޸���Ϣʱ��У��
	 * @param mdStudent : MDStudent
	 * @return message : String ����ÿһ���ֶεĴ�����Ϣ
	 */
	public static String updateValidator(MDStudent mdStudent) {
		String message = "";
		if(!StringUtil.inLengthOrNull(2, mdStudent.getName(), 4)) {
			message += "��������:������2-4���������;";
		}
		if (!StringUtil.inArrayOrNull(mdStudent.getType(), Constant.MDSTUDENT_TYPE)) {
			message += "ѧ�����ʹ���:��ѡֵ(˶ʿ����ʿ);";
		}
		return message;
	}
}
