package cn.nchu.lims.dao.provider;

import java.util.Calendar;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import cn.nchu.lims.domain.Project;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

public class ProjectDynaSqlProvider {

	/**
	 * 根据参数动态更新project信息
	 * @param project : Project
	 * @return sql语句 : String
	 */
	public String updateDyna(final Project project) {
		return new SQL() {
			{
				UPDATE(Constant.PROJECT_TABLE);
				if(!StringUtil.isNullOrEmpty(project.getName())) {
					SET("Name = #{name}");
				}
				if(project.getTeacher().getId() != 0) {
					SET("TeacherId = #{teacher.id}");
				}
				if(!StringUtil.isNullOrEmpty(project.getMember())) {
					SET("Member = #{member}");
				}
				if(!StringUtil.isNullOrEmpty(project.getCode())) {
					SET("Code = #{code}");
				}
				if(project.getFund() != 0) {
					SET("Fund = #{fund}");
				}
				if(!StringUtil.isNullOrEmpty(project.getSource())) {
					SET("Source = #{source}");
				}
				if(!StringUtil.isNullOrEmpty(project.getLevel())) {
					SET("Level = #{level}");
				}
				if(project.getStartDate() != null) {
					SET("StartDate = #{startDate}");
				}
				if(project.getEndDate() != null) {
					SET("EndDate = #{endDate}");
				}
				if(!StringUtil.isNullOrEmpty(project.getDescription())) {
					SET("Description = #{description}");
				}
				SET("ID = #{id}");
				WHERE("ID = #{id}");
			}
		}.toString();
	}
	
	/**
	 * 根据参数动态查询
	 * @param project : Project
	 * @return sql语句 : String
	 */
	public String listDyna(final Project project) {
		return new SQL() {
			{
				SELECT("*");
				FROM(Constant.PROJECT_TABLE);
				if(!StringUtil.isNullOrEmpty(project.getName())) {
					WHERE("Name LIKE CONCAT ('%',#{name},'%')");
				}
				if(project.getTeacher() != null) {
					if (!IntegerUtil.isNullOrZero(project.getTeacher().getId())) {
						WHERE("TeacherId = #{teacher.id}");
					}
				}
				if(!StringUtil.isNullOrEmpty(project.getMember())) {
					WHERE("Member LIKE CONCAT ('%',#{member},'%')");
				}
				if(!StringUtil.isNullOrEmpty(project.getCode())) {
					WHERE("Code LIKE CONCAT ('%',#{code},'%')");
				}
				
				if(project.getFund() != 0) {
					WHERE("Fund = #{fund}");
				}
				if(!StringUtil.isNullOrEmpty(project.getSource())) {
					WHERE("Source LIKE CONCAT ('%',#{source},'%')");
				}
				if(!StringUtil.isNullOrEmpty(project.getLevel())) {
					WHERE("Level = #{level}");
				}
				if(project.getStartDate() != null) {
					// 将Date 转换成 Calendar， 便于取年月日
				    Calendar cal=Calendar.getInstance();  
				    cal.setTime(project.getStartDate());  
				    String p_year = String.valueOf(cal.get(Calendar.YEAR));
					WHERE("year(StartDate) = " + p_year);
				}
			}
		}.toString();
	}
	
	/**
	 * 根据参数动态查询
	 * @param project : Project
	 * @return sql语句 : String
	 */
	public String listDynaPageMap(final Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(Constant.PROJECT_TABLE);
				if(!StringUtil.isNullOrEmpty((String)params.get("name"))) {
					WHERE("Name LIKE CONCAT ('%',#{name},'%')");
				}
				if (!StringUtil.isNullOrEmpty((String)params.get("teacherId"))) {
					WHERE("TeacherId = #{teacherId}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("member"))) {
					WHERE("Member LIKE CONCAT ('%',#{member},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("code"))) {
					WHERE("Code LIKE CONCAT ('%',#{code},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("source"))) {
					WHERE("Source LIKE CONCAT ('%',#{source},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("level"))) {
					WHERE("Level = #{level}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(StartDate) between " + params.get("startDate_s") + " AND " + params.get("startDate_e"));
				} else if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(StartDate) >= " + params.get("startDate_s"));
				} else if(StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(StartDate) <= " + params.get("startDate_e"));
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}
		
}
