package cn.nchu.lims.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.MDStudentDao;
import cn.nchu.lims.domain.MDStudent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.MDStudentValidator;

@Controller
@RequestMapping(value="/mdstudent")
public class MDStudentController {

	/**
	 * �Զ�ע��MDStudentDao������Notice�����ݿ����
	 */
	@Autowired
	private MDStudentDao mdStudentDao;
	
	/**
	 * ��̬����MDStudent��Ϣ
	 * @param mdStudent : MDStudetn
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody MDStudent mdStudent) {
		try {
			// ����mdStudentValidator.validator�����ֶ��Ƿ���Ϲ淶
			String message = MDStudentValidator.validator(mdStudent);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // ���û�д�����Ϣ������״ֵ̬ 0 ���ɹ���
				// ����mdStudentDao.save����mdStudent��Ϣ
				mdStudentDao.save(mdStudent); 
				// ����ɹ� 0,����ѯ����teacher�����ص�����information������
				return new AjaxJsonReturnParam(0);  
			} 
			return new AjaxJsonReturnParam(1, message);   //����ʧ��״̬1��������Ϣ
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣���������Ա��ϵ ");   //����ʧ��״̬1��������Ϣ
		}
	}
	
	/**
	 * ͨ��idɾ��MDStudent��Ϣ
	 * @param mdStudent : MDStudent
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public AjaxJsonReturnParam delete(
			@RequestBody MDStudent mdStudent, HttpServletRequest request) {
		
		mdStudent = mdStudentDao.get(mdStudent);  // ����mdStudentDao.get��ѯmdStudent��Ϣ
		
		if(mdStudent != null) {// ����,���سɹ�״̬0
			mdStudentDao.delete(mdStudent);  // ����mdStudentDao.deleteɾ��mdStudent��Ϣ
			
			EnclosureUtil.clearFileOnDisk(  // �������·���µ�ͷ���ļ�
					mdStudent.getPhoto(), request, Constant.UPLOAD_MDSTUDENTENCLOSURE_URL);
			
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "ѧ����Ŵ���:��Ų�����");  // �����ڣ�����ʧ��״̬0��������Ϣ
	}
	
	/**
	 * ��̬����teacher��Ϣ
	 * @param mdStudent : MDStudent
	 * @param AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody MDStudent mdStudent, HttpServletRequest request) {
		
		MDStudent student = mdStudentDao.get(mdStudent);  // ����mdStudentDao.get��ѯmdStudent��Ϣ
		
		if(student != null) { // mdStudent���ڣ����
			// ����mdStudentValidator.validator�����ֶ��Ƿ���Ϲ淶
			String message = MDStudentValidator.updateValidator(mdStudent);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // ���û�д�����Ϣ������״ֵ̬ 0 ���ɹ���
				/* 
				 * ������޸ļ�¼photo�ֶ���Ϊ��Ϊ�ջ���photo���޸�
				 * ɾ��ԭ����·���µ�ͷ���ļ����滻�����ڵ��ļ�
				 * �޸�photo�ֶ���Ϣ
				 */
				if (!StringUtil.isNullOrEmpty(student.getPhoto()) 
						&& !student.getPhoto().equals(mdStudent.getPhoto())) {  
					EnclosureUtil.clearFileOnDisk( 
							student.getPhoto(), request, Constant.UPLOAD_MDSTUDENTENCLOSURE_URL);
				}
				mdStudentDao.updateDyna(mdStudent);  // ����mdStudentDao.update����mdStudent��Ϣ
				mdStudent = mdStudentDao.get(mdStudent);  // ����mdStudentDao.get�õ��޸ĺ��mdStudent��Ϣ
				return new AjaxJsonReturnParam(0, mdStudent);  	// ���سɹ�״̬0��mdStudent��Ϣ
			} 
			return new AjaxJsonReturnParam(1, message);
		}
		return new AjaxJsonReturnParam(1, "ѧ����Ŵ���:��Ų�����");  // mdStudent�����ڣ�����ʧ��״̬0��������Ϣ
	}
	
	/**
	 * ����Id��ѯMDStudent��Ϣ
	 * @param mdStudent : MDStudent
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody MDStudent mdStudent) {
		mdStudent = mdStudentDao.get(mdStudent);
		if(mdStudent != null) {
			return new AjaxJsonReturnParam(0, mdStudent);
		}
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ��̬��ѯMDStudent��Ϣ
	 * @param teacher : MDStudent
	 * @return ���϶�̬��ѯ��MDStudent �б�
	 */
	@RequestMapping("/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<MDStudent> listDynaPage(
			@RequestBody AjaxJsonRequestDynaPageParam<MDStudent> ajrdpp) {
		
		int recordCount  // ����ǰ̨��װ��AjaxJsonRequestDynaPageParam.param��Ϣ����ѯ��Ϣ������
			= mdStudentDao.listDyna(ajrdpp.getParam()).size();
		ajrdpp.getPageModel().setRecordCount(recordCount);  // �����ѯ��������MDStudent��Ϣ����
		List<MDStudent> teachers = mdStudentDao.listDynaPage(ajrdpp);  // ��̬��ѯMDStudent��Ϣ
		return new AjaxJsonReturnDynaPageParam<MDStudent>(recordCount, teachers);
	}
	
}
