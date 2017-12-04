package cn.nchu.lims.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.AdminDao;
import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.util.MD5;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.AdminValidator;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	/**
	 * ʡ����Service��
	 * �Զ�ע��AdminDao
	 */
	@Autowired
	private AdminDao adminDao;
	
	/**
	 * �����û�������ǰ̨���������ݿ�������û���Ϣ
	* @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam saveDyan(@RequestBody Admin admin) {
		String message = AdminValidator.validator(admin);  // У������������ش�����Ϣ
		if(StringUtil.isNullOrEmpty(message)) {  // ���û�д�����Ϣ�������ݿ�������û���Ϣ�������سɹ���Ϣ
			try {  // ����MD5,����password����
				admin.setPassword(MD5.EncoderByMd5(admin.getPassword()));
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			adminDao.saveDyna(admin); //����adminDao.saveDyan�����ݴ������ݿ�
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, message);
	}
	
	/**
	 * ����IDɾ��admin��Ϣ
	* @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam deleteDyan(@RequestBody Admin admin) {	
		admin = adminDao.get(admin);  // ��ѯ�û��Ƿ����
		if(admin != null) {
			adminDao.delete(admin);  // ����adminDao.deleteɾ�����û�
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * �޸�admin��state��״̬��
	 * @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/updateState")
	@ResponseBody
	public AjaxJsonReturnParam updateState(@RequestBody Admin admin) {
		admin = adminDao.get(admin);  // ��ѯ���û����Ƿ����
		if(admin != null) {
			if( admin.getState() == 0) {  // ʵ��״̬��ת��
				admin.setState(1);
			} else if( admin.getState() == 1) {
				admin.setState(0);
			}
			adminDao.updateState(admin);  // �����޸ĺ����Ϣ
			return new AjaxJsonReturnParam(0, admin);
		} 
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ��������
	 */
	@RequestMapping(value="/resetPassword")
	@ResponseBody
	public AjaxJsonReturnParam resetPassword(@RequestBody Admin admin) {
		if(adminDao.get(admin) != null) {
			try {
				admin.setPassword(MD5.EncoderByMd5("1234567809"));
				adminDao.resetPassword(admin);
				return new AjaxJsonReturnParam(0, "�������óɹ�");
			} catch (Exception e) {
				return new AjaxJsonReturnParam(1, "�����쳣");
			}
		}
		return new AjaxJsonReturnParam(1, "�û�������");
	}
	
	/**
	 * ����ǰ̨���ݲ�������admin
	 * @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody Admin admin) {
		// ��ѯ���û����Ƿ����
		Admin ad = adminDao.get(admin);
		if(ad != null) {
			String message = AdminValidator.upateValidator(admin);  // �����²�������֤,���õ�������Ϣ
			if(StringUtil.isNullOrEmpty(message)) {
				adminDao.updateDyna(admin);
				ad = adminDao.get(admin);  // �õ����º��admin��Ϣ
				return new AjaxJsonReturnParam(0, null, ad);
			} else {
				return new AjaxJsonReturnParam(1, message);
			}
		} 
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ����Id��ѯAdmin��Ϣ
	 * @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Admin admin) {
		admin = adminDao.get(admin);
		if(admin != null) {
			return new AjaxJsonReturnParam(0, admin);
		}
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ���ݲ�����̬��ѯAdmin��Ϣ
	 * @param admin : admin
	 * @return admins : List<Admin>
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public List<Admin> listDyna(@RequestBody Admin admin) {
		List<Admin> admins = adminDao.listDyna(admin);
		return admins;
	}
	
	/**
	 * ���ݲ�����̬��ҳ��ѯ
	 * @param adpp : AjaxJsonRequestDynaPageParam<Admin> ����Admin �� PageModel
	 *   Admin ��װ��ѯ���������� PageModel ��װ��ҳ���� 
	 * @return AjaxJsonReturnDynaPageParam<Admin>
	 */
	@RequestMapping(value="/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Admin> listDynaPage(
			@RequestBody AjaxJsonRequestDynaPageParam<Admin> ajrdpp) {
		int recordCount = adminDao.listDyna(ajrdpp.getParam()).size();   // ����ǰ̨��װ��AjaxJsonRequestDynaPageParam.param��Ϣ����ѯ��Ϣ������
		ajrdpp.getPageModel().setRecordCount(recordCount);  // �����ѯ��������teacher��Ϣ����
		List<Admin> admins = adminDao.listDynaPage(ajrdpp);  // ����adminDao.listDynaPage��̬��ҳ��ѯadmin��Ϣ
		return new AjaxJsonReturnDynaPageParam<Admin>(recordCount, admins);  // ���ص�ǰҳ�����ݣ��ͷ��ϲ�ѯ��������Ϣ����
	}
	
}
