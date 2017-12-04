package cn.nchu.lims.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.lang.StringUtil;

public class AdminDynaSqlProvider {

	/**
	 * 动态插入Admin信息
	 * @param admin : Admin
	 * @return sql语句 : String
	 */
	public String saveDyna(final Admin admin) {
		return new SQL(){
			{
				INSERT_INTO(Constant.ADMIN_TABLE);
				if(!StringUtil.isNullOrEmpty(admin.getUserName())) {
					VALUES("username", "#{userName}");
				}				
				if(!StringUtil.isNullOrEmpty(admin.getNickName())) {
					VALUES("nickname", "#{nickName}");
				}				
				if(admin.getInstituteId() != 0) {
					VALUES("instituteId", "#{instituteId}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getPassword())) {
					VALUES("password", "#{password}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getPhone())) {
					VALUES("phone", "#{phone}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getEmail())) {
					VALUES("email", "#{email}");
				}
				if(admin.getLastLogin() != null) {
					VALUES("lastlogin", "#{lastLogin}");
				}
			}
		}.toString();
	}
	
	/**
	 * 动态更新Admin信息
	 * @param admin : Admin
	 * @return sql语句 : String
	 */
	public String updateDyna(final Admin admin) {
		return new SQL() {
			{
				UPDATE(Constant.ADMIN_TABLE);			
				if(!StringUtil.isNullOrEmpty(admin.getUserName())) {
					SET("username = #{userName}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getNickName())) {
					SET("nickname = #{nickName}");
				}
				if(admin.getInstituteId() != 0) {
					SET("instituteId = #{instituteId}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getPassword())) {
					SET("password = #{password}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getPhone())) {
					SET("phone = #{phone}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getEmail())) {
					SET("email = #{email}");
				}
				if(admin.getLastLogin() != null) {
					SET("lastlogin = #{lastLogin}");
				}
				SET("ID = #{id}");
				WHERE("ID = #{id}");
			}
		}.toString();
	}
	
	/**
	 * 动态查询Admin信息
	 * sate 字段的查询的查询
	 * @param admin : Admin
	 * @return sql语句 : String
	 */
	public String listDyna(final Admin admin) {
		return new SQL(){
			{
				SELECT("id, username, nickname, instituteId, phone, email, lastlogin, state");
				FROM(Constant.ADMIN_TABLE);
				if(admin.getId() !=0) {
					WHERE("ID = #{id}");
				}
				if(!StringUtil.isNullOrEmpty(admin.getUserName())) {
					WHERE("username LIKE CONCAT ('%',#{userName},'%')");
				}			
				if(!StringUtil.isNullOrEmpty(admin.getNickName())) {
					WHERE("nickname LIKE CONCAT ('%',#{nickName},'%')");
				}			
				if(admin.getInstituteId() != 0) {
					WHERE("instituteId = #{instituteId}");
				}				
				if(!StringUtil.isNullOrEmpty(admin.getPassword())) {
					WHERE("password = #{password}");
				}			
				if(!StringUtil.isNullOrEmpty(admin.getPhone())) {
					WHERE("phone LIKE CONCAT ('%',#{phone},'%')");
				}				
				if(!StringUtil.isNullOrEmpty(admin.getEmail())) {
					WHERE("email LIKE CONCAT ('%',#{email},'%')");
				}				
				if(admin.getLastLogin() != null) {
					WHERE("lastlogin = #{lastLogin}");
				}			
			}
		}.toString();
	}
	
	/**
	 * 动态分页查询Admin信息
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam 包含属性Amin、pageModel
	 *   Admin 包含查询信息
	 *   PageModel 包含分页信息
	 * @return sql语句 : String
	 */
	public String listDynaPage(final AjaxJsonRequestDynaPageParam<Admin> ajrdpp) {
		String sql = new SQL(){
			{
				SELECT("id, username, nickname, instituteId, phone, email, lastlogin, state");
				FROM(Constant.ADMIN_TABLE);
				Admin admin = ajrdpp.getParam();  //得到封装的查询参数
				if(admin != null) {
					if(admin.getId() !=0) {
						WHERE("ID = #{param.id}");
					}
					if(!StringUtil.isNullOrEmpty(admin.getUserName())) {
						WHERE("username LIKE CONCAT ('%',#{param.userName},'%')");
					}					
					if(!StringUtil.isNullOrEmpty(admin.getNickName())) {
						WHERE("nickname LIKE CONCAT ('%',#{param.nickName},'%')");
					}					
					if(admin.getInstituteId() != 0) {
						WHERE("instituteId = #{param.instituteId}");
					}				
					if(!StringUtil.isNullOrEmpty(admin.getPassword())) {
						WHERE("password = #{param.password}");
					}				
					if(!StringUtil.isNullOrEmpty(admin.getPhone())) {
						WHERE("phone LIKE CONCAT ('%',#{param.phone},'%')");
					}					
					if(!StringUtil.isNullOrEmpty(admin.getEmail())) {
						WHERE("email LIKE CONCAT ('%',#{param.email},'%')");
					}
					if(admin.getLastLogin() != null) {
						WHERE("lastlogin = #{param.lastLogin}");
					}
				}
			}
		}.toString();
				
		PageModel pageModel = ajrdpp.getPageModel();
		if(pageModel != null) {
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}";
		}
		
		return sql;
	}
	
}
