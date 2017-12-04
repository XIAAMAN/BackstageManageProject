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
	 * 自动注入PaperDao，用于Notice的数据库访问
	 */
	@Autowired
	private PaperDao paperDao;
	
	/**
	 * 自动注入WebServic，用于模块化模板代码
	 */
	@Autowired
	private EnclosureService webService;
	
	/**
	 * 根据前台的参数，向据库中插入一条paper信息
	 *   并根据fileIds参数，将paper的主键绑定到本次上传的附件的外键上
	 * @param param : AjaxJsonRequestSubjectAndFileIdsParam
	 * @return AjaxJsonReturnParam
	 * @throws Exception
	 */
	@Transactional  // 事务注解
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Paper> param) 
					throws Exception{
		
		try {
			Paper paper= param.getParam();  // 获取Notice信息
			List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
			String message = PaperValidator.validator(paper);
			
			if(StringUtil.isNullOrEmpty(message)) {  // 检验信息是否符合规范，符合继续，否则返回错误信息
				paperDao.save(paper);  // 向数据库插入Notice信息
				webService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Paper上
						fileIds, paper.getId(), Constant.PAPERENCLOSURE_TABLE, "paperId");
				return new AjaxJsonReturnParam(0, paper);  // 返回成功信息
			} else {
				return new AjaxJsonReturnParam(1, message);  // 返回失败信息
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据paper.id 删除数据库中paper记录
	 *   并清理附件表和附件物理路径的实体文件
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
			
			if (p != null) {  // 调用paperDao.get()查询所删除Paper是否存在
				if(!p.getEnclosures().isEmpty()) {  // 判断是否有附件
					for (Enclosure enclosure : p.getEnclosures()) {  // 清理主体所有附件在磁盘上所有的物理信息
						
						EnclosureUtil.clearFileOnDisk(
								enclosure.getFileName()
								, request, Constant.UPLOAD_PAPERENCLOSURE_URL);
					}
				}
				
				paperDao.delete(p);  // 存在，调用noticeDao.delete()删除数据库中此Notice信息
				return new AjaxJsonReturnParam(0);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询论文");  // 所查询信息为null，返回 1
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据前台传递的参数，通过id动态更新Paper信息
	 *   并修跟新相关的附件信息
	 * @param param : AjaxJsonRequestSubjectAndFileIdsParam
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Paper> param) {
		
		Paper paper = param.getParam();  // 获取Paper信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		String message = PaperValidator.updateValidator(paper);  // 信息验证
		
		if(StringUtil.isNullOrEmpty(message)) {
			Paper result = paperDao.get(paper);  // 调用paperDao.get()方法从数据库查询Paper信息
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				paperDao.updateDyna(paper);  // 跟新数据库信息
				webService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Paper上
						fileIds, paper.getId(), Constant.PAPERENCLOSURE_TABLE, "paperId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询论文");  // 所查询信息为null，返回 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // 所查询信息为null，返回 1
	}
	
	/**
	 * 根究ID查询Paper信息，并返回相关信息
	 * @param paper : Paper
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Paper paper) {
		try {
			
			Paper result = paperDao.get(paper);  // 调用paperDao.get()方法从数据库查询Paper信息
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询论文");  // 所查询信息为null，返回 1
			}
		} catch(Exception e) {
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据动态参数Map分页查询Paper信息，没有参数时默认查询所有
	 * 因为有日期的查询，不能够很好的封装进Paper对象，所以用Map进行传值
	 * 把分页信息封装入，pageModel
	 * @param params : Map 动态参数
	 * @return 符合信息的Paper列表
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Paper> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
		
		PageModel pageModel = new PageModel();  // 封装查询参数
		
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
		
		int recordCount = paperDao.listDynaPageMap(params).size();  // 得到查询信息的总数
		pageModel.setRecordCount(recordCount);
		params.put("pageModel", pageModel);  // 传入分页信息
		
		// 调用paperDao.listDynaPageMap查询project信息
		List<Paper> projects = paperDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Paper>(recordCount, projects);
	}
}
