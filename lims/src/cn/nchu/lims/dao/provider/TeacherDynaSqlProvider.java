package cn.nchu.lims.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import cn.nchu.lims.domain.Teacher;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.lang.StringUtil;

public class TeacherDynaSqlProvider {

	/**
	 * 动态插入teacher信息
	 * 通过Teacher类的属性值参数插入
	 * @param teacher : Teacher
	 * @return sql语句
	 */
	public String save(final Teacher teacher) {
		return new SQL() {
			{
				INSERT_INTO(Constant.TEACHER_TABLE);
				if(teacher.getName() != null) {
					VALUES("Name", "#{name}");
				}
				if(teacher.getMajor() != null) {
					VALUES("Major", "#{major}");
				}
				if(teacher.getCollege() != null) {
					VALUES("College", "#{college}");
				}
				if(teacher.getGraduateCollege() != null) {
					VALUES("GraduateCollege", "#{graduateCollege}");
				}
				if(teacher.getResearchInterest() != null) {
					VALUES("ResearchInterest", "#{researchInterest}");
				}
				if(teacher.getTitle() != null) {
					VALUES("Title", "#{title}");
				}
				if(teacher.getPosition() != null) {
					VALUES("Position", "#{position}");
				}
				if(teacher.getDirector() != null) {
					VALUES("Director", "#{director}");
				}
				if(teacher.getEmail() != null) {
					VALUES("Email", "#{email}");
				}
				if(teacher.getDescription() != null) {
					VALUES("Description", "#{description}");
				}
			}
		}.toString();
	}
	
	/**
	 * 动态更新teacher信息
	 * @param teacher : Teacher
	 * @return sql语句 : String
	 */
	public String update(final Teacher teacher) {
		return new SQL() {
			{
				UPDATE(Constant.TEACHER_TABLE);
				if(!StringUtil.isNullOrEmpty(teacher.getName())) {
					SET("name = #{name}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getPhoto())) {
					SET("photo = #{photo}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getMajor())) {
					SET("major = #{major}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getCollege())) {
					SET("college = #{college}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getGraduateCollege())) {
					SET("graduateCollege = #{graduateCollege}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getResearchInterest())) {
					SET("researchInterest = #{researchInterest}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getTitle())) {
					SET("title = #{title}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getPosition())) {
					SET("position = #{position}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getDirector())) {
					SET("director = #{director}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getEmail())) {
					SET("email = #{email}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getDescription())) {
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
	 * @param teacher : Teacher
	 * @return sql语句 : String
	 */
	public String listDyna(final Teacher teacher) {
		return new SQL() {
			{
				SELECT("*");
				FROM(Constant.TEACHER_TABLE);
				if(!StringUtil.isNullOrEmpty(teacher.getName())) {
					WHERE("Name LIKE CONCAT ('%',#{name},'%')");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getMajor())) {
					WHERE("Major LIKE CONCAT ('%',#{major},'%')");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getCollege())) {
					WHERE("College LIKE CONCAT ('%',#{college},'%')");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getGraduateCollege())) {
					WHERE("GraduateCollege LIKE CONCAT ('%',#{graduateCollege},'%')");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getResearchInterest())) {
					WHERE("ResearchInterest LIKE CONCAT ('%',#{researchInterest},'%')");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getTitle())) {
					WHERE("Title = #{title}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getPosition())) {
					WHERE("Position LIKE CONCAT ('%',#{position},'%')");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getDirector())) {
					WHERE("Director = #{director}");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getEmail())) {
					WHERE("Email LIKE CONCAT ('%',#{email},'%')");
				}
				if(!StringUtil.isNullOrEmpty(teacher.getDescription())) {
					WHERE("Description LIKE \"%\"#{description}\"%\"");
				}
			}
		}.toString();
	}
	
	/**
	 * 分页动态查询，Map传参，里面包含Teacher和PageModel查询信息
	 * @param teacher : Teacher
	 * @return sql语句 : String
	 */
	public String listDynaPage(final AjaxJsonRequestDynaPageParam<Teacher> ajrdpp) {
		String sql =  new SQL() {
			{
				SELECT("*");
				FROM(Constant.TEACHER_TABLE);
				Teacher teacher = ajrdpp.getParam();
				if(teacher != null) {
					if(!StringUtil.isNullOrEmpty(teacher.getName())) {
						WHERE("Name LIKE CONCAT ('%',#{param.name},'%')");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getMajor())) {
						WHERE("Major LIKE CONCAT ('%',#{param.major},'%')");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getCollege())) {
						WHERE("College LIKE CONCAT ('%',#{param.college},'%')");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getGraduateCollege())) {
						WHERE("GraduateCollege LIKE CONCAT ('%',#{param.graduateCollege},'%')");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getResearchInterest())) {
						WHERE("ResearchInterest LIKE CONCAT ('%',#{param.researchInterest},'%')");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getTitle())) {
						WHERE("Title = #{param.title}");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getPosition())) {
						WHERE("Position LIKE CONCAT ('%',#{param.position},'%')");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getDirector())) {
						WHERE("Director = #{param.director}");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getEmail())) {
						WHERE("Email LIKE CONCAT ('%',#{param.email},'%')");
					}
					if(!StringUtil.isNullOrEmpty(teacher.getDescription())) {
						WHERE("Description LIKE \"%\"#{teacher.description}\"%\"");
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
