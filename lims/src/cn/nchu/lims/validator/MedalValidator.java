package cn.nchu.lims.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.nchu.lims.dao.TeacherDao;
import cn.nchu.lims.domain.Medal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

@Component
public class MedalValidator {
	
	@Autowired
	private TeacherDao teacherDao;
	
	/**
	 * 对将要封装成Medal 的字段参数进行校验
	 * @param medal : Medal
	 * @return message : String
	 */
	public String validator(Medal medal) {
		String message = "";
		if(StringUtil.isNullOrEmpty(medal.getName())) {
			message += "项目名称错误:项目名称不可为空;";
		}
		if(teacherDao.get(medal.getTeacher()) == null) {  // 检查teacherId 是否存在
			message += "教师编号错误:获奖教师不存在;";
		}
		if(!StringUtil.inArray(
				medal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "项目等级错误:可选值：（国家级、省部级、市厅级、校级、横向）;";
		}
		if(!StringUtil.inArray(
				medal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "项目等级错误:可选值（特等奖，一等奖，二等奖，三等奖，其他）;";
		}
		if(!StringUtil.inArray(
				medal.getType(), Constant.MEDAL_TYPE)) {
			message += "项目等级错误:可选值（教学成果奖，科研成果奖，其他奖项）;";
		}
		if(IntegerUtil.isNullOrZero(medal.getRanking())) {
			message += "排名错误:排名应大于等于1;";
		}
		if(medal.getWinningTime() == null) {
			message += "获奖时间:获奖时间不可为空;";
		}
		if(!("0".equals(medal.getSponsor()) || "1".equals(medal.getSponsor()))) {
			message += "第一单位错误:参数不可为空（为0或1）;";
		}
		return message;
	}
	
	/**
	 * 更新Project时的参数校验
	 * @param medal ： Medal
	 * @return message : String
	 */
	public String updateValidator(Medal medal) {
		String message = "";
		if(!StringUtil.inArrayOrNull(
				medal.getLevel(), Constant.PROJECT_LEVEL)) {
			message += "项目等级错误:可选值：（国家级、省部级、市厅级、校级、横向）;";
		}
		if(!StringUtil.inArrayOrNull(
				medal.getGrade(), Constant.MEDAL_GRADE)) {
			message += "项目等级错误:可选值（特等奖，一等奖，二等奖，三等奖，其他）;";
		}
		if(!StringUtil.inArrayOrNull(
				medal.getType(), Constant.MEDAL_TYPE)) {
			message += "项目等级错误:可选值（教学成果奖，科研成果奖，其他奖项）;";
		}
		if(!StringUtil.isNullOrEmpty(medal.getSponsor()) && (
				!("0".equals(medal.getSponsor()) || "1".equals(medal.getSponsor())))) {
			message += "第一单位错误:参数不可为空（为0或1）;";
		}
		return message;
	}
	
}
