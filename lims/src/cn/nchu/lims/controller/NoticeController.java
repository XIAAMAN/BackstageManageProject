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
	 * 自动注入NoticeDao，用于Notice的数据库访问
	 */
	@Autowired
	private NoticeDao noticeDao;
	
	/**
	 * 自动注入WebServic，用于模块化模板代码
	 */
	@Autowired
	private EnclosureService enclosureService;
	
	/**
	 * 根据参数向数据库保存Notice信息
	 * 根据前台传递的fileIds附件ID列表，向NoticeEnclosure更新外键noticeId
	 * @param notice
	 * @return
	 */
	@Transactional  // 事务注解
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Notice> param) throws Exception{
		try {
			Notice notice = param.getParam();  // 获取Notice信息
			List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
			String message = NoticeValidator.validator(notice);
			if(StringUtil.isNullOrEmpty(message)) {  // 检验信息是否符合规范，符合继续，否则返回错误信息
				/** 改造成AOP的注解方式 **/
				if (notice.getPublishTime() == null) {  // 如果publishTime为null，默认为当前时间
					notice.setPublishTime(Calendar.getInstance().getTime());
				}
				noticeDao.save(notice);  // 向数据库插入Notice信息
				enclosureService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Notice上
						fileIds, notice.getId(), Constant.NOTICEENCLOSURE_TABLE, "noticeId");
				return new AjaxJsonReturnParam(0, notice);  // 返回成功信息
			} else {
				return new AjaxJsonReturnParam(1, message);  // 返回失败信息
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据ID调用NoticeDao.delete删除Notice信息
	 * 清除此Notice附件的物理路径文件
	 * @param n : Notice
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody Notice notice, HttpServletRequest request) {
		try {
			Notice n = noticeDao.get(notice);
			if (noticeDao.get(n) != null) {  // 调用noticeDao.get()查询所删除Notice是否存在
				if(!n.getEnclosures().isEmpty()) {  // 判断是否有附件
					
					for (Enclosure enclosure : n.getEnclosures()) {  // 清理主体所有附件在磁盘上所有的物理信息
						EnclosureUtil.clearFileOnDisk(
								enclosure.getFileName(), request, Constant.UPLOAD_NOTICEENCLOSURE_URL);
					}
				}
				noticeDao.delete(n);  // 存在，调用noticeDao.delete()删除数据库中此Notice信息
				return new AjaxJsonReturnParam(0);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询公告");  // 所查询信息为null，返回 1
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据Notice参数修改数据库中符合条件的记录
	 * @param notice : Notice
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Notice> param) throws Exception{
		Notice notice = param.getParam();  // 获取Notice信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		
		try {
			Notice result = noticeDao.get(notice);  // 调用noticeDao.get()方法从数据库查询Notice信息
			
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				noticeDao.updateDyna(notice);  // 跟新数据库信息
				enclosureService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Notice上
						fileIds, notice.getId(), Constant.NOTICEENCLOSURE_TABLE, "noticeId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询公告");  // 所查询信息为null，返回 1
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 修改notice的seq（是否置顶）
	 * @param notice : Notice
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/updateSeq")
	@ResponseBody
	public AjaxJsonReturnParam updateState(@RequestBody Notice notice) {
		notice = noticeDao.get(notice);  // 查询此用户是是否存在
		if(notice != null) {
			if( notice.getSeq() == 0) {  // 实现状态的转换
				notice.setSeq(1);
			} else if( notice.getSeq() == 1) {
				notice.setSeq(0);
			}
			noticeDao.updateState(notice);  // 更新修改后的信息
			return new AjaxJsonReturnParam(0, notice);
		} 
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 根究ID查询Notice信息，并返回相关信息
	 * @param notice : Notice
	 * @return
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Notice notice) {
		try {
			// 调用noticeDao.get()方法从数据库查询Notice信息
			Notice result = noticeDao.get(notice);  
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询公告");  // 所查询信息为null，返回 1
			}
		} catch(Exception e) {
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据查询条件参数和分页条件动态分页查询Notice信息
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam
	 * @return List<Notice>
	 */
	@RequestMapping(value="/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Notice> listDyna(
			@RequestBody AjaxJsonRequestDynaPageParam<Notice> ajrdpp) {
		// 根据前台封装的AjaxJsonRequestDynaPageParam.param信息，查询信息的总数
		int recordCount = noticeDao.listDyna(ajrdpp.getParam()).size();  
		// 传入查询到的所有teacher信息总数
		ajrdpp.getPageModel().setRecordCount(recordCount);  
		// 调用adminDao.listDynaPage动态分页查询admin信息
		List<Notice> notices = noticeDao.listDynaPage(ajrdpp);  
		// 返回当前页的数据，和符合查询条件的信息总数
		return new AjaxJsonReturnDynaPageParam<Notice>(recordCount, notices);  
	}
	
	/**
	 * 根据动态参数Map分页查询Notice信息，没有参数时默认查询所有
	 * 因为有日期的查询，不能够很好的封装进Notice对象，所以用Map进行传值
	 * 把分页信息封装入，pageModel
	 * @param params 动态参数
	 * @return 符合信息的Project列表
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Notice> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
		// 封装PageModel分页查询参数
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
		int recordCount = noticeDao.listDynaPageMap(params).size();  // 得到查询信息的总数
		pageModel.setRecordCount(recordCount);
		params.put("pageModel", pageModel);  // 传入分页信息
		// 调用noticeDao.listDynaPageMap查询project信息
		List<Notice> notices = noticeDao.listDynaPageMap(params);  
		return new AjaxJsonReturnDynaPageParam<Notice>(recordCount, notices);
	}
}
