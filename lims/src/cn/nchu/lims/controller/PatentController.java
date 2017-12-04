package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.PatentDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.Patent;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.PatentValidator;

@Controller
@RequestMapping(value="/patent")
public class PatentController {
	
	/**
	 * �Զ�ƥ��PatentDao�ӿ�
	 */
	@Autowired
	private PatentDao patentDao;
	
	/**
	 * �Զ�ע��WebServic������ģ�黯ģ�����
	 */
	@Autowired
	private EnclosureService webService;
	
	/**
	 * ����ǰ̨��װ�Ĳ��������ݿ����Paten��¼
	 * 	 �޸ĸ����������Ϊ��ǰע��Patent���
	 * @param param :  AjaxJsonRequestSubjectAndFileIdsParam<Patent>
	 * 	param.param ����������
	 *  param.fileIds �������
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Patent> param) {
		Patent patent = param.getParam();  // ��ȡPatent��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		String message = PatentValidator.validator(patent);
		if(StringUtil.isNullOrEmpty(message)) {  // ������Ϣ�Ƿ���Ϲ淶�����ϼ��������򷵻ش�����Ϣ
			patentDao.save(patent);  // �����ݿ����Patent��Ϣ
			webService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Patent��
					fileIds, patent.getId(), Constant.PATENTENCLOSURE_TABLE, "patentId");
			return new AjaxJsonReturnParam(0, patent);  // ���سɹ���Ϣ
		}
		return new AjaxJsonReturnParam(1, message);  // ����ʧ����Ϣ
	}
	
	/**
	 * ����ID����PatentDao.deleteɾ��News��Ϣ
	 * �����Patent����������·���ļ�
	 * @param patent : Patent
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody Patent patent, HttpServletRequest request) {
		Patent p = patentDao.get(patent);  // ����patentsDao.get()��ѯ��ɾ��Patent
		if (p != null) {  
			if(!p.getEnclosures().isEmpty()) {  // �ж��Ƿ��и���
				for (Enclosure enclosure : p.getEnclosures()) {  // �����������и����ڴ��������е�������Ϣ
					EnclosureUtil.clearFileOnDisk(
							enclosure.getFileName(), request, Constant.UPLOAD_PATENTENCLOSURE_URL);
				}
			}
			patentDao.delete(p);  // ���ڣ�����patentDao.delete()ɾ�����ݿ��д�Patent��Ϣ
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "����������ѯר��");  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * ����Patent�����޸����ݿ��з��������ļ�¼
	 * @param patent : Patent
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Patent> param) {
		Patent patent = param.getParam();  // ��ȡPatent��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		String message = PatentValidator.updateValidator(patent);
		if(StringUtil.isNullOrEmpty(message)) {
			Patent result = patentDao.get(patent);  // ����patenDao.get()���������ݿ��ѯPaten��Ϣ
			if(result != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
				patentDao.updateDyna(patent);  // �������ݿ���Ϣ
				webService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Patent��
						fileIds, patent.getId(), Constant.PATENTENCLOSURE_TABLE, "patentId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "����������ѯר��");  // ����ѯ��ϢΪnull������ 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * ����ID��ѯPatent��Ϣ�������������Ϣ
	 * @param patent : Patent
	 * @return
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Patent patent) {
		try {
			Patent result = patentDao.get(patent);  // ����patentDao.get()���������ݿ��ѯPatent��Ϣ
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
	 * ���ݶ�̬����Map��ҳ��ѯPatent��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * ��Ϊ�����ڵĲ�ѯ�����ܹ��ܺõķ�װ��Patent����������Map���д�ֵ
	 * �ѷ�ҳ��Ϣ��װ�룬pageModel
	 * @param params ��̬����
	 * @return ������Ϣ��News�б�
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Patent> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
		
		// ��װ��ѯ����
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// �õ���ѯ��Ϣ������
		int recordCount = patentDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// �����ҳ��Ϣ
		params.put("pageModel", pageModel);
		// ����project.listDynaPageMap��ѯproject��Ϣ
		List<Patent> projects = patentDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Patent>(recordCount, projects);
	}

}
