package cn.nchu.lims.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.MedalDynaSqlProvider;
import cn.nchu.lims.domain.Medal;
import cn.nchu.lims.util.Constant;

@Repository
public interface MedalDao {

	/**
	 * ���ݲ�������Medal�������ݿ����һ��medal��¼
	 * @param medal : Medal
	 */
	@Insert("INSERT INTO "+ Constant.MEDAL_TABLE +"("
			+ "name, photo, teacherId, level, type, "
			+ "grade, ranking, winningTime, sponsor, abstracts ) "
			+ "VALUES(#{name}, #{photo}, #{teacher.id}, #{level}, #{type}, "
			+ "#{grade}, #{ranking}, #{winningTime},#{sponsor}, #{abstracts})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Medal medal);
	
	/**
	 * ����idɾ��Medal��Ϣ
	  * @param medal : Medal
	 */
	@Delete("DELETE FROM " + Constant.MEDAL_TABLE +" WHERE ID = #{id}")
	public void delete(Medal medal);
	
	/**
	 * �����������ļ�¼Medal�����ÿ�
	 * @param photo : String
	 */
	@Update("UPDATE " + Constant.MEDAL_TABLE + " SET photo = null WHERE photo = #{photo}")
	public void updatePhotoSetNull(String photo);
	
	/**
	 * ���ݲ�����Ϣ����̬����Medal��Ϣ
	  * @param medal : Medal
	 */
	@UpdateProvider(type=MedalDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Medal medal);
	
	/**
	 * ����id��ѯMedal��Ϣ
	 * @param medal : Medal
	 * @return Medal
	 */
	@Select("SELECT * FROM " + Constant.MEDAL_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="teacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public Medal get(Medal medal);
	
	/**
	 * ���ݲ�����̬��ҳ��ѯ��ѯProject
	 * @param params Map
	 * @return list ���ݲ�����ѯ��project��Ϣ
	 */
	@SelectProvider(type=MedalDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="teacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public List<Medal> listDynaPageMap(Map<String, Object> params);
}
