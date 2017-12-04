package cn.nchu.lims.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import cn.nchu.lims.domain.Medal;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class MedalDynaSqlProvider {

	/**
	 * 动态更新mdStudent信息
	 * @param medal : Medal
	 * @return sql语句 : String
	 */
	public String updateDyna(final Medal medal) {
		return new SQL() {
			{
				UPDATE(Constant.MEDAL_TABLE);
				if(!StringUtil.isNullOrEmpty(medal.getName())) {
					SET("name = #{name}");
				}
				if(!StringUtil.isNullOrEmpty(medal.getPhoto())) {
					SET("photo = #{photo}");
				}
				if(medal.getTeacher().getId() != 0) {
					SET("TeacherId = #{teacher.id}");
				}
				if(!StringUtil.isNullOrEmpty(medal.getLevel())) {
					SET("level = #{level}");
				}
				if(!StringUtil.isNullOrEmpty(medal.getType())) {
					SET("type = #{type}");
				}
				if(!StringUtil.isNullOrEmpty(medal.getGrade())) {
					SET("grade = #{grade}");
				}
				if(medal.getRanking() >= 1) {
					SET("ranking = #{ranking}");
				}
				if("0".equals(medal.getSponsor()) || "1".equals(medal.getSponsor())) {
					SET("sponsor = #{sponsor}");
				}
				if(medal.getWinningTime() != null) {
					SET("winningTime = #{winningTime}");
				}
				if(!StringUtil.isNullOrEmpty(medal.getAbstracts())) {
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
				FROM(Constant.MEDAL_TABLE);
				if(!StringUtil.isNullOrEmpty((String)params.get("name"))) {
					WHERE("Name LIKE CONCAT ('%',#{name},'%')");
				}
				if (!StringUtil.isNullOrEmpty((String)params.get("teacherId"))) {
					WHERE("TeacherId = #{teacherId}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("type"))) {
					WHERE("type LIKE CONCAT ('%',#{type},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("level"))) {
					WHERE("level LIKE CONCAT ('%',#{level},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("grade"))) {
					WHERE("grade LIKE CONCAT ('%',#{grade},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("sponsor"))) {
					WHERE("sponsor = #{sponsor}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("ranking"))) {
					WHERE("ranking = #{ranking}");
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
