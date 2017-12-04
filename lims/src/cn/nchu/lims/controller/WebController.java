package cn.nchu.lims.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.nchu.lims.dao.AdminDao;
import cn.nchu.lims.dao.MenuDao;
import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.domain.Menu;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.MD5;

/**
 * Wab项目全局控制器
 * */
@Controller
public class WebController{
	/**
	 * 省略了Service层
	 * 自动注入AdminDao
	 */
	@Autowired
	private AdminDao adminDao;
	
	/**
	 * 省略了Service层
	 * 自动注入MenuDao
	 */
	@Autowired
	private MenuDao menuDao;

	/**
	 * 根据jspName自动跳转页面
	 * @param jspName
	 * @return jspName
	 */
	@RequestMapping(value="/{jspName}")
	 public String loginForm(@PathVariable String jspName){
		return jspName; // 动态跳转页面
	}
	
	/**
	 * 处理登录请求
	 * @param id : String 登录名
	 * @param name : String 密码
	 * @return String 跳转的视图
	 * */
	@RequestMapping(value="/login")
	 public ModelAndView login(@RequestParam("username") String username,
			 @RequestParam("password") String password,
			 HttpSession session, ModelAndView mv){
		try {  // 将password 进行MD5加密
			password = MD5.EncoderByMd5(password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Admin admin = adminDao.login(username, password);  // 调用业务逻辑组件判断用户是否可以登录
		if(admin != null){
			Date time= new java.sql.Date(new java.util.Date().getTime());  // 取得lastLogin当前时间
			admin.setLastLogin(time);  // 将修改后的lastLogin 封装到当前admin对象
			adminDao.updateDyna(admin);  // 将修改后的admin对象存入数据库
			session.setAttribute(Constant.USER_SESSION, admin);  // 将用户保存到HttpSession当中
			List<Menu> menus = menuDao.listLevelOne();  // 调用menuDao.listLevelOne取得所有的Menu信息
			session.setAttribute(Constant.MENU_SESSION, menus);  // 将用户保存到HttpSession当中
			mv.setViewName("redirect:/main");  // 客户端跳转到main页面
		}else{
			mv.addObject("message", "登录名或密码错误!请重新输入");  // 设置登录失败提示信息
			mv.setViewName("forward:/loginForm");  // 服务器内部跳转到登录页面
		}
		return mv;
	}

}

