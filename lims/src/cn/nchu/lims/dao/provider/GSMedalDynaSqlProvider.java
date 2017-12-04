package cn.nchu.lims.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.nchu.lims.domain.GSMedal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class GSMedalDynaSqlProvider {
	/**
	 * 动态更新mdStudent信息
	 * @param gsMedal : GSMedal
	 * @return sql语句 : String
	 */
	public String updateDyna(final GSMedal gsMedal) {
		return new SQL() {
			{
				UPDATE(Constant.GSMEDAL_TABLE);
				if(!StringUtil.isNullOrEmpty(gsMedal.getName())) {
					SET("name = #{name}");
				}
				if(gsMedal.getTeacher().getId() != 0) {
					SET("teacherid = #{teacher.id}");
				}
				if(!StringUtil.isNullOrEmpty(gsMedal.getLevel())) {
					SET("level = #{level}");
				}
				if(!StringUtil.isNullOrEmpty(gsMedal.getStudentAll())) {
					SET("studentAll = #{studentAll}");
				}
				if(!StringUtil.isNullOrEmpty(gsMedal.getGrade())) {
					SET("grade = #{grade}");
				}
				if(gsMedal.getWinningTime() != null) {
					SET("winningTime = #{winningTime}");
				}
				if(!StringUtil.isNullOrEmpty(gsMedal.getAbstracts())) {
					SET("abstracts = #{abstracts}");
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
	public String listDynaPageMap(final Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(Constant.GSMEDAL_TABLE);
				if(!StringUtil.isNullOrEmpty((String)params.get("name"))) {
					WHERE("Name LIKE CONCAT ('%',#{name},'%')");
				}
				if (!StringUtil.isNullOrEmpty((String)params.get("teacherId"))) {
					WHERE("teacherId = #{teacherId}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("studentAll"))) {
					WHERE("studentAll LIKE CONCAT ('%',#{studentAll},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("level"))) {
					WHERE("level LIKE CONCAT ('%',#{level},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("grade"))) {
					WHERE("grade LIKE CONCAT ('%',#{grade},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(winningTime) between " + params.get("startDate_s") + " AND " + params.get("startDate_e"));
				} else if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(winningTime) >= " + params.get("startDate_s"));
				} else if(StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(winningTime) <= " + params.get("startDate_e"));
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}
}