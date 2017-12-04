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
	 * ���ݴ����Ĳ���Admin����̬����
	 * @param admin : Admin
	 */
	@InsertProvider(type=AdminDynaSqlProvider.class, method="saveDyna")
	public void saveDyna(Admin admin);
	
	/**
	 * ����idɾ��Admin��Ϣ
	 * @param admin : Admin
	 * @return Admin
	 */
	@Delete("DELETE FROM " + Constant.ADMIN_TABLE + " WHERE ID = #{id}")
	public void delete(Admin admin);
	
	/**
	 * �޸��û���״̬
	 * @param admin : Admin
	 */
	@Update("UPDATE " + Constant.ADMIN_TABLE + " SET state = #{state} WHERE ID = #{id}")
	public void updateState(Admin admin);
	
	/**
	 * ��������
	 */
	@Update("UPDATE " + Constant.ADMIN_TABLE + " SET password = #{password} WHERE id = #{id}")
	public void resetPassword(Admin admin);
	
	/**
	 * ���ݲ�����Admin����̬���� 
	 * @param admin : Admin
	 */
	@UpdateProvider(type=AdminDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Admin admin);
	
	/**
	 * ���ݣɣĲ�ѯAdmin��Ϣ
	* @param admin : Admin
	 * @return admin : Admin
	 */
	@Select("SELECT * FROM " + Constant.ADMIN_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="instituteId", property="instituteId"), 
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public Admin get(Admin admin);

	/**
	 *  ����username��password ��ѯAdmin��Ϣ
	 * @param id : String
	 * @param name : String
	 * @return Admin
	 */
	@Select("SELECT * FROM " + Constant.ADMIN_TABLE + " WHERE username = #{username} AND password = #{password}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public Admin login(@Param("username") String username, @Param("password") String password);
	
	/**
	 * ���ݴ�����admin��Ϣ����̬��ѯAmin��Ϣ
	 * @param admin : Admin
	 * @return List<Admin> �������з�����Ϣ��admin�б�
	 */
	@SelectProvider(type=AdminDynaSqlProvider.class, method="listDyna")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public List<Admin> listDyna(Admin admin);
	
	/**
	 * ���ݴ�����admin��Ϣ����̬��ҳ��ѯAmin��Ϣ
	 * @param rbdpp : AjaxJsonRequestDynaPageParam<Admin>
	 * @return List<Admin> �������з�����Ϣ��admin�б�
	 */
	@SelectProvider(type=AdminDynaSqlProvider.class, method="listDynaPage")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(property="instituteName",column="instituteId",javaType=java.lang.String.class, 
        		one=@One(select="cn.nchu.lims.dao.InstituteDao.getName", 
        		fetchType=FetchType.EAGER))
	})
	public List<Admin> listDynaPage(AjaxJsonRequestDynaPageParam<Admin> rbdpp);
}
