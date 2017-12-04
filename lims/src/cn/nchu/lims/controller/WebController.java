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
 * Wab��Ŀȫ�ֿ�����
 * */
@Controller
public class WebController{
	/**
	 * ʡ����Service��
	 * �Զ�ע��AdminDao
	 */
	@Autowired
	private AdminDao adminDao;
	
	/**
	 * ʡ����Service��
	 * �Զ�ע��MenuDao
	 */
	@Autowired
	private MenuDao menuDao;

	/**
	 * ����jspName�Զ���תҳ��
	 * @param jspName
	 * @return jspName
	 */
	@RequestMapping(value="/{jspName}")
	 public String loginForm(@PathVariable String jspName){
		return jspName; // ��̬��תҳ��
	}
	
	/**
	 * �����¼����
	 * @param id : String ��¼��
	 * @param name : String ����
	 * @return String ��ת����ͼ
	 * */
	@RequestMapping(value="/login")
	 public ModelAndView login(@RequestParam("username") String username,
			 @RequestParam("password") String password,
			 HttpSession session, ModelAndView mv){
		try {  // ��password ����MD5����
			password = MD5.EncoderByMd5(password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Admin admin = adminDao.login(username, password);  // ����ҵ���߼�����ж��û��Ƿ���Ե�¼
		if(admin != null){
			Date time= new java.sql.Date(new java.util.Date().getTime());  // ȡ��lastLogin��ǰʱ��
			admin.setLastLogin(time);  // ���޸ĺ��lastLogin ��װ����ǰadmin����
			adminDao.updateDyna(admin);  // ���޸ĺ��admin����������ݿ�
			session.setAttribute(Constant.USER_SESSION, admin);  // ���û����浽HttpSession����
			List<Menu> menus = menuDao.listLevelOne();  // ����menuDao.listLevelOneȡ�����е�Menu��Ϣ
			session.setAttribute(Constant.MENU_SESSION, menus);  // ���û����浽HttpSession����
			mv.setViewName("redirect:/main");  // �ͻ�����ת��mainҳ��
		}else{
			mv.addObject("message", "��¼�����������!����������");  // ���õ�¼ʧ����ʾ��Ϣ
			mv.setViewName("forward:/loginForm");  // �������ڲ���ת����¼ҳ��
		}
		return mv;
	}

}

