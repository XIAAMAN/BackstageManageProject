package cn.nchu.lims.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Project;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.FloatUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

@Component
public class ProjectValidator {

	/**
	 * ʡ����Service��
	 * �Զ�ע��TeacherDao
	 */
	@Autowired
	private TeacherDao teacherDao;
	
	/**
	 * �Խ�Ҫ��װ��Project ���ֶβ�������У��
	 * @param params : Project
	 * @return message : String
	 */
	public String validator(Project project) {
		String message = "";
		if(StringUtil.isNullOrEmpty(project.getName().trim())) {
			message += "��Ŀ���ƴ���:��Ŀ���Ʋ���Ϊ��;";
		}
			
		if(teacherDao.get(project.getTeacher()) == null) {  // ���teacherId �Ƿ����
			message += "�����˱�Ŵ���:�����˱�Ų�����;";
		}
		if(StringUtil.isNullOrEmpty(project.getMember().trim())) {
			message += "��Ŀ��Ա����:��Ŀ��Ա������Ϊ��;";
		}
		if(FloatUtil.isNullOrZero(project.getFund())) {
			message += "��Ŀ����:��Ŀ����Ϊ������;";
		}
		if(StringUtil.isNullOrEmpty(project.getSource().trim())) {
			message += "��Ŀ��Դ����:��Ŀ��Դ����Ϊ��;";
		}
		if(!StringUtil.inArray(
				project.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "��Ŀ�ȼ�����:��ѡֵ(���Ҽ���ʡ��������������У��������);";
		}
		if(project.getStartDate() == null) {
			message += "��Ŀ��ʼʱ��:��Ŀ��ʼʱ�䲻��Ϊ��;";
		}
		
		return message;
	}
	
	/**
	 * ����Projectʱ�Ĳ���У��
	 * @param project �� Project
	 * @return message : String
	 */
	public String updateValidator(Project project) {
		String message = "";
		if(!IntegerUtil.isNullOrZero(project.getTeacher().getId())) {
			if(teacherDao.get(project.getTeacher()) == null) {
				message += "�����˱�Ŵ���:�����˱�Ų�����;";
			}
		}
		if(!StringUtil.inArrayOrNull(
				project.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "��Ŀ�ȼ�����:��ѡֵ(���Ҽ���ʡ��������������У��������);";
		}
		return message;
	}
	
}
