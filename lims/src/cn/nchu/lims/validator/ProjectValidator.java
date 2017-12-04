package cn.nchu.lims.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Project;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.FloatUtil;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

@Component
public class ProjectValidator {

	/**
	 * 省略了Service层
	 * 自动注入TeacherDao
	 */
	@Autowired
	private TeacherDao teacherDao;
	
	/**
	 * 对将要封装成Project 的字段参数进行校验
	 * @param params : Project
	 * @return message : String
	 */
	public String validator(Project project) {
		String message = "";
		if(StringUtil.isNullOrEmpty(project.getName().trim())) {
			message += "项目名称错误:项目名称不可为空;";
		}
			
		if(teacherDao.get(project.getTeacher()) == null) {  // 检查teacherId 是否存在
			message += "负责人编号错误:负责人编号不存在;";
		}
		if(StringUtil.isNullOrEmpty(project.getMember().trim())) {
			message += "项目成员错误:项目成员不可以为空;";
		}
		if(FloatUtil.isNullOrZero(project.getFund())) {
			message += "项目经费:项目经费为浮点数;";
		}
		if(StringUtil.isNullOrEmpty(project.getSource().trim())) {
			message += "项目来源错误:项目来源不可为空;";
		}
		if(!StringUtil.inArray(
				project.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "项目等级错误:可选值(国家级、省部级、市厅级、校级、横向);";
		}
		if(project.getStartDate() == null) {
			message += "项目起始时间:项目起始时间不可为空;";
		}
		
		return message;
	}
	
	/**
	 * 更新Project时的参数校验
	 * @param project ： Project
	 * @return message : String
	 */
	public String updateValidator(Project project) {
		String message = "";
		if(!IntegerUtil.isNullOrZero(project.getTeacher().getId())) {
			if(teacherDao.get(project.getTeacher()) == null) {
				message += "负责人编号错误:负责人编号不存在;";
			}
		}
		if(!StringUtil.inArrayOrNull(
				project.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "项目等级错误:可选值(国家级、省部级、市厅级、校级、横向);";
		}
		return message;
	}
	
}
