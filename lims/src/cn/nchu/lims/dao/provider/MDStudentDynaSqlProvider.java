package cn.nchu.lims.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import cn.nchu.lims.domain.MDStudent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.lang.StringUtil;

public class MDStudentDynaSqlProvider {

	/**
	 * 动态更新mdStudent信息
	 * @param mdStudent : MDStudent
	 * @return sql语句 : String
	 */
	public String updateDyna(final MDStudent mdStudent) {
		return new SQL() {
			{
				UPDATE(Constant.MDSTUDENT_TABLE);
				if(!StringUtil.isNullOrEmpty(mdStudent.getName())) {
					SET("name = #{name}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getPhoto())) {
					SET("photo = #{photo}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getMajor())) {
					SET("major = #{major}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getType())) {
					SET("type = #{type}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getCollege())) {
					SET("college = #{college}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getResearchInterest())) {
					SET("researchInterest = #{researchInterest}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getEmail())) {
					SET("email = #{email}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getDescription())) {
					SET("description = #{description}");
				}
				SET("ID = #{id}");
				WHERE("ID = #{id}");
			}
		}.toString();
	}
	
	/**
	 * 动态查询teacher信息
	 * 通过Teacher类的属性值参数查询
	 * @param mdStudent : MDStudent
	 * @return sql语句 : String
	 */
	public String listDyna(final MDStudent mdStudent) {
		return new SQL() {
			{
				SELECT("*");
				FROM(Constant.MDSTUDENT_TABLE);
				if(!StringUtil.isNullOrEmpty(mdStudent.getName())) {
					WHERE("Name LIKE CONCAT ('%',#{name},'%')");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getMajor())) {
					WHERE("Major LIKE CONCAT ('%',#{major},'%')");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getCollege())) {
					WHERE("College LIKE CONCAT ('%',#{college},'%')");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getType())) {
					WHERE("type = #{type}");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getResearchInterest())) {
					WHERE("ResearchInterest LIKE CONCAT ('%',#{researchInterest},'%')");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getEmail())) {
					WHERE("Email LIKE CONCAT ('%',#{email},'%')");
				}
				if(!StringUtil.isNullOrEmpty(mdStudent.getDescription())) {
					WHERE("Description LIKE \"%\"#{description}\"%\"");
				}
			}
		}.toString();
	}
	
	/**
	 * @param mdStudent : MDStudent
	 * @return sql语句 : String
	 */
	public String listDynaPage(final AjaxJsonRequestDynaPageParam<MDStudent> ajrdpp) {
		String sql =  new SQL() {
			{
				SELECT("*");
				FROM(Constant.MDSTUDENT_TABLE);
				MDStudent mdStudent = ajrdpp.getParam();
				if(mdStudent != null) {
					if(!StringUtil.isNullOrEmpty(mdStudent.getName())) {
						WHERE("Name LIKE CONCAT ('%',#{param.name},'%')");
					}
					if(!StringUtil.isNullOrEmpty(mdStudent.getMajor())) {
						WHERE("Major LIKE CONCAT ('%',#{param.major},'%')");
					}
					if(!StringUtil.isNullOrEmpty(mdStudent.getCollege())) {
						WHERE("College LIKE CONCAT ('%',#{param.college},'%')");
					}
					if(!StringUtil.isNullOrEmpty(mdStudent.getType())) {
						WHERE("type = #{param.type}");
					}
					if(!StringUtil.isNullOrEmpty(mdStudent.getResearchInterest())) {
						WHERE("ResearchInterest LIKE CONCAT ('%',#{param.researchInterest},'%')");
					}
					if(!StringUtil.isNullOrEmpty(mdStudent.getEmail())) {
						WHERE("Email LIKE CONCAT ('%',#{param.email},'%')");
					}
					if(!StringUtil.isNullOrEmpty(mdStudent.getDescription())) {
						WHERE("Description LIKE \"%\"#{param.description}\"%\"");
					}
				}
			}
		}.toString();
		
		PageModel pageModel = ajrdpp.getPageModel();
		if(pageModel != null){
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		
		return sql;
	}
}
