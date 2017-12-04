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
	 * 省略了Service层
	 * 自动注入ProjectDao
	 */
	@Autowired
	private ProjectDao projectDao;
	
	/**
	 * 自动注入WebServic，用于模块化模板代码
	 */
	@Autowired
	private EnclosureService enclosureService;
	
	@Autowired
	private ProjectValidator projectValidator;
	
	/**
	 * 根据前台的参数，向据库中插入一条Project信息
	 *   并根据fileIds参数，将project的主键绑定到本次上传的附件的外键上
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
			Project project= param.getParam();  // 获取Notice信息
			List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
			String message = projectValidator.validator(project);
			
			if(StringUtil.isNullOrEmpty(message)) {  // 检验信息是否符合规范，符合继续，否则返回错误信息
				projectDao.save(project);  // 向数据库插入Notice信息
				enclosureService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Paper上
						fileIds, project.getId(), Constant.PROJECTENCLOSURE_TABLE, "projectId");
				return new AjaxJsonReturnParam(0, project);  // 返回成功信息
			} else {
				return new AjaxJsonReturnParam(1, message);  // 返回失败信息
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据project.id 删除数据库中project记录
	 *   并清理附件表和附件物理路径的实体文件
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
			
			if(!p.getEnclosures().isEmpty()) {  // 判断是否有附件
				for (Enclosure enclosure : p.getEnclosures()) {  // 清理主体所有附件在磁盘上所有的物理信息
					
					EnclosureUtil.clearFileOnDisk(  
							enclosure.getFileName()
							, request, Constant.UPLOAD_PROJECTENCLOSURE_URL);
				}
			}
			
			projectDao.delete(p);  // 存在，调用projectDao.delete()删除数据库中此project信息
			return new AjaxJsonReturnParam(0);
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}

	/**
	 * 根据前台传递的参数，通过id动态更新Project信息
	 *   并修跟新相关的附件信息
	 * @param param : AjaxJsonRequestSubjectAndFileIdsParam
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Project> param) {
		
		Project project = param.getParam();  // 获取Project信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		// 调用projectValidator.validator检验字段是否符合规范
		String message = projectValidator.updateValidator(project);  
		
		if(StringUtil.isNullOrEmpty(message)) {
			
			Project result = projectDao.get(project);  // 调用projectDao.get()方法从数据库查询Project信息
			
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				projectDao.updateDyna(project);  // 跟新数据库信息
				enclosureService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Project上
						fileIds, project.getId(), Constant.PROJECTENCLOSURE_TABLE, "projectId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询项目");  // 所查询信息为null，返回 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // 所查询信息为null，返回 1
	}
	
	/**
	 * 根据id 查询Project 信息
	 * @param project : Project
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Project project) {
		project = projectDao.get(project);  // 调用projectDao.get查询project信息
		if(project != null) { 
			return new AjaxJsonReturnParam(0, project);
		}
		return new AjaxJsonReturnParam(1, "项目编号错误:编号不存在");  // 不存在，返回失败状态0，错误信息
	} 
	
	/**
	 * 根据动态参数查询Project信息，没有参数时默认查询所有
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
	 * 根据动态参数Map分页查询project信息，没有参数时默认查询所有
	 * 因为有日期的查询，不能够很好的封装进Projet对象，所以用Map进行传值
	 * 把分页信息封装入，pageModel
	 * @param params 动态参数
	 * @return 符合信息的Project列表
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Project> listDynaPageMap(@RequestBody Map<String, Object> params) {
		
		// 封装查询参数
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		
		// 得到查询信息的总数
		int recordCount = projectDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// 传入分页信息
		params.put("pageModel", pageModel);
		// 调用project.listDynaPageMap查询project信息
		List<Project> projects = projectDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Project>(recordCount, projects);
	}
}
