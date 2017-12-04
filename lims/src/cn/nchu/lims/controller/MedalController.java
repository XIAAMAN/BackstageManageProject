package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.MedalDao;
import cn.nchu.lims.domain.Medal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.MedalValidator;

@Controller
@RequestMapping(value="/medal")
public class MedalController {

	@Autowired
	MedalDao medalDao;
	
	@Autowired
	MedalValidator medalValidator;
	/**
	 * ��̬����Medal��Ϣ
	 * @param medal : Medal
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody Medal medal) {
		try {
			// ����MedalValidator.validator�����ֶ��Ƿ���Ϲ淶
			String message = medalValidator.validator(medal);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // ���û�д�����Ϣ������״ֵ̬ 0 ���ɹ���
				// ����medalDao.save����mdStudent��Ϣ
				medalDao.save(medal); 
				// ����ɹ� 0,����ѯ����medal�����ص�����information������
				return new AjaxJsonReturnParam(0);  
			} 
			return new AjaxJsonReturnParam(1, message);   //����ʧ��״̬1��������Ϣ
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣���������Ա��ϵ ");   //����ʧ��״̬1��������Ϣ
		}
	}
	
	/**
	 * ͨ��idɾ��Medal��Ϣ
	 * @param medal : Medal
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public AjaxJsonReturnParam delete(
			@RequestBody Medal medal, HttpServletRequest request) {
		
		medal = medalDao.get(medal);  // ����medalDao.get��ѯmedal��Ϣ
		
		if(medal != null) {// ����,���سɹ�״̬0
			medalDao.delete(medal);  // ����medalDao.deleteɾ��medal��Ϣ
			
			EnclosureUtil.clearFileOnDisk(  // �������·���µ�ͷ���ļ�
					medal.getPhoto(), request, Constant.UPLOAD_MEDALENCLOSURE_URL);
			
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "ѧ����Ŵ���:��Ų�����");  // �����ڣ�����ʧ��״̬0��������Ϣ
	}
	
	/**
	 * ��̬����teacher��Ϣ
	 * @param medal : Medal
	 * @param AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody Medal medal, HttpServletRequest request) {
		
		Medal oldMedal = medalDao.get(medal);  // ����medalDao.get��ѯmdStudent��Ϣ
		try {
			if(oldMedal != null) { // medal���ڣ����
				// ����medalValidator.validator�����ֶ��Ƿ���Ϲ淶
				String message = medalValidator.updateValidator(medal);  
				
				if (StringUtil.isNullOrEmpty(message)) {  // ���û�д�����Ϣ������״ֵ̬ 0 ���ɹ���
					/* 
					 * ������޸ļ�¼photo�ֶ���Ϊ��Ϊ�ջ���photo���޸�
					 * ɾ��ԭ����·���µ�ͷ���ļ����滻�����ڵ��ļ�
					 * �޸�photo�ֶ���Ϣ
					 */
					if (!StringUtil.isNullOrEmpty(oldMedal.getPhoto()) 
							&& !oldMedal.getPhoto().equals(medal.getPhoto())) {  
						EnclosureUtil.clearFileOnDisk( 
								oldMedal.getPhoto(), request, Constant.UPLOAD_MEDALENCLOSURE_URL);
					}
					medalDao.updateDyna(medal);  // ����medalDao.update����mdStudent��Ϣ
					medal = medalDao.get(medal);  // ����medalDao.get�õ��޸ĺ��mdStudent��Ϣ
					return new AjaxJsonReturnParam(0, medal);  	// ���سɹ�״̬0��mdStudent��Ϣ
				} 
				return new AjaxJsonReturnParam(1, message);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new AjaxJsonReturnParam(1, "��Ŵ���:��Ų�����");  // medal�����ڣ�����ʧ��״̬0��������Ϣ
	}
	
	/**
	 * ����Id��ѯMedal��Ϣ
	 * @param medal : Medal
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Medal medal) {
		medal = medalDao.get(medal);
		if(medal != null) {
			return new AjaxJsonReturnParam(0, medal);
		}
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ���ݶ�̬����Map��ҳ��ѯmedal��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * ��Ϊ�����ڵĲ�ѯ�����ܹ��ܺõķ�װ��Medal����������Map���д�ֵ
	 * �ѷ�ҳ��Ϣ��װ�룬pageModel
	 * @param params ��̬����
	 * @return ������Ϣ��Project�б�
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Medal> listDynaPageMap(@RequestBody Map<String, Object> params) {
		// ��װ��ѯ����
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// �õ���ѯ��Ϣ������
		int recordCount =medalDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// �����ҳ��Ϣ
		params.put("pageModel", pageModel);
		// ����project.listDynaPageMap��ѯproject��Ϣ
		List<Medal> medal = medalDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Medal>(recordCount, medal);
	}
}
