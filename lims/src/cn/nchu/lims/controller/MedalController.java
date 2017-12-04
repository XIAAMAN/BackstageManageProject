package cn.nchu.lims.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.MedalDao;
import cn.nchu.lims.domain.Medal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.MedalValidator;

@Controller
@RequestMapping(value="/medal")
public class MedalController {

	@Autowired
	MedalDao medalDao;
	
	@Autowired
	MedalValidator medalValidator;
	/**
	 * 动态插入Medal信息
	 * @param medal : Medal
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody Medal medal) {
		try {
			// 调用MedalValidator.validator检验字段是否符合规范
			String message = medalValidator.validator(medal);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // 如果没有错误信息，返回状态值 0 （成功）
				// 调用medalDao.save插入mdStudent信息
				medalDao.save(medal); 
				// 插入成功 0,将查询到的medal传进回调对象information属性中
				return new AjaxJsonReturnParam(0);  
			} 
			return new AjaxJsonReturnParam(1, message);   //返回失败状态1，错误信息
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请与管理员联系 ");   //返回失败状态1，错误信息
		}
	}
	
	/**
	 * 通过id删除Medal信息
	 * @param medal : Medal
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public AjaxJsonReturnParam delete(
			@RequestBody Medal medal, HttpServletRequest request) {
		
		medal = medalDao.get(medal);  // 调用medalDao.get查询medal信息
		
		if(medal != null) {// 存在,返回成功状态0
			medalDao.delete(medal);  // 调用medalDao.delete删除medal信息
			
			EnclosureUtil.clearFileOnDisk(  // 清楚物理路径下的头像文件
					medal.getPhoto(), request, Constant.UPLOAD_MEDALENCLOSURE_URL);
			
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "学生编号错误:编号不存在");  // 不存在，返回失败状态0，错误信息
	}
	
	/**
	 * 动态更新teacher信息
	 * @param medal : Medal
	 * @param AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody Medal medal, HttpServletRequest request) {
		
		Medal oldMedal = medalDao.get(medal);  // 调用medalDao.get查询mdStudent信息
		try {
			if(oldMedal != null) { // medal存在，情况
				// 调用medalValidator.validator检验字段是否符合规范
				String message = medalValidator.updateValidator(medal);  
				
				if (StringUtil.isNullOrEmpty(message)) {  // 如果没有错误信息，返回状态值 0 （成功）
					/* 
					 * 如果被修改记录photo字段是为不为空或者photo被修改
					 * 删除原物理路径下的头像文件，替换成现在的文件
					 * 修改photo字段信息
					 */
					if (!StringUtil.isNullOrEmpty(oldMedal.getPhoto()) 
							&& !oldMedal.getPhoto().equals(medal.getPhoto())) {  
						EnclosureUtil.clearFileOnDisk( 
								oldMedal.getPhoto(), request, Constant.UPLOAD_MEDALENCLOSURE_URL);
					}
					medalDao.updateDyna(medal);  // 调用medalDao.update更新mdStudent信息
					medal = medalDao.get(medal);  // 调用medalDao.get得到修改后的mdStudent信息
					return new AjaxJsonReturnParam(0, medal);  	// 返回成功状态0和mdStudent信息
				} 
				return new AjaxJsonReturnParam(1, message);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new AjaxJsonReturnParam(1, "编号错误:编号不存在");  // medal不存在，返回失败状态0，错误信息
	}
	
	/**
	 * 根据Id查询Medal信息
	 * @param medal : Medal
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Medal medal) {
		medal = medalDao.get(medal);
		if(medal != null) {
			return new AjaxJsonReturnParam(0, medal);
		}
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
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
	public AjaxJsonReturnDynaPageParam<Medal> listDynaPageMap(@RequestBody Map<String, Object> params) {
		// 封装查询参数
		PageModel pageModel = new PageModel();
		if (IntegerUtil.isDigit((String)params.get("pageIndex")) 
				&& IntegerUtil.isDigit((String)params.get("pageSize"))) {
			pageModel.setPageIndex(Integer.valueOf((String) params.get("pageIndex")));
			pageModel.setPageSize(Integer.valueOf((String) params.get("pageSize")));
		}
		// 得到查询信息的总数
		int recordCount =medalDao.listDynaPageMap(params).size();
		pageModel.setRecordCount(recordCount);
		// 传入分页信息
		params.put("pageModel", pageModel);
		// 调用project.listDynaPageMap查询project信息
		List<Medal> medal = medalDao.listDynaPageMap(params);
		return new AjaxJsonReturnDynaPageParam<Medal>(recordCount, medal);
	}
}
