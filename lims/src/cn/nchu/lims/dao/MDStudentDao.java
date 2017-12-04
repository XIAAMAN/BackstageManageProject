package cn.nchu.lims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.MDStudentDynaSqlProvider;
import cn.nchu.lims.domain.MDStudent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;

@Repository
public interface MDStudentDao {
	/**
	 * ���ݲ�������MDStudent�������ݿ����һ��mdStudent��¼
	 * @param mdStudent : MDStudent
	 */
	@Insert("INSERT INTO "+ Constant.MDSTUDENT_TABLE +"("
			+ "name, photo, major, college, researchInterest, "
			+ "email, description, type ) "
			+ "VALUES(#{name}, #{photo}, #{major}, #{college}, #{researchInterest}, "
			+ "#{email}, #{description}, #{type})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(MDStudent mdStudent);
	
	/**
	 * ����idɾ��MDStudent��Ϣ
	  * @param mdStudent : MDStudent
	 */
	@Delete("DELETE FROM " + Constant.MDSTUDENT_TABLE +" WHERE ID = #{id}")
	public void delete(MDStudent mdStudent);
	
	/**
	 * �����������ļ�¼photo�����ÿ�
	 * @param photo : String
	 */
	@Update("UPDATE " + Constant.MDSTUDENT_TABLE + " SET photo = null WHERE photo = #{photo}")
	public void updatePhotoSetNull(String photo);
	
	/**
	 * ���ݲ�����Ϣ����̬����MDStudent��Ϣ
	  * @param mdStudent : MDStudent
	 */
	@UpdateProvider(type=MDStudentDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(MDStudent mdStudent);
	
	/**
	 * ����id��ѯMDStudent��Ϣ
	 * @param mdStudent : MDStudent
	 * @return MDStudent
	 */
	@Select("SELECT * FROM " + Constant.MDSTUDENT_TABLE + " WHERE ID = #{id}")
	public MDStudent get(MDStudent mdStudent);
	
	/**
	 * ��̬��ѯmdStudent��Ϣ,MDStudent����
	 * @param mdStudent : MDStudent
	 * @return List<Teacher>
	 */
	@SelectProvider(type=MDStudentDynaSqlProvider.class, method="listDyna")
	public List<MDStudent> listDyna(MDStudent mdStudent);
	
	/**
	 * ��̬��ҳ��ѯmdStudent��Ϣ,MDStudent����
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam
	 * @return List<MDStudent>
	 */
	@SelectProvider(type=MDStudentDynaSqlProvider.class, method="listDynaPage")
	public List<MDStudent> listDynaPage(AjaxJsonRequestDynaPageParam<MDStudent> ajrdpp);
}
