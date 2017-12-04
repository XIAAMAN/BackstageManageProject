package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nchu.lims.dao.BookDao;
import cn.nchu.lims.domain.Book;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.BookValidator;


@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookDao bookDao;
	
	@Autowired
	BookValidator bookValidator;
	/**
	 * ��̬����Book��Ϣ
	 * @param book : Book
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody Book book) {
		try {
			// ����MedalValidator.validator�����ֶ��Ƿ���Ϲ淶
			String message = bookValidator.validator(book);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // ���û�д�����Ϣ������״ֵ̬ 0 ���ɹ���
				// ����bookDao.save����mdStudent��Ϣ
				bookDao.save(book); 
				// ����ɹ� 0,����ѯ����book�����ص�����information������
				return new AjaxJsonReturnParam(0);  
			} 
			return new AjaxJsonReturnParam(1, message);   //����ʧ��״̬1��������Ϣ
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "�����쳣���������Ա��ϵ ");   //����ʧ��״̬1��������Ϣ
		}
	}
	
	/**
	 * ����IDɾ��book��Ϣ
	* @param book : Book
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam deleteDyan(@RequestBody Book book) {	
		book = bookDao.get(book);  // ��ѯ�û��Ƿ����
		if(book != null) {
			bookDao.delete(book);  // ����bookDao.deleteɾ�����û�
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ����ǰ̨���ݲ�������Book
	 * @param book : Book
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody Book book) {
		// ��ѯ���û����Ƿ����
		Book ad = bookDao.get(book);
		if(ad != null) {
			String message = bookValidator.updateValidator(book);  // �����²�������֤,���õ�������Ϣ
			if(StringUtil.isNullOrEmpty(message)) {
				bookDao.updateDyna(book);
				ad = bookDao.get(book);  // �õ����º��admin��Ϣ
				return new AjaxJsonReturnParam(0, null, ad);
			} else {
				return new AjaxJsonReturnParam(1, message);
			}
		} 
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ����Id��ѯAdmin��Ϣ
	 * @param book : Book
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Book book) {
		book = bookDao.get(book);
		if(book != null) {
			return new AjaxJsonReturnParam(0, book);
		}
		return new AjaxJsonReturnParam(1, "��Ŵ���:���û�������;");
	}
	
	/**
	 * ���ݶ�̬����Map��ҳ��ѯbook��Ϣ��û�в���ʱĬ�ϲ�ѯ����
	 * ��Ϊ�����ڵĲ�ѯ�����ܹ��ܺõķ�װ��Book����������Map���д�ֵ
	 * �ѷ�ҳ��Ϣ��װ�룬pageModel
	 * @param params ��̬����
	 * @return ������Ϣ��book�б�
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Book> listDynaPageMap(@RequestBody Map<String, Object> params) {
		// ��װ��ѯ����
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// �õ���ѯ��Ϣ������
		int recordCount =bookDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// �����ҳ��Ϣ
		params.put("pageModel", pageModel);
		// ����project.listDynaPageMap��ѯproject��Ϣ
		List<Book> medal = bookDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Book>(recordCount, medal);
	}
}
