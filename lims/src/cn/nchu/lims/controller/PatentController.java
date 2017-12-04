package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.PatentDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.Patent;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.PatentValidator;

@Controller
@RequestMapping(value="/patent")
public class PatentController {
	
	/**
	 * 自动匹配PatentDao接口
	 */
	@Autowired
	private PatentDao patentDao;
	
	/**
	 * 自动注入WebServic，用于模块化模板代码
	 */
	@Autowired
	private EnclosureService webService;
	
	/**
	 * 根据前台封装的参数向数据库添加Paten记录
	 * 	 修改附件外键，改为当前注册Patent编号
	 * @param param :  AjaxJsonRequestSubjectAndFileIdsParam<Patent>
	 * 	param.param 附件主体类
	 *  param.fileIds 附件编号
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Patent> param) {
		Patent patent = param.getParam();  // 获取Patent信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		String message = PatentValidator.validator(patent);
		if(StringUtil.isNullOrEmpty(message)) {  // 检验信息是否符合规范，符合继续，否则返回错误信息
			patentDao.save(patent);  // 向数据库插入Patent信息
			webService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Patent上
					fileIds, patent.getId(), Constant.PATENTENCLOSURE_TABLE, "patentId");
			return new AjaxJsonReturnParam(0, patent);  // 返回成功信息
		}
		return new AjaxJsonReturnParam(1, message);  // 返回失败信息
	}
	
	/**
	 * 根据ID调用PatentDao.delete删除News信息
	 * 清除此Patent附件的物理路径文件
	 * @param patent : Patent
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody Patent patent, HttpServletRequest request) {
		Patent p = patentDao.get(patent);  // 调用patentsDao.get()查询所删除Patent
		if (p != null) {  
			if(!p.getEnclosures().isEmpty()) {  // 判断是否有附件
				for (Enclosure enclosure : p.getEnclosures()) {  // 清理主体所有附件在磁盘上所有的物理信息
					EnclosureUtil.clearFileOnDisk(
							enclosure.getFileName(), request, Constant.UPLOAD_PATENTENCLOSURE_URL);
				}
			}
			patentDao.delete(p);  // 存在，调用patentDao.delete()删除数据库中此Patent信息
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "不存在所查询专利");  // 所查询信息为null，返回 1
	}
	
	/**
	 * 根据Patent参数修改数据库中符合条件的记录
	 * @param patent : Patent
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<Patent> param) {
		Patent patent = param.getParam();  // 获取Patent信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		String message = PatentValidator.updateValidator(patent);
		if(StringUtil.isNullOrEmpty(message)) {
			Patent result = patentDao.get(patent);  // 调用patenDao.get()方法从数据库查询Paten信息
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				patentDao.updateDyna(patent);  // 跟新数据库信息
				webService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Patent上
						fileIds, patent.getId(), Constant.PATENTENCLOSURE_TABLE, "patentId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询专利");  // 所查询信息为null，返回 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // 所查询信息为null，返回 1
	}
	
	/**
	 * 根究ID查询Patent信息，并返回相关信息
	 * @param patent : Patent
	 * @return
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Patent patent) {
		try {
			Patent result = patentDao.get(patent);  // 调用patentDao.get()方法从数据库查询Patent信息
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询新闻");  // 所查询信息为null，返回 1
			}
		} catch(Exception e) {
			return new AjaxJsonReturnParam(1, "请求异常，请联系管理人员");  // 返回失败信息
		}
	}
	
	/**
	 * 根据动态参数Map分页查询Patent信息，没有参数时默认查询所有
	 * 因为有日期的查询，不能够很好的封装进Patent对象，所以用Map进行传值
	 * 把分页信息封装入，pageModel
	 * @param params 动态参数
	 * @return 符合信息的News列表
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Patent> listDynaPageMap(
			@RequestBody Map<String, Object> params) {
		
		// 封装查询参数
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// 得到查询信息的总数
		int recordCount = patentDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// 传入分页信息
		params.put("pageModel", pageModel);
		// 调用project.listDynaPageMap查询project信息
		List<Patent> projects = patentDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Patent>(recordCount, projects);
	}

}
