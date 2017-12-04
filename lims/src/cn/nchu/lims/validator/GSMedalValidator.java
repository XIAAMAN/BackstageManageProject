package cn.nchu.lims.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.GSMedal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

@Component
public class GSMedalValidator {
	
	/**
	 * ʡ����Service��
	 * �Զ�ע��TeacherDao
	 */
	@Autowired
	private TeacherDao teacherDao;

	public String validator(GSMedal gsMedal) {
		String message = "";
		if (StringUtil.isNullOrEmpty(gsMedal.getName())) {
			message += "���ƴ���:�������Ʋ���Ϊ��,";
		}
		if(teacherDao.get(gsMedal.getTeacher()) == null) {  // ���teacherId �Ƿ����
			message += "�����˱�Ŵ���:�����˱�Ų�����;";
		}
		if(!StringUtil.inArray(
				gsMedal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "��Ŀ�ȼ�����:��ѡֵ�������Ҽ���ʡ��������������У��������;";
		}
		if(!StringUtil.inArray(
				gsMedal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "��Ŀ�ȼ�����:��ѡֵ���صȽ���һ�Ƚ������Ƚ������Ƚ���������;";
		}
		if(gsMedal.getWinningTime() == null) {
			message += "��ʱ��:��ʱ���ʽ����;";
		}
		return message;
	}

	public String updateValidator(GSMedal gsMedal) {
		String message = "";
		
		if(teacherDao.get(gsMedal.getTeacher()) == null) {  // ���teacherId �Ƿ����
			message += "�����˱�Ŵ���:�����˱�Ų�����;";
		}
		if(!StringUtil.inArrayOrNull(
				gsMedal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "��Ŀ�ȼ�����:��ѡֵ�������Ҽ���ʡ��������������У��������;";
		}
		if(!StringUtil.inArrayOrNull(
				gsMedal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "��Ŀ�ȼ�����:��ѡֵ���صȽ���һ�Ƚ������Ƚ������Ƚ���������;";
		}
		return message;
	}
}
