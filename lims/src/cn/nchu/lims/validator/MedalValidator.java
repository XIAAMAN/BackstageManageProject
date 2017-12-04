package cn.nchu.lims.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Medal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

@Component
public class MedalValidator {
	
	@Autowired
	private TeacherDao teacherDao;
	
	/**
	 * �Խ�Ҫ��װ��Medal ���ֶβ�������У��
	 * @param medal : Medal
	 * @return message : String
	 */
	public String validator(Medal medal) {
		String message = "";
		if(StringUtil.isNullOrEmpty(medal.getName())) {
			message += "��Ŀ���ƴ���:��Ŀ���Ʋ���Ϊ��;";
		}
		if(teacherDao.get(medal.getTeacher()) == null) {  // ���teacherId �Ƿ����
			message += "��ʦ��Ŵ���:�񽱽�ʦ������;";
		}
		if(!StringUtil.inArray(
				medal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "��Ŀ�ȼ�����:��ѡֵ�������Ҽ���ʡ��������������У��������;";
		}
		if(!StringUtil.inArray(
				medal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "��Ŀ�ȼ�����:��ѡֵ���صȽ���һ�Ƚ������Ƚ������Ƚ���������;";
		}
		if(!StringUtil.inArray(
				medal.getType(), Constant.MEDAL_TYPE)) {
			message += "��Ŀ�ȼ�����:��ѡֵ����ѧ�ɹ��������гɹ������������;";
		}
		if(IntegerUtil.isNullOrZero(medal.getRanking())) {
			message += "��������:����Ӧ���ڵ���1;";
		}
		if(medal.getWinningTime() == null) {
			message += "��ʱ��:��ʱ�䲻��Ϊ��;";
		}
		if(!("0".equals(medal.getSponsor()) || "1".equals(medal.getSponsor()))) {
			message += "��һ��λ����:��������Ϊ�գ�Ϊ0��1��;";
		}
		return message;
	}
	
	/**
	 * ����Projectʱ�Ĳ���У��
	 * @param medal �� Medal
	 * @return message : String
	 */
	public String updateValidator(Medal medal) {
		String message = "";
		if(!StringUtil.inArrayOrNull(
				medal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "��Ŀ�ȼ�����:��ѡֵ�������Ҽ���ʡ��������������У��������;";
		}
		if(!StringUtil.inArrayOrNull(
				medal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "��Ŀ�ȼ�����:��ѡֵ���صȽ���һ�Ƚ������Ƚ������Ƚ���������;";
		}
		if(!StringUtil.inArrayOrNull(
				medal.getType(), Constant.MEDAL_TYPE)) {
			message += "��Ŀ�ȼ�����:��ѡֵ����ѧ�ɹ��������гɹ������������;";
		}
		if(!StringUtil.isNullOrEmpty(medal.getSponsor()) && (
				!("0".equals(medal.getSponsor()) || "1".equals(medal.getSponsor())))) {
			message += "��һ��λ����:��������Ϊ�գ�Ϊ0��1��;";
		}
		return message;
	}
	
}
