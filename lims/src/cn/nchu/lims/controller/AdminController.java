package cn.nchu.lims.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.AdminDao;
import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.util.MD5;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.AdminValidator;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	/**
	 * 省略了Service层
	 * 自动注入AdminDao
	 */
	@Autowired
	private AdminDao adminDao;
	
	/**
	 * 增加用户，根据前台参数向数据库中添加用户信息
	* @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam saveDyan(@RequestBody Admin admin) {
		String message = AdminValidator.validator(admin);  // 校验参数，并返回错误信息
		if(StringUtil.isNullOrEmpty(message)) {  // 如果没有错误信息，向数据库中添加用户信息，并返回成功信息
			try {  // 调用MD5,进行password加密
				admin.setPassword(MD5.EncoderByMd5(admin.getPassword()));
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			adminDao.saveDyna(admin); //调用adminDao.saveDyan将数据存入数据库
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, message);
	}
	
	/**
	 * 根据ID删除admin信息
	* @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxJsonReturnParam deleteDyan(@RequestBody Admin admin) {	
		admin = adminDao.get(admin);  // 查询用户是否存在
		if(admin != null) {
			adminDao.delete(admin);  // 调用adminDao.delete删除此用户
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 修改admin的state（状态）
	 * @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/updateState")
	@ResponseBody
	public AjaxJsonReturnParam updateState(@RequestBody Admin admin) {
		admin = adminDao.get(admin);  // 查询此用户是是否存在
		if(admin != null) {
			if( admin.getState() == 0) {  // 实现状态的转换
				admin.setState(1);
			} else if( admin.getState() == 1) {
				admin.setState(0);
			}
			adminDao.updateState(admin);  // 更新修改后的信息
			return new AjaxJsonReturnParam(0, admin);
		} 
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping(value="/resetPassword")
	@ResponseBody
	public AjaxJsonReturnParam resetPassword(@RequestBody Admin admin) {
		if(adminDao.get(admin) != null) {
			try {
				admin.setPassword(MD5.EncoderByMd5("1234567809"));
				adminDao.resetPassword(admin);
				return new AjaxJsonReturnParam(0, "密码重置成功");
			} catch (Exception e) {
				return new AjaxJsonReturnParam(1, "操作异常");
			}
		}
		return new AjaxJsonReturnParam(1, "用户不存在");
	}
	
	/**
	 * 根据前台传递参数更新admin
	 * @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public AjaxJsonReturnParam update(@RequestBody Admin admin) {
		// 查询此用户是是否存在
		Admin ad = adminDao.get(admin);
		if(ad != null) {
			String message = AdminValidator.upateValidator(admin);  // 待更新参数的验证,并得到错误信息
			if(StringUtil.isNullOrEmpty(message)) {
				adminDao.updateDyna(admin);
				ad = adminDao.get(admin);  // 得到更新后打admin信息
				return new AjaxJsonReturnParam(0, null, ad);
			} else {
				return new AjaxJsonReturnParam(1, message);
			}
		} 
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 根据Id查询Admin信息
	 * @param admin : Admin
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Admin admin) {
		admin = adminDao.get(admin);
		if(admin != null) {
			return new AjaxJsonReturnParam(0, admin);
		}
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 根据参数动态查询Admin信息
	 * @param admin : admin
	 * @return admins : List<Admin>
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public List<Admin> listDyna(@RequestBody Admin admin) {
		List<Admin> admins = adminDao.listDyna(admin);
		return admins;
	}
	
	/**
	 * 根据参数动态分页查询
	 * @param adpp : AjaxJsonRequestDynaPageParam<Admin> 包含Admin 和 PageModel
	 *   Admin 封装查询条件参数， PageModel 封装分页参数 
	 * @return AjaxJsonReturnDynaPageParam<Admin>
	 */
	@RequestMapping(value="/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Admin> listDynaPage(
			@RequestBody AjaxJsonRequestDynaPageParam<Admin> ajrdpp) {
		int recordCount = adminDao.listDyna(ajrdpp.getParam()).size();   // 根据前台封装的AjaxJsonRequestDynaPageParam.param信息，查询信息的总数
		ajrdpp.getPageModel().setRecordCount(recordCount);  // 传入查询到的所有teacher信息总数
		List<Admin> admins = adminDao.listDynaPage(ajrdpp);  // 调用adminDao.listDynaPage动态分页查询admin信息
		return new AjaxJsonReturnDynaPageParam<Admin>(recordCount, admins);  // 返回当前页的数据，和符合查询条件的信息总数
	}
	
}
