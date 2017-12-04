package cn.nchu.lims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nchu.lims.dao.InstituteDao;
import cn.nchu.lims.domain.Institute;

@Controller
@RequestMapping(value="/institute")
public class InstituteController {
	
	@Autowired
	private InstituteDao instituteDao;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public List<Institute> list() {
		return instituteDao.list();
	}

}
