package cn.nchu.lims.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Teacher;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnDynaPageParam;
import cn.nchu.lims.util.ajax.AjaxJsonReturnParam;
import cn.nchu.lims.util.lang.EnclosureUtil;
import cn.nchu.lims.util.lang.StringUtil;
import cn.nchu.lims.validator.TeacherValidator;

@Controller
@RequestMapping(value="/teacher")
public class TeacherController {
	
	/**
	 * 省略了Service层
	 * 自动注入TeacherDao
	 */
	@Autowired
	private TeacherDao teacherDao;
	
	/**
	 * 动态插入teacher信息
	 * @param teacher : Teacher
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public AjaxJsonReturnParam add(@RequestBody Teacher teacher) {
		// 调用teacherValidator.validator检验字段是否符合规范
		String message = TeacherValidator.validator(teacher);  
		// 如果没有错误信息，返回状态值 0 （成功）
		if (StringUtil.isNullOrEmpty(message)) {  
			// 调用teacherDao.save插入teacher信息
			teacherDao.saveAndReturnId(teacher); 
			// 插入成功 0,将查询到的teacher传进回调对象information属性中
			return new AjaxJsonReturnParam(0);  
		} 
		//返回失败状态0，错误信息
		return new AjaxJsonReturnParam(1, message);   
	}
	
	/**
	 * 通过id删除teacher信息
	 * @param teacher : Teacher
	 * @return AjaxJsonReturnParam
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public AjaxJsonReturnParam delete(@RequestBody Teacher teacher, HttpServletRequest request) {
		teacher = teacherDao.get(teacher);  // 调用teacherDao.get查询teacher信息
//		if(teacher != null) {
//			teacherDao.delete(teacher);  // 调用teacherDao.deleteById删除teacher信息
//			
//			EnclosureUtil.clearFileOnDisk(  // 清楚物理文件
//					teacher.getPhoto(), request, Constant.UPLOAD_TEACHERENCLOSURE_URL);
//			
//			return new AjaxJsonReturnParam(0);
//		} 
		
		if (teacher != null) {
			teacherDao.deleteState(teacher);
			return new AjaxJsonReturnParam(0);
		}
		return new AjaxJsonReturnParam(1, "教师编号错误:编号不存在");  // 不存在，返回失败状态0，错误信息
	}
	
	/**
	 * 动态更新teacher信息
	 * @param teacher : Teacher
	 * @param AjaxJsonReturnParam
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam update(
			@RequestBody Teacher teacher, HttpServletRequest request) {
		
		Teacher oldTeacher = teacherDao.get(teacher);  // 调用teacherDao.get查询teacher信息
		if(oldTeacher != null) { // teache存在，情况
			// 调用teacherValidator.validator检验字段是否符合规范
			String message = TeacherValidator.updateValidator(teacher);  
			
			if (StringUtil.isNullOrEmpty(message)) {  // 如果没有错误信息，返回状态值 0 （成功）
				/* 
				 * 如果被修改记录photo字段是为不为空或者photo被修改
				 * 删除原物理路径下的头像文件，替换成现在的文件
				 * 修改photo字段信息
				 */
				if (!StringUtil.isNullOrEmpty(oldTeacher.getPhoto()) 
						&& !oldTeacher.getPhoto().equals(teacher.getPhoto())) {  
					EnclosureUtil.clearFileOnDisk( 
							oldTeacher.getPhoto(), request, Constant.UPLOAD_TEACHERENCLOSURE_URL);
				}
				teacherDao.update(teacher);  // 调用teacherDao.update更新teacher信息
				teacher = teacherDao.get(teacher);  // 调用teacherDao.get得到修改后的teacher信息
				return new AjaxJsonReturnParam(0,teacher);  	// 返回成功状态0和teacher信息
			} 
			return new AjaxJsonReturnParam(1, message);
		}
		return new AjaxJsonReturnParam(1, "教师编号错误:编号不存在");  // teacher不存在，返回失败状态0，错误信息
	}
	
	/**
	 * 通过id查询teacher信息
	 * @param teacher : Teacher
	 * @return AjaxJsonReturnParam
	 */
	@RequestMapping(value="/get", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJsonReturnParam get(@RequestBody Teacher teacher) {
		teacher = teacherDao.get(teacher);  // 调用teacherDao.get查询teacher信息
		
		if(teacher != null) { 
			// 存在,返回成功状态0，message和teacher信息
			return new AjaxJsonReturnParam(0, null, teacher);  
		}
		return new AjaxJsonReturnParam(1, "教师编号错误:编号不存在");  // 不存在，返回失败状态0，错误信息
	}
	
	/**
	 * 静态查询，得到所有teacher信息
	 * @return list : List<Teacher> 所有teacher信息
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<Teacher> list() {
		// 调用teacherDao.list方法，得到所有teacher信息
		List<Teacher> teachers = teacherDao.list();  
		return teachers;
	}
	
	/**
	 * 动态查询teacher信息
	 * @param teacher : Teacher
	 * @return 符合动态查询的teacher 列表
	 */
	@RequestMapping("/listDynaPage")
	@ResponseBody
	public AjaxJsonReturnDynaPageParam<Teacher> listDynaPage(
			@RequestBody AjaxJsonRequestDynaPageParam<Teacher> ajrdpp) {
		int recordCount  // 根据前台封装的AjaxJsonRequestDynaPageParam.param信息，查询信息的总数
			= teacherDao.listDyna(ajrdpp.getParam()).size();
		ajrdpp.getPageModel().setRecordCount(recordCount);  // 传入查询到的所有teacher信息总数
		List<Teacher> teachers = teacherDao.listDynaPage(ajrdpp);  // 动态查询teacher信息
		return new AjaxJsonReturnDynaPageParam<Teacher>(recordCount, teachers);
	}
	
}
