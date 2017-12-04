package cn.nchu.lims.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.MDStudentDao;
import cn.nchu.lims.domain.MDStudent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.MDStudentValidator;

@Controller
@RequestMapping(value="/mdstudent")
public class MDStudentController {

	/**
	 * 自动注入MDStudentDao，用于Notice的数据库访问
	 */
	@Autowired
	private MDStudentDao mdStudentDao;
	
	/**
	 * 动态插入MDStudent信息
	 * @param mdStudent : MDStudetn
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody MDStudent mdStudent) {
		try {
			// 调用mdStudentValidator.validator检验字段是否符合规范
			String message = MDStudentValidator.validator(mdStudent);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // 如果没有错误信息，返回状态值 0 （成功）
				// 调用mdStudentDao.save插入mdStudent信息
				mdStudentDao.save(mdStudent); 
				// 插入成功 0,将查询到的teacher传进回调对象information属性中
				return new AjaxJsonReturnParam(0);  
			} 
			return new AjaxJsonReturnParam(1, message);   //返回失败状态1，错误信息
		} catch(Exception e) {
			e.printStackTrace();
			return new AjaxJsonReturnParam(1, "请求异常，请与管理员联系 ");   //返回失败状态1，错误信息
		}
	}
	
	/**
	 * 通过id删除MDStudent信息
	 * @param mdStudent : MDStudent
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public AjaxJsonReturnParam delete(
			@RequestBody MDStudent mdStudent, HttpServletRequest request) {
		
		mdStudent = mdStudentDao.get(mdStudent);  // 调用mdStudentDao.get查询mdStudent信息
		
		if(mdStudent != null) {// 存在,返回成功状态0
			mdStudentDao.delete(mdStudent);  // 调用mdStudentDao.delete删除mdStudent信息
			
			EnclosureUtil.clearFileOnDisk(  // 清楚物理路径下的头像文件
					mdStudent.getPhoto(), request, Constant.UPLOAD_MDSTUDENTENCLOSURE_URL);
			
			return new AjaxJsonReturnParam(0);
		} 
		return new AjaxJsonReturnParam(1, "学生编号错误:编号不存在");  // 不存在，返回失败状态0，错误信息
	}
	
	/**
	 * 动态更新teacher信息
	 * @param mdStudent : MDStudent
	 * @param AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody MDStudent mdStudent, HttpServletRequest request) {
		
		MDStudent student = mdStudentDao.get(mdStudent);  // 调用mdStudentDao.get查询mdStudent信息
		
		if(student != null) { // mdStudent存在，情况
			// 调用mdStudentValidator.validator检验字段是否符合规范
			String message = MDStudentValidator.updateValidator(mdStudent);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // 如果没有错误信息，返回状态值 0 （成功）
				/* 
				 * 如果被修改记录photo字段是为不为空或者photo被修改
				 * 删除原物理路径下的头像文件，替换成现在的文件
				 * 修改photo字段信息
				 */
				if (!StringUtil.isNullOrEmpty(student.getPhoto()) 
						&& !student.getPhoto().equals(mdStudent.getPhoto())) {  
					EnclosureUtil.clearFileOnDisk( 
							student.getPhoto(), request, Constant.UPLOAD_MDSTUDENTENCLOSURE_URL);
				}
				mdStudentDao.updateDyna(mdStudent);  // 调用mdStudentDao.update更新mdStudent信息
				mdStudent = mdStudentDao.get(mdStudent);  // 调用mdStudentDao.get得到修改后的mdStudent信息
				return new AjaxJsonReturnParam(0, mdStudent);  	// 返回成功状态0和mdStudent信息
			} 
			return new AjaxJsonReturnParam(1, message);
		}
		return new AjaxJsonReturnParam(1, "学生编号错误:编号不存在");  // mdStudent不存在，返回失败状态0，错误信息
	}
	
	/**
	 * 根据Id查询MDStudent信息
	 * @param mdStudent : MDStudent
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody MDStudent mdStudent) {
		mdStudent = mdStudentDao.get(mdStudent);
		if(mdStudent != null) {
			return new AjaxJsonReturnParam(0, mdStudent);
		}
		return new AjaxJsonReturnParam(1, "编号错误:此用户不存在;");
	}
	
	/**
	 * 动态查询MDStudent信息
	 * @param teacher : MDStudent
	 * @return 符合动态查询的MDStudent 列表
	 */
	@RequestMapping("/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<MDStudent> listDynaPage(
			@RequestBody AjaxJsonRequestDynaPageParam<MDStudent> ajrdpp) {
		
		int recordCount  // 根据前台封装的AjaxJsonRequestDynaPageParam.param信息，查询信息的总数
			= mdStudentDao.listDyna(ajrdpp.getParam()).size();
		ajrdpp.getPageModel().setRecordCount(recordCount);  // 传入查询到的所有MDStudent信息总数
		List<MDStudent> teachers = mdStudentDao.listDynaPage(ajrdpp);  // 动态查询MDStudent信息
		return new AjaxJsonReturnDynaPageParam<MDStudent>(recordCount, teachers);
	}
	
}
