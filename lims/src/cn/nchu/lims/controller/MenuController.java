package cn.nchu.lims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.nchu.lims.dao.MenuDao;

@Controller
@RequestMapping(value="/menu")
public class MenuController {

	/**
	 * ʡ����Service��
	 * �Զ�ע��MenuDao
	 */
	@SuppressWarnings("unused")
	@Autowired
	private MenuDao menuDao;
	
}
