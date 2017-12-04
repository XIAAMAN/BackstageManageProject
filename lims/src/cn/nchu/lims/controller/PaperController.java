package cn.nchu.lims.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.PaperDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.Paper;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.PaperValidator;

@Controller
@RequestMapping(value="/paper")
public class PaperController {

	/**
	 * �Զ�ע��PaperDao������Notice�����ݿ����
	 */
	@Autowired
	private PaperDao paperDao;
	
	/**
	 * �Զ�ע��WebServic������ģ�黯ģ�����
	 */
	@Autowired
	private EnclosureService webService;
	
	/**
	 * ����ǰ̨�Ĳ�������ݿ��в���һ��paper��Ϣ
	 *   ������fileIds��������paper�������󶨵������ϴ��ĸ����������
	 * @param param : AjaxJsonRequestSubjectAndFileIdsParam
	 * @return AjaxJsonReturnParam
	 * @throws Exception
	 */
	@Transactional  // ����ע��
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Paper> param) 
					throws Exception{
		
		try {
			Paper paper= param.getParam();  // ��ȡNotice��Ϣ
			List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
			String message = PaperValidator.validator(paper);
			
			if(StringUtil.isNullOrEmpty(message)) {  // ������Ϣ�Ƿ���Ϲ淶�����ϼ��������򷵻ش�����Ϣ
				paperDao.save(paper);  // �����ݿ����Notice��Ϣ
				webService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Paper��
						fileIds, paper.getId(), Constant.PAPERENCLOSURE_TABLE, "paperId");
				return new AjaxJsonReturnParam(0, paper);  // ���سɹ���Ϣ
			} else {
				return new AjaxJsonReturnParam(1, message);  // ����ʧ����Ϣ
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣������ϵ������Ա");  // ����ʧ����Ϣ
		}
	}
	
	/**
	 * ����paper.id ɾ�����ݿ���paper��¼
	 *   ����������͸�������·����ʵ���ļ�
	 * @param paper : Paper
	 * @param request : HttpServletRequest
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete (
			@RequestBody Paper paper, HttpServletRequest request) {
	
		try {
			Paper p = paperDao.get(paper);
			
			if (p != null) {  // ����paperDao.get()��ѯ��ɾ��Paper�Ƿ����
				if(!p.getEnclosures().isEmpty()) {  // �ж��Ƿ��и���
					for (Enclosure enclosure : p.getEnclosures()) {  // �����������и����ڴ��������е�������Ϣ
						
						EnclosureUtil.clearFileOnDisk(
								enclosure.getFileName()
								, request, Constant.UPLOAD_PAPERENCLOSURE_URL);
					}
				}
				
				paperDao.delete(p);  // ���ڣ�����noticeDao.delete()ɾ�����ݿ��д�Notice��Ϣ
				return new AjaxJsonReturnParam(0);
			} else {
				return new AjaxJsonReturnParam(1, "����������ѯ����");  // ����ѯ��ϢΪnull������ 1
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣������ϵ������Ա");  // ����ʧ����Ϣ
		}
	}
	
	/**
	 * ����ǰ̨���ݵĲ�����ͨ��id��̬����Paper��Ϣ
	 *   ���޸�����صĸ�����Ϣ
	 * @param param : AjaxJsonRequestSubjectAndFileIdsParam
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Paper> param) {
		
		Paper paper = param.getParam();  // ��ȡPaper��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		String message = PaperValidator.updateValidator(paper);  // ��Ϣ��֤
		
		if(StringUtil.isNullOrEmpty(message)) {
			Paper result = paperDao.get(paper);  // ����paperDao.get()���������ݿ��ѯPaper��Ϣ
			if(result != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
				paperDao.updateDyna(paper);  // �������ݿ���Ϣ
				webService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Paper��
						fileIds, paper.getId(), Constant.PAPERENCLOSURE_TABLE, "paperId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "����������ѯ����");  // ����ѯ��ϢΪnull������ 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * ����ID��ѯPaper��Ϣ�������������Ϣ
	 * @param paper : Paper
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Paper paper) {
		try {
			
			Paper result = paperDao.get(paper);  // ����paperDao.get()���������ݿ��ѯPaper��Ϣ
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
	 * ���ݶ�̬����Map��ҳ��ѯPaper��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * ��Ϊ�����ڵĲ�ѯ�����ܹ��ܺõķ�װ��Paper����������Map���д�ֵ
	 * �ѷ�ҳ��Ϣ��װ�룬pageModel
	 * @param params : Map ��̬����
	 * @return ������Ϣ��Paper�б�
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Paper> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
		
		PageModel pageModel = new PageModel();  // ��װ��ѯ����
		
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		
		List<String> indexTypeList = Arrays.asList(StringUtil.split((String)params.get("indexType"), ","));
		if (!StringUtil.isNullOrEmpty(indexTypeList.get(0))) {
			params.put("indexType", indexTypeList);
		} else {
			params.put("indexType", null);
		}
		
		int recordCount = paperDao.listDynaPageMap(params).size();  // �õ���ѯ��Ϣ������
		pageModel.setRecordCount(recordCount);
		params.put("pageModel", pageModel);  // �����ҳ��Ϣ
		
		// ����paperDao.listDynaPageMap��ѯproject��Ϣ
		List<Paper> projects = paperDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Paper>(recordCount, projects);
	}
}
