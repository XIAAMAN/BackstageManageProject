package cn.nchu.lims.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Teacher;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.TeacherValidator;

@Controller
@RequestMapping(value="/teacher")
public class TeacherController {
	
	/**
	 * ʡ����Service��
	 * �Զ�ע��TeacherDao
	 */
	@Autowired
	private TeacherDao teacherDao;
	
	/**
	 * ��̬����teacher��Ϣ
	 * @param teacher : Teacher
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody Teacher teacher) {
		// ����teacherValidator.validator�����ֶ��Ƿ���Ϲ淶
		String message = TeacherValidator.validator(teacher);  
		// ���û�д�����Ϣ������״ֵ̬ 0 ���ɹ���
		if (StringUtil.isNullOrEmpty(message)) {  
			// ����teacherDao.save����teacher��Ϣ
			teacherDao.saveAndReturnId(teacher); 
			// ����ɹ� 0,����ѯ����teacher�����ص�����information������
			return new AjaxJsonReturnParam(0);  
		} 
		//����ʧ��״̬0��������Ϣ
		return new AjaxJsonReturnParam(1, message);   
	}
	
	/**
	 * ͨ��idɾ��teacher��Ϣ
	 * @param teacher : Teacher
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public AjaxJsonReturnParam delete(@RequestBody Teacher teacher, HttpServletRequest request) {
		teacher = teacherDao.get(teacher);  // ����teacherDao.get��ѯteacher��Ϣ
//		if(teacher != null) {
//			teacherDao.delete(teacher);  // ����teacherDao.deleteByIdɾ��teacher��Ϣ
//			
//			EnclosureUtil.clearFileOnDisk(  // ��������ļ�
//					teacher.getPhoto(), request, Constant.UPLOAD_TEACHERENCLOSURE_URL);
//			
//			return new AjaxJsonReturnParam(0);
//		} 
		
		if (teacher != null) {
			teacherDao.deleteState(teacher);
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, "��ʦ��Ŵ���:��Ų�����");  // �����ڣ�����ʧ��״̬0��������Ϣ
	}
	
	/**
	 * ��̬����teacher��Ϣ
	 * @param teacher : Teacher
	 * @param AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody Teacher teacher, HttpServletRequest request) {
		
		Teacher oldTeacher = teacherDao.get(teacher);  // ����teacherDao.get��ѯteacher��Ϣ
		if(oldTeacher != null) { // teache���ڣ����
			// ����teacherValidator.validator�����ֶ��Ƿ���Ϲ淶
			String message = TeacherValidator.updateValidator(teacher);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // ���û�д�����Ϣ������״ֵ̬ 0 ���ɹ���
				/* 
				 * ������޸ļ�¼photo�ֶ���Ϊ��Ϊ�ջ���photo���޸�
				 * ɾ��ԭ����·���µ�ͷ���ļ����滻�����ڵ��ļ�
				 * �޸�photo�ֶ���Ϣ
				 */
				if (!StringUtil.isNullOrEmpty(oldTeacher.getPhoto()) 
						&& !oldTeacher.getPhoto().equals(teacher.getPhoto())) {  
					EnclosureUtil.clearFileOnDisk( 
							oldTeacher.getPhoto(), request, Constant.UPLOAD_TEACHERENCLOSURE_URL);
				}
				teacherDao.update(teacher);  // ����teacherDao.update����teacher��Ϣ
				teacher = teacherDao.get(teacher);  // ����teacherDao.get�õ��޸ĺ��teacher��Ϣ
				return new AjaxJsonReturnParam(0,teacher);  	// ���سɹ�״̬0��teacher��Ϣ
			} 
			return new AjaxJsonReturnParam(1, message);
		}
		return new AjaxJsonReturnParam(1, "��ʦ��Ŵ���:��Ų�����");  // teacher�����ڣ�����ʧ��״̬0��������Ϣ
	}
	
	/**
	 * ͨ��id��ѯteacher��Ϣ
	 * @param teacher : Teacher
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Teacher teacher) {
		teacher = teacherDao.get(teacher);  // ����teacherDao.get��ѯteacher��Ϣ
		
		if(teacher != null) { 
			// ����,���سɹ�״̬0��message��teacher��Ϣ
			return new AjaxJsonReturnParam(0, null, teacher);  
		}
		return new AjaxJsonReturnParam(1, "��ʦ��Ŵ���:��Ų�����");  // �����ڣ�����ʧ��״̬0��������Ϣ
	}
	
	/**
	 * ��̬��ѯ���õ�����teacher��Ϣ
	 * @return list : List<Teacher> ����teacher��Ϣ
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<Teacher> list() {
		// ����teacherDao.list�������õ�����teacher��Ϣ
		List<Teacher> teachers = teacherDao.list();  
		return teachers;
	}
	
	/**
	 * ��̬��ѯteacher��Ϣ
	 * @param teacher : Teacher
	 * @return ���϶�̬��ѯ��teacher �б�
	 */
	@RequestMapping("/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Teacher> listDynaPage(
			@RequestBody AjaxJsonRequestDynaPageParam<Teacher> ajrdpp) {
		int recordCount  // ����ǰ̨��װ��AjaxJsonRequestDynaPageParam.param��Ϣ����ѯ��Ϣ������
			= teacherDao.listDyna(ajrdpp.getParam()).size();
		ajrdpp.getPageModel().setRecordCount(recordCount);  // �����ѯ��������teacher��Ϣ����
		List<Teacher> teachers = teacherDao.listDynaPage(ajrdpp);  // ��̬��ѯteacher��Ϣ
		return new AjaxJsonReturnDynaPageParam<Teacher>(recordCount, teachers);
	}
	
}
