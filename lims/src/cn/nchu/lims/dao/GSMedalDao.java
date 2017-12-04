package cn.nchu.lims.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.GSMedalDynaSqlProvider;
import cn.nchu.lims.domain.GSMedal;
import cn.nchu.lims.util.Constant;

@Repository
public interface GSMedalDao {

	/**
	 * ����GSMedal�еĲ�������T_GSMedal���в���һ����¼
	 * @param patent : GSMedal
	 */
	@Insert("INSERT INTO "+ Constant.GSMEDAL_TABLE +"("
			+ "name, teacherId, studentAll, level, grade, winningTime, abstracts) "
			+ "VALUES(#{name}, #{teacher.id} ,#{studentAll}, #{level}, "
			+ "#{grade}, #{winningTime}, #{abstracts})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(GSMedal gsmedal);
	
	/**
	 * ����ID ɾ��gsmedal��Ϣ
	 * @param gsmedal : GSMedal
	 */
	@Delete("DELETE FROM " + Constant.GSMEDAL_TABLE + " WHERE ID = #{id}")
	public void delete(GSMedal GSMedal);
	
	/**
	 * ���ݲ�����Ϣ����̬����GSMedal��Ϣ
	  * @param gsmedal : GSMedal
	 */
	@UpdateProvider(type=GSMedalDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(GSMedal gsMedal);
	
	
	/**
	 * ����ID ��ѯGSMedal��Ϣ
	 * @param GSMedal : GSMedal
	 * @return  GSMedal
	 */
	@Select("SELECT * FROM " + Constant.GSMEDAL_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfGSMedal", 
        		fetchType=FetchType.EAGER)),
		@Result(column="teacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public GSMedal get(GSMedal patGSMedalent);
	
	
	/**
	 * ������ʼ����ʱ��β�ѯ�Ķ�̬��ҳ��ѯ
	 * @param params : Map
	 * @return List<Paper>
	 */
	@SelectProvider(type=GSMedalDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfGSMedal", 
        		fetchType=FetchType.EAGER)),
		@Result(column="teacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public List<GSMedal> listDynaPageMap(Map<String, Object> params);
}
