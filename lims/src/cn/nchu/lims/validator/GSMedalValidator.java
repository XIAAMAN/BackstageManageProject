package cn.nchu.lims.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.GSMedal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

@Component
public class GSMedalValidator {
	
	/**
	 * 省略了Service层
	 * 自动注入TeacherDao
	 */
	@Autowired
	private TeacherDao teacherDao;

	public String validator(GSMedal gsMedal) {
		String message = "";
		if (StringUtil.isNullOrEmpty(gsMedal.getName())) {
			message += "名称错误:比赛名称不可为空,";
		}
		if(teacherDao.get(gsMedal.getTeacher()) == null) {  // 检查teacherId 是否存在
			message += "负责人编号错误:负责人编号不存在;";
		}
		if(!StringUtil.inArray(
				gsMedal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "项目等级错误:可选值：（国家级、省部级、市厅级、校级、横向）;";
		}
		if(!StringUtil.inArray(
				gsMedal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "项目等级错误:可选值（特等奖，一等奖，二等奖，三等奖，其他）;";
		}
		if(gsMedal.getWinningTime() == null) {
			message += "获奖时间:获奖时间格式错误;";
		}
		return message;
	}

	public String updateValidator(GSMedal gsMedal) {
		String message = "";
		
		if(teacherDao.get(gsMedal.getTeacher()) == null) {  // 检查teacherId 是否存在
			message += "负责人编号错误:负责人编号不存在;";
		}
		if(!StringUtil.inArrayOrNull(
				gsMedal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "项目等级错误:可选值：（国家级、省部级、市厅级、校级、横向）;";
		}
		if(!StringUtil.inArrayOrNull(
				gsMedal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "项目等级错误:可选值（特等奖，一等奖，二等奖，三等奖，其他）;";
		}
		return message;
	}
}
