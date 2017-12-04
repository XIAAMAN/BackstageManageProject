package cn.nchu.lims.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.NewsDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.News;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.DateUtil;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.NewsValidator;

@Controller
@RequestMapping(value="/news")
public class NewsController {
	
	/**
	 * �Զ�ƥ��NewsDao�ӿ�
	 */
	@Autowired
	private NewsDao newsDao;

	/**
	 * �Զ�ע��WebServic������ģ�黯ģ�����
	 */
	@Autowired
	private EnclosureService enclosureService;
	
	/**
	 * ����ǰ̨��װ�Ĳ��������ݿ����News��¼
	 * 	 �޸ĸ����������Ϊ��ǰע��News���
	 * @param param :  AjaxJsonRequestSubjectAndFileIdsParam<News>
	 * 	param.param ����������
	 *  param.fileIds �������
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<News> param) {
		
		News news = param.getParam();  // ��ȡNotice��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		String message = NewsValidator.validator(news);
		
		if(StringUtil.isNullOrEmpty(message)) {  // ������Ϣ�Ƿ���Ϲ淶�����ϼ��������򷵻ش�����Ϣ
			/** �����AOP��ע�ⷽʽ **/
			
			if (news.getPublishTime() == null) {  // ���publishTimeΪnull��Ĭ��Ϊ��ǰʱ��
				news.setPublishTime(Calendar.getInstance().getTime());
			}
			
			newsDao.save(news);  // �����ݿ����Notice��Ϣ
			enclosureService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������News��
					fileIds, news.getId(), Constant.NEWSENCLOSURE_TABLE, "newsId");
			
			return new AjaxJsonReturnParam(0, news);  // ���سɹ���Ϣ
		}
		
		return new AjaxJsonReturnParam(1, message);  // ����ʧ����Ϣ
	}
	
	/**
	 * ����ID����NewsDao.deleteɾ��News��Ϣ
	 * �����News����������·���ļ�
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody News news, HttpServletRequest request) {
		News n = newsDao.get(news);  // ����newsDao.get()��ѯ��ɾ��News
		if (n != null) {  
			newsDao.delete(n);  // ���ڣ�����newsDao.delete()ɾ�����ݿ��д�News��Ϣ
			
			if(!n.getEnclosures().isEmpty()) {  // �ж��Ƿ��и���
				for (Enclosure enclosure : n.getEnclosures()) {  // �����������и����ڴ��������е�������Ϣ
					EnclosureUtil.clearFileOnDisk(
							enclosure.getFileName(), request, Constant.UPLOAD_NEWSENCLOSURE_URL);
				}
			}
			
			EnclosureUtil.clearFileOnDisk(  // ���ͷ�������ļ�
					n.getPhoto(), request, Constant.UPLOAD_NEWSENCLOSURE_URL);
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "����������ѯ����");  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * ����News�����޸����ݿ��з��������ļ�¼
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<News> param
			, HttpServletRequest request) {
		
		News news = param.getParam();  // ��ȡNotice��Ϣ
		List<Integer> fileIds = param.getFileIds();  // ��ȡfileIds��������ID��
		News oldnews = newsDao.get(news);  // ����newsDao.get()���������ݿ��ѯNews��Ϣ
		
		if(oldnews != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
			/* 
			 * ������޸ļ�¼photo�ֶ���Ϊ��Ϊ�ջ���photo���޸�
			 * ɾ��ԭ����·���µ�ͷ���ļ����滻�����ڵ��ļ�
			 * �޸�photo�ֶ���Ϣ
			 */
			if (!StringUtil.isNullOrEmpty(oldnews.getPhoto()) 
					&& !oldnews.getPhoto().equals(news.getPhoto())) {  
				EnclosureUtil.clearFileOnDisk( 
						oldnews.getPhoto(), request, Constant.UPLOAD_NEWSENCLOSURE_URL);
			}
			
			newsDao.updateDyna(news);  // �������ݿ���Ϣ
			
			enclosureService.multipleBindForginKey(  // �޸ĸ�����������󶨵���ǰ������News��
					fileIds, news.getId(), Constant.NEWSENCLOSURE_TABLE, "newsId");
			
			return new AjaxJsonReturnParam(0, oldnews);
		}
		return new AjaxJsonReturnParam(1, "����������ѯ����");  // ����ѯ��ϢΪnull������ 1
	}
	
	/**
	 * �޸�news��seq���Ƿ��ö���
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/updateSeq")
	@ResponseBody
	public AjaxJsonReturnParam updateState(@RequestBody News news) {
		news = newsDao.get(news);  // ��ѯ���û����Ƿ����
		if(news != null) {
			if( news.getSeq() == 0) {  // ʵ��״̬��ת��
				news.setSeq(1);
			} else if( news.getSeq() == 1) {
				news.setSeq(0);
			}
			newsDao.updateState(news);  // �����޸ĺ����Ϣ
			return new AjaxJsonReturnParam(0, news);
		} 
		return new AjaxJsonReturnParam(1, "��Ŵ���:�����Ų�����;");
	}
	
	/**
	 * ����ID��ѯNews��Ϣ�������������Ϣ
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody News news) {
		try {
			News result = newsDao.get(news);  // ����noticeDao.get()���������ݿ��ѯNotice��Ϣ
			if(result != null) {  // �����ѯ�����Ϊnull���򷵻ش˲�ѯ���
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
	 * ���ݶ�̬����Map��ҳ��ѯNotice��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * ��Ϊ�����ڵĲ�ѯ�����ܹ��ܺõķ�װ��Notice����������Map���д�ֵ
	 * �ѷ�ҳ��Ϣ��װ�룬pageModel
	 * @param params ��̬����
	 * @return ������Ϣ��News�б�
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<News> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
	
		// ��װ��ѯ����
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
		int recordCount = newsDao.listDynaPageMap(params).size();  		// �õ���ѯ��Ϣ������
		pageModel.setRecordCount(recordCount);
		params.put("pageModel", pageModel);  // �����ҳ��Ϣ
		// ����noticeDao.listDynaPageMap��ѯproject��Ϣ
		List<News> newss = newsDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<News>(recordCount, newss);
	}

}
