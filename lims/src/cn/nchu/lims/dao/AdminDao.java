package cn.nchu.lims.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.AdminDynaSqlProvider;
import cn.nchu.lims.domain.Admin;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;

@Repository
public interface AdminDao {
	
	String useCache = null;

	/**
	 * 根据传来的参数Admin，动态插入
	 * @param admin : Admin
	 */
	@InsertProvider(type=AdminDynaSqlProvider.class, method="saveDyna")
	public void saveDyna(Admin admin);
	
	/**
	 * 根据id删除Admin信息
	 * @param admin : Admin
	 * @return Admin
	 */
	@Delete("DELETE FROM " + Constant.ADMIN_TABLE + " WHERE ID = #{id}")
	public void delete(Admin admin);
	
	/**
	 * 修改用户的状态
	 * @param admin : Admin
	 */
	@Update("UPDATE " + Constant.ADMIN_TABLE + " SET state = #{state} WHERE ID = #{id}")
	public void updateState(Admin admin);
	
	/**
	 * 重置密码
	 */
	@Update("UPDATE " + Constant.ADMIN_TABLE + " SET password = #{password} WHERE id = #{id}")
	public void resetPassword(Admin admin);
	
	/**
	 * 根据参数的Admin，动态跟新 
	 * @param admin : Admin
	 */
	@UpdateProvider(type=AdminDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Admin admin);
	
	/**
	 * 根据ＩＤ查询Admin信息
	* @param admin : Admin
	 * @return admin : Admin
	 */
	@Select("SELECT * FROM " + Constant.ADMIN_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="instituteId", property="instituteId"), 
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public Admin get(Admin admin);

	/**
	 *  根究username和password 查询Admin信息
	 * @param id : String
	 * @param name : String
	 * @return Admin
	 */
	@Select("SELECT * FROM " + Constant.ADMIN_TABLE + " WHERE username = #{username} AND password = #{password}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public Admin login(@Param("username") String username, @Param("password") String password);
	
	/**
	 * 根据传来的admin信息，动态查询Amin信息
	 * @param admin : Admin
	 * @return List<Admin> 返回所有符合信息的admin列表
	 */
	@SelectProvider(type=AdminDynaSqlProvider.class, method="listDyna")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public List<Admin> listDyna(Admin admin);
	
	/**
	 * 根据传来的admin信息，动态分页查询Amin信息
	 * @param rbdpp : AjaxJsonRequestDynaPageParam<Admin>
	 * @return List<Admin> 返回所有符合信息的admin列表
	 */
	@SelectProvider(type=AdminDynaSqlProvider.class, method="listDynaPage")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public List<Admin> listDynaPage(AjaxJsonRequestDynaPageParam<Admin> rbdpp);
}
