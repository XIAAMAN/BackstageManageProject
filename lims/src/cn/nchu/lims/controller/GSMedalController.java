package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nchu.lims.dao.GSMedalDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.GSMedal;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.GSMedalValidator;

@Controller
@RequestMapping(value="/gsmedal")
public class GSMedalController {

	/**
	 * �Զ�ƥ��PatentDao�ӿ�
	 */
	@Autowired
	private GSMedalDao gsMedalDao;
	
	/**
	 * �Զ�ע��WebServic������ģ�黯ģ�����
	 */
	@Autowired
	private EnclosureService webService;
	
	@Autowired
	private GSMedalValidator gsMedalValidator;
	
	
	/**
	 * ����ǰ̨��װ�Ĳ��������ݿ����GSMedal��¼
	 * 	 �޸ĸ����������Ϊ��ǰע��GSMedal���
	 * @param param :  AjaxJsonRequestSubjectAndFileIdsParam<GSMedal>
	 * 	param.param ����������
	 *  param.fileIds �������
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<GSMedal> param) {
		GSMedal gsMedal = param.getParam();  // ��ȡPatent��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		String message = gsMedalValidator.validator(gsMedal);
		if(StringUtil.isNullOrEmpty(message)) {  // ������Ϣ�Ƿ���Ϲ淶�����ϼ��������򷵻ش�����Ϣ
			gsMedalDao.save(gsMedal);  // �����ݿ����Patent��Ϣ
			webService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Patent��
					fileIds, gsMedal.getId(), Constant.GSMEDALENCLOSURE_TABLE, "gsmedalId");
			return new AjaxJsonReturnParam(0, gsMedal);  // ���سɹ���Ϣ
		}
		return new AjaxJsonReturnParam(1, message);  // ����ʧ����Ϣ
	}
	
	/**
	 * ����ID����GSMedalDao.deleteɾ��News��Ϣ
	 * �����gsMedal����������·���ļ�
	 * @param gsMedal : GSMedal
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody GSMedal gsMedal, HttpServletRequest request) {
		GSMedal oldGsMedalp = gsMedalDao.get(gsMedal);  // ����gsMedasDao.get()��ѯ��ɾ��gsMedal
		if (oldGsMedalp != null) {  
			if(!oldGsMedalp.getEnclosures().isEmpty()) {  // �ж��Ƿ��и���
				for (Enclosure enclosure : oldGsMedalp.getEnclosures()) {  // �����������и����ڴ��������е�������Ϣ
					EnclosureUtil.clearFileOnDisk(
							enclosure.getFileName(), request, Constant.UPLOAD_GSMEDALENCLOSURE_URL);
				}
			}
			gsMedalDao.delete(oldGsMedalp);  // ���ڣ�����gsMedalDao.delete()ɾ�����ݿ��д�GSMedal��Ϣ
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "����������ѯר��");  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * ����GSMedal�����޸����ݿ��з��������ļ�¼
	 * @param gsMedal : GSMedal
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<GSMedal> param) {
		GSMedal gsMedal = param.getParam();  // ��ȡgsMedal��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		String message = gsMedalValidator.updateValidator(gsMedal);
		if(StringUtil.isNullOrEmpty(message)) {
			GSMedal result = gsMedalDao.get(gsMedal);  // ����gsMedalDao.get()���������ݿ��ѯPaten��Ϣ
			if(result != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
				gsMedalDao.updateDyna(gsMedal);  // �������ݿ���Ϣ
				webService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Patent��
						fileIds, gsMedal.getId(), Constant.GSMEDALENCLOSURE_TABLE, "gsmedalId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "����������ѯר��");  // ����ѯ��ϢΪnull������ 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * ����ID��ѯPatent��Ϣ�������������Ϣ
	 * @param gsMedal : Patent
	 * @return
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody GSMedal gsMedal) {
		try {
			GSMedal result = gsMedalDao.get(gsMedal);  // ����patentDao.get()���������ݿ��ѯPatent��Ϣ
			if(result != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "����������ѯ����");  // ����ѯ��ϢΪnull������ 1
			}
		} catch(Exception e) {
			return new AjaxJsonReturnParam(1, "�����쳣������ϵ������Ա");  // ����ʧ����Ϣ
		}
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
	public AjaxJsonReturnDynaPageParam<GSMedal> listDynaPageMap(@RequestBody Map<String, Object> params) {
		// ��װ��ѯ����
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// �õ���ѯ��Ϣ������
		int recordCount =gsMedalDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// �����ҳ��Ϣ
		params.put("pageModel", pageModel);
		// ����project.listDynaPageMap��ѯproject��Ϣ
		List<GSMedal> gsMedals = gsMedalDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<GSMedal>(recordCount, gsMedals);
	}
}
