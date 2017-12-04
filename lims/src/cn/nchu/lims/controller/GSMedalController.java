package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nchu.lims.dao.GSMedalDao;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.domain.GSMedal;
import cn.nchu.lims.service.EnclosureService;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestSubjectAndFileIdsParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.GSMedalValidator;

@Controller
@RequestMapping(value="/gsmedal")
public class GSMedalController {

	/**
	 * 自动匹配PatentDao接口
	 */
	@Autowired
	private GSMedalDao gsMedalDao;
	
	/**
	 * 自动注入WebServic，用于模块化模板代码
	 */
	@Autowired
	private EnclosureService webService;
	
	@Autowired
	private GSMedalValidator gsMedalValidator;
	
	
	/**
	 * 根据前台封装的参数向数据库添加GSMedal记录
	 * 	 修改附件外键，改为当前注册GSMedal编号
	 * @param param :  AjaxJsonRequestSubjectAndFileIdsParam<GSMedal>
	 * 	param.param 附件主体类
	 *  param.fileIds 附件编号
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public AjaxJsonReturnParam add(
			@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<GSMedal> param) {
		GSMedal gsMedal = param.getParam();  // 获取Patent信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		String message = gsMedalValidator.validator(gsMedal);
		if(StringUtil.isNullOrEmpty(message)) {  // 检验信息是否符合规范，符合继续，否则返回错误信息
			gsMedalDao.save(gsMedal);  // 向数据库插入Patent信息
			webService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Patent上
					fileIds, gsMedal.getId(), Constant.GSMEDALENCLOSURE_TABLE, "gsmedalId");
			return new AjaxJsonReturnParam(0, gsMedal);  // 返回成功信息
		}
		return new AjaxJsonReturnParam(1, message);  // 返回失败信息
	}
	
	/**
	 * 根据ID调用GSMedalDao.delete删除News信息
	 * 清除此gsMedal附件的物理路径文件
	 * @param gsMedal : GSMedal
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam delete(@RequestBody GSMedal gsMedal, HttpServletRequest request) {
		GSMedal oldGsMedalp = gsMedalDao.get(gsMedal);  // 调用gsMedasDao.get()查询所删除gsMedal
		if (oldGsMedalp != null) {  
			if(!oldGsMedalp.getEnclosures().isEmpty()) {  // 判断是否有附件
				for (Enclosure enclosure : oldGsMedalp.getEnclosures()) {  // 清理主体所有附件在磁盘上所有的物理信息
					EnclosureUtil.clearFileOnDisk(
							enclosure.getFileName(), request, Constant.UPLOAD_GSMEDALENCLOSURE_URL);
				}
			}
			gsMedalDao.delete(oldGsMedalp);  // 存在，调用gsMedalDao.delete()删除数据库中此GSMedal信息
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "不存在所查询专利");  // 所查询信息为null，返回 1
	}
	
	/**
	 * 根据GSMedal参数修改数据库中符合条件的记录
	 * @param gsMedal : GSMedal
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody AjaxJsonRequestSubjectAndFileIdsParam<GSMedal> param) {
		GSMedal gsMedal = param.getParam();  // 获取gsMedal信息
		List<Integer> fileIds = param.getFileIds();  // 获取fileIds（附件的ID）
		String message = gsMedalValidator.updateValidator(gsMedal);
		if(StringUtil.isNullOrEmpty(message)) {
			GSMedal result = gsMedalDao.get(gsMedal);  // 调用gsMedalDao.get()方法从数据库查询Paten信息
			if(result != null) {  // 如果查询结果不为null，则返回此查询结果
				gsMedalDao.updateDyna(gsMedal);  // 跟新数据库信息
				webService.multipleBindForginKey(  // 修改附件的外键，绑定到当前新增的Patent上
						fileIds, gsMedal.getId(), Constant.GSMEDALENCLOSURE_TABLE, "gsmedalId");
				return new AjaxJsonReturnParam(0, result);
			} else {
				return new AjaxJsonReturnParam(1, "不存在所查询专利");  // 所查询信息为null，返回 1
			}
		}
		return new AjaxJsonReturnParam(1, message);  // 所查询信息为null，返回 1
	}
	
	/**
	 * 根究ID查询Patent信息，并返回相关信息
	 * @param gsMedal : Patent
	 * @return
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody GSMedal gsMedal) {
		try {
			GSMedal result = gsMedalDao.get(gsMedal);  // 调用patentDao.get()方法从数据库查询Patent信息
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
	 * 根据动态参数Map分页查询medal信息，没有参数时默认查询所有
	 * 因为有日期的查询，不能够很好的封装进Medal对象，所以用Map进行传值
	 * 把分页信息封装入，pageModel
	 * @param params 动态参数
	 * @return 符合信息的Project列表
	 */
	@RequestMapping(value="/listDynaPageMap")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<GSMedal> listDynaPageMap(@RequestBody Map<String, Object> params) {
		// 封装查询参数
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// 得到查询信息的总数
		int recordCount =gsMedalDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// 传入分页信息
		params.put("pageModel", pageModel);
		// 调用project.listDynaPageMap查询project信息
		List<GSMedal> gsMedals = gsMedalDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<GSMedal>(recordCount, gsMedals);
	}
}
