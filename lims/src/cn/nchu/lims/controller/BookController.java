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
	 * 动态插入Book信息
	 * @param book : Book
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody Book book) {
		try {
			// 调用MedalValidator.validator检验字段是否符合规范
			String message = bookValidator.validator(book);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // 如果没有错误信息，返回状态值 0 （成功）
				// 调用bookDao.save插入mdStudent信息
				bookDao.save(book); 
				// 插入成功 0,将查询到的book传进回调对象information属性中
				return new AjaxJsonReturnParam(0);  
			} 
			return new AjaxJsonReturnParam(1, message);   //返回失败状态1，错误信息
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请与管理员联系 ");   //返回失败状态1，错误信息
		}
	}
	
	/**
	 * 根据ID删除book信息
	* @param book : Book
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam deleteDyan(@RequestBody Book book) {	
		book = bookDao.get(book);  // 查询用户是否存在
		if(book != null) {
			bookDao.delete(book);  // 调用bookDao.delete删除此用户
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 根据前台传递参数更新Book
	 * @param book : Book
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody Book book) {
		// 查询此用户是是否存在
		Book ad = bookDao.get(book);
		if(ad != null) {
			String message = bookValidator.updateValidator(book);  // 待更新参数的验证,并得到错误信息
			if(StringUtil.isNullOrEmpty(message)) {
				bookDao.updateDyna(book);
				ad = bookDao.get(book);  // 得到更新后打admin信息
				return new AjaxJsonReturnParam(0, null, ad);
			} else {
				return new AjaxJsonReturnParam(1, message);
			}
		} 
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 根据Id查询Admin信息
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
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 根据动态参数Map分页查询book信息，没有参数时默认查询所有
	 * 因为有日期的查询，不能够很好的封装进Book对象，所以用Map进行传值
	 * 把分页信息封装入，pageModel
	 * @param params 动态参数
	 * @return 符合信息的book列表
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Book> listDynaPageMap(@RequestBody Map<String, Object> params) {
		// 封装查询参数
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// 得到查询信息的总数
		int recordCount =bookDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// 传入分页信息
		params.put("pageModel", pageModel);
		// 调用project.listDynaPageMap查询project信息
		List<Book> medal = bookDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Book>(recordCount, medal);
	}
}
