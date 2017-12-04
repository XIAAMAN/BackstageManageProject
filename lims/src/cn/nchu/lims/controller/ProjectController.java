package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nchu.lims.dao.ProjectDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.Project;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.ProjectValidator;

@Controller
@RequestMapping(value="/project")
public class ProjectController {

	/**
	 * ʡ����Service��
	 * �Զ�ע��ProjectDao
	 */
	@Autowired
	private ProjectDao projectDao;
	
	/**
	 * �Զ�ע��WebServic������ģ�黯ģ�����
	 */
	@Autowired
	private EnclosureService enclosureService;
	
	@Autowired
	private ProjectValidator projectValidator;
	
	/**
	 * ����ǰ̨�Ĳ�������ݿ��в���һ��Project��Ϣ
	 *   ������fileIds��������project�������󶨵������ϴ��ĸ����������
	 * @param param : AjaxJsonRequestSubjectAndFileIdsParam
	 * @return AjaxJsonReturnParam
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Project> param) 
					throws Exception{
		
		try {
			Project project= param.getParam();  // ��ȡNotice��Ϣ
			List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
			String message = projectValidator.validator(project);
			
			if(StringUtil.isNullOrEmpty(message)) {  // ������Ϣ�Ƿ���Ϲ淶�����ϼ��������򷵻ش�����Ϣ
				projectDao.save(project);  // �����ݿ����Notice��Ϣ
				enclosureService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Paper��
						fileIds, project.getId(), Constant.PROJECTENCLOSURE_TABLE, "projectId");
				return new AjaxJsonReturnParam(0, project);  // ���سɹ���Ϣ
			} else {
				return new AjaxJsonReturnParam(1, message);  // ����ʧ����Ϣ
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣������ϵ������Ա");  // ����ʧ����Ϣ
		}
	}
	
	/**
	 * ����project.id ɾ�����ݿ���project��¼
	 *   ����������͸�������·����ʵ���ļ�
	 * @param project : Project
	 * @param request : HttpServletRequest
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete (
			@RequestBody Project project, HttpServletRequest request) {
	
		try {
			Project p = projectDao.get(project); 
			
			if(!p.getEnclosures().isEmpty()) {  // �ж��Ƿ��и���
				for (Enclosure enclosure : p.getEnclosures()) {  // �����������и����ڴ��������е�������Ϣ
					
					EnclosureUtil.clearFileOnDisk(  
							enclosure.getFileName()
							, request, Constant.UPLOAD_PROJECTENCLOSURE_URL);
				}
			}
			
			projectDao.delete(p);  // ���ڣ�����projectDao.delete()ɾ�����ݿ��д�project��Ϣ
			return new AjaxJsonReturnParam(0);
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣������ϵ������Ա");  // ����ʧ����Ϣ
		}
	}

	/**
	 * ����ǰ̨���ݵĲ�����ͨ��id��̬����Project��Ϣ
	 *   ���޸�����صĸ�����Ϣ
	 * @param param : AjaxJsonRequestSubjectAndFileIdsParam
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Project> param) {
		
		Project project = param.getParam();  // ��ȡProject��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		// ����projectValidator.validator�����ֶ��Ƿ���Ϲ淶
		String message = projectValidator.updateValidator(project);  
		
		if(StringUtil.isNullOrEmpty(message)) {
			
			Project result = projectDao.get(project);  // ����projectDao.get()���������ݿ��ѯProject��Ϣ
			
			if(result != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
				projectDao.updateDyna(project);  // �������ݿ���Ϣ
				enclosureService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Project��
						fileIds, project.getId(), Constant.PROJECTENCLOSURE_TABLE, "projectId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "����������ѯ��Ŀ");  // ����ѯ��ϢΪnull������ 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * ����id ��ѯProject ��Ϣ
	 * @param project : Project
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Project project) {
		project = projectDao.get(project);  // ����projectDao.get��ѯproject��Ϣ
		if(project != null) { 
			return new AjaxJsonReturnParam(0, project);
		}
		return new AjaxJsonReturnParam(1, "��Ŀ��Ŵ���:��Ų�����");  // �����ڣ�����ʧ��״̬0��������Ϣ
	} 
	
	/**
	 * ���ݶ�̬������ѯProject��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * @param project : Project
	 * @return List<Project>
	 */
	@RequestMapping(value="/listDyna")
	@ResponseBody
	public List<Project> list(@RequestBody Project project) {
		List<Project> projects = projectDao.listDyna(project);
		return projects;
	}
	
	/**
	 * ���ݶ�̬����Map��ҳ��ѯproject��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * ��Ϊ�����ڵĲ�ѯ�����ܹ��ܺõķ�װ��Projet����������Map���д�ֵ
	 * �ѷ�ҳ��Ϣ��װ�룬pageModel
	 * @param params ��̬����
	 * @return ������Ϣ��Project�б�
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Project> listDynaPageMap(@RequestBody Map<String, Object> params) {
		
		// ��װ��ѯ����
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		
		// �õ���ѯ��Ϣ������
		int recordCount = projectDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// �����ҳ��Ϣ
		params.put("pageModel", pageModel);
		// ����project.listDynaPageMap��ѯproject��Ϣ
		List<Project> projects = projectDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Project>(recordCount, projects);
	}
}
