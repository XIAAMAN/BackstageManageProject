package cn.nchu.lims.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.NoticeDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.Notice;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.DateUtil;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.NoticeValidator;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {

	/**
	 * �Զ�ע��NoticeDao������Notice�����ݿ����
	 */
	@Autowired
	private NoticeDao noticeDao;
	
	/**
	 * �Զ�ע��WebServic������ģ�黯ģ�����
	 */
	@Autowired
	private EnclosureService enclosureService;
	
	/**
	 * ���ݲ��������ݿⱣ��Notice��Ϣ
	 * ����ǰ̨���ݵ�fileIds����ID�б���NoticeEnclosure�������noticeId
	 * @param notice
	 * @return
	 */
	@Transactional  // ����ע��
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Notice> param) throws Exception{
		try {
			Notice notice = param.getParam();  // ��ȡNotice��Ϣ
			List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
			String message = NoticeValidator.validator(notice);
			if(StringUtil.isNullOrEmpty(message)) {  // ������Ϣ�Ƿ���Ϲ淶�����ϼ��������򷵻ش�����Ϣ
				/** �����AOP��ע�ⷽʽ **/
				if (notice.getPublishTime() == null) {  // ���publishTimeΪnull��Ĭ��Ϊ��ǰʱ��
					notice.setPublishTime(Calendar.getInstance().getTime());
				}
				noticeDao.save(notice);  // �����ݿ����Notice��Ϣ
				enclosureService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Notice��
						fileIds, notice.getId(), Constant.NOTICEENCLOSURE_TABLE, "noticeId");
				return new AjaxJsonReturnParam(0, notice);  // ���سɹ���Ϣ
			} else {
				return new AjaxJsonReturnParam(1, message);  // ����ʧ����Ϣ
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣������ϵ������Ա");  // ����ʧ����Ϣ
		}
	}
	
	/**
	 * ����ID����NoticeDao.deleteɾ��Notice��Ϣ
	 * �����Notice����������·���ļ�
	 * @param n : Notice
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody Notice notice, HttpServletRequest request) {
		try {
			Notice n = noticeDao.get(notice);
			if (noticeDao.get(n) != null) {  // ����noticeDao.get()��ѯ��ɾ��Notice�Ƿ����
				if(!n.getEnclosures().isEmpty()) {  // �ж��Ƿ��и���
					
					for (Enclosure enclosure : n.getEnclosures()) {  // �����������и����ڴ��������е�������Ϣ
						EnclosureUtil.clearFileOnDisk(
								enclosure.getFileName(), request, Constant.UPLOAD_NOTICEENCLOSURE_URL);
					}
				}
				noticeDao.delete(n);  // ���ڣ�����noticeDao.delete()ɾ�����ݿ��д�Notice��Ϣ
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
	 * ����Notice�����޸����ݿ��з��������ļ�¼
	 * @param notice : Notice
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Notice> param) throws Exception{
		Notice notice = param.getParam();  // ��ȡNotice��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		
		try {
			Notice result = noticeDao.get(notice);  // ����noticeDao.get()���������ݿ��ѯNotice��Ϣ
			
			if(result != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
				noticeDao.updateDyna(notice);  // �������ݿ���Ϣ
				enclosureService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������Notice��
						fileIds, notice.getId(), Constant.NOTICEENCLOSURE_TABLE, "noticeId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "����������ѯ����");  // ����ѯ��ϢΪnull������ 1
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣������ϵ������Ա");  // ����ʧ����Ϣ
		}
	}
	
	/**
	 * �޸�notice��seq���Ƿ��ö���
	 * @param notice : Notice
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/updateSeq")
	@ResponseBody
	public AjaxJsonReturnParam updateState(@RequestBody Notice notice) {
		notice = noticeDao.get(notice);  // ��ѯ���û����Ƿ����
		if(notice != null) {
			if( notice.getSeq() == 0) {  // ʵ��״̬��ת��
				notice.setSeq(1);
			} else if( notice.getSeq() == 1) {
				notice.setSeq(0);
			}
			noticeDao.updateState(notice);  // �����޸ĺ����Ϣ
			return new AjaxJsonReturnParam(0, notice);
		} 
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ����ID��ѯNotice��Ϣ�������������Ϣ
	 * @param notice : Notice
	 * @return
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Notice notice) {
		try {
			// ����noticeDao.get()���������ݿ��ѯNotice��Ϣ
			Notice result = noticeDao.get(notice);  
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
	 * ���ݲ�ѯ���������ͷ�ҳ������̬��ҳ��ѯNotice��Ϣ
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam
	 * @return List<Notice>
	 */
	@RequestMapping(value="/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Notice> listDyna(
			@RequestBody AjaxJsonRequestDynaPageParam<Notice> ajrdpp) {
		// ����ǰ̨��װ��AjaxJsonRequestDynaPageParam.param��Ϣ����ѯ��Ϣ������
		int recordCount = noticeDao.listDyna(ajrdpp.getParam()).size();  
		// �����ѯ��������teacher��Ϣ����
		ajrdpp.getPageModel().setRecordCount(recordCount);  
		// ����adminDao.listDynaPage��̬��ҳ��ѯadmin��Ϣ
		List<Notice> notices = noticeDao.listDynaPage(ajrdpp);  
		// ���ص�ǰҳ�����ݣ��ͷ��ϲ�ѯ��������Ϣ����
		return new AjaxJsonReturnDynaPageParam<Notice>(recordCount, notices);  
	}
	
	/**
	 * ���ݶ�̬����Map��ҳ��ѯNotice��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * ��Ϊ�����ڵĲ�ѯ�����ܹ��ܺõķ�װ��Notice����������Map���д�ֵ
	 * �ѷ�ҳ��Ϣ��װ�룬pageModel
	 * @param params ��̬����
	 * @return ������Ϣ��Project�б�
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Notice> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
		// ��װPageModel��ҳ��ѯ����
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		params.put("startDate_s",   // У�顢������ѯ��ʼʱ���ʱ��
				DateUtil.dateAddStartHMS((String)params.get("startDate_s")));
		params.put("startDate_e",   // У�顢������ѯ��ֹʱ���ʱ��
				DateUtil.dateAddEndHMS((String)params.get("startDate_e")));
		int recordCount = noticeDao.listDynaPageMap(params).size();  // �õ���ѯ��Ϣ������
		pageModel.setRecordCount(recordCount);
		params.put("pageModel", pageModel);  // �����ҳ��Ϣ
		// ����noticeDao.listDynaPageMap��ѯproject��Ϣ
		List<Notice> notices = noticeDao.listDynaPageMap(params);  
		return new AjaxJsonReturnDynaPageParam<Notice>(recordCount, notices);
	}
}
