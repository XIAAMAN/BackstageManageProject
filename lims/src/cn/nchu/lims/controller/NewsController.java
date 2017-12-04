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
	 * 自动匹配NewsDao接口
	 */
	@Autowired
	private NewsDao newsDao;

	/**
	 * 自动注入WebServic，用于模块化模板代码
	 */
	@Autowired
	private EnclosureService enclosureService;
	
	/**
	 * 根据前台封装的参数向数据库添加News记录
	 * 	 修改附件外键，改为当前注册News编号
	 * @param param :  AjaxJsonRequestSubjectAndFileIdsParam<News>
	 * 	param.param 附件主体类
	 *  param.fileIds 附件编号
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<News> param) {
		
		News news = param.getParam();  // 获取Notice信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		String message = NewsValidator.validator(news);
		
		if(StringUtil.isNullOrEmpty(message)) {  // 检验信息是否符合规范，符合继续，否则返回错误信息
			/** 改造成AOP的注解方式 **/
			
			if (news.getPublishTime() == null) {  // 如果publishTime为null，默认为当前时间
				news.setPublishTime(Calendar.getInstance().getTime());
			}
			
			newsDao.save(news);  // 向数据库插入Notice信息
			enclosureService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的News上
					fileIds, news.getId(), Constant.NEWSENCLOSURE_TABLE, "newsId");
			
			return new AjaxJsonReturnParam(0, news);  // 返回成功信息
		}
		
		return new AjaxJsonReturnParam(1, message);  // 返回失败信息
	}
	
	/**
	 * 根据ID调用NewsDao.delete删除News信息
	 * 清除此News附件的物理路径文件
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody News news, HttpServletRequest request) {
		News n = newsDao.get(news);  // 调用newsDao.get()查询所删除News
		if (n != null) {  
			newsDao.delete(n);  // 存在，调用newsDao.delete()删除数据库中此News信息
			
			if(!n.getEnclosures().isEmpty()) {  // 判断是否有附件
				for (Enclosure enclosure : n.getEnclosures()) {  // 清理主体所有附件在磁盘上所有的物理信息
					EnclosureUtil.clearFileOnDisk(
							enclosure.getFileName(), request, Constant.UPLOAD_NEWSENCLOSURE_URL);
				}
			}
			
			EnclosureUtil.clearFileOnDisk(  // 清除头像物理文件
					n.getPhoto(), request, Constant.UPLOAD_NEWSENCLOSURE_URL);
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "不存在所查询新闻");  // 所查询信息为null，返回 1
	}
	
	/**
	 * 根据News参数修改数据库中符合条件的记录
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<News> param
			, HttpServletRequest request) {
		
		News news = param.getParam();  // 获取Notice信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		News oldnews = newsDao.get(news);  // 调用newsDao.get()方法从数据库查询News信息
		
		if(oldnews != null) {  // 如果查询结果不为null，则返回此查询结果
			/* 
			 * 如果被修改记录photo字段是为不为空或者photo被修改
			 * 删除原物理路径下的头像文件，替换成现在的文件
			 * 修改photo字段信息
			 */
			if (!StringUtil.isNullOrEmpty(oldnews.getPhoto()) 
					&& !oldnews.getPhoto().equals(news.getPhoto())) {  
				EnclosureUtil.clearFileOnDisk( 
						oldnews.getPhoto(), request, Constant.UPLOAD_NEWSENCLOSURE_URL);
			}
			
			newsDao.updateDyna(news);  // 跟新数据库信息
			
			enclosureService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的News上
					fileIds, news.getId(), Constant.NEWSENCLOSURE_TABLE, "newsId");
			
			return new AjaxJsonReturnParam(0, oldnews);
		}
		return new AjaxJsonReturnParam(1, "不存在所查询新闻");  // 所查询信息为null，返回 1
	}
	
	/**
	 * 修改news的seq（是否置顶）
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/updateSeq")
	@ResponseBody
	public AjaxJsonReturnParam updateState(@RequestBody News news) {
		news = newsDao.get(news);  // 查询此用户是是否存在
		if(news != null) {
			if( news.getSeq() == 0) {  // 实现状态的转换
				news.setSeq(1);
			} else if( news.getSeq() == 1) {
				news.setSeq(0);
			}
			newsDao.updateState(news);  // 更新修改后的信息
			return new AjaxJsonReturnParam(0, news);
		} 
		return new AjaxJsonReturnParam(1, "编号错误:此新闻不存在;");
	}
	
	/**
	 * 根究ID查询News信息，并返回相关信息
	 * @param news : News
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody News news) {
		try {
			News result = newsDao.get(news);  // 调用noticeDao.get()方法从数据库查询Notice信息
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询新闻");  // 所查询信息为null，返回 1
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据动态参数Map分页查询Notice信息，没有参数时默认查询所有
	 * 因为有日期的查询，不能够很好的封装进Notice对象，所以用Map进行传值
	 * 把分页信息封装入，pageModel
	 * @param params 动态参数
	 * @return 符合信息的News列表
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<News> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
	
		// 封装查询参数
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		params.put("startDate_s",   // 校验、修正查询起始时间段时间
				DateUtil.dateAddStartHMS((String)params.get("startDate_s")));
		params.put("startDate_e",   // 校验、修正查询终止时间段时间
				DateUtil.dateAddEndHMS((String)params.get("startDate_e")));
		int recordCount = newsDao.listDynaPageMap(params).size();  		// 得到查询信息的总数
		pageModel.setRecordCount(recordCount);
		params.put("pageModel", pageModel);  // 传入分页信息
		// 调用noticeDao.listDynaPageMap查询project信息
		List<News> newss = newsDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<News>(recordCount, newss);
	}

}
