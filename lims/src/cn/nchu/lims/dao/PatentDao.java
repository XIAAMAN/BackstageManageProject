package cn.nchu.lims.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.PatentDynaSqlProvider;
import cn.nchu.lims.domain.Patent;
import cn.nchu.lims.util.Constant;

@Repository
public interface PatentDao {

	/**
	 * ����patent�еĲ�������T_Patent���в���һ����¼
	 * @param patent : Patent
	 */
	@Insert("INSERT INTO "+ Constant.PATENT_TABLE +"("
			+ "name, author, authorAll, patentType, status, publishTime"
			+ ", abstracts, processingTime, code) "
			+ "VALUES(#{name}, #{author}, #{authorAll}, "
			+ "#{patentType}, #{status}, #{publishTime}, #{abstracts}"
			+ ", #{processingTime}, #{code})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Patent patent);
	
	/**
	 * ����ID ɾ��patent��Ϣ
	 * @param patent : Patent
	 */
	@Delete("DELETE FROM " + Constant.PATENT_TABLE + " WHERE ID = #{id}")
	public void delete(Patent patent);
	
	/**
	 * ����ID ��ѯpatent��Ϣ
	 * @param patent : Patent
	 * @return  Patent
	 */
	@Select("SELECT * FROM " + Constant.PATENT_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPatent", 
        		fetchType=FetchType.EAGER))
	})
	public Patent get(Patent patent);

	/**
	 * ����patent�������Ĳ����޸�Patent�����Ϣ
	 */
	@UpdateProvider(type=PatentDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Patent patent);

	/**
	 * ������ʼ����ʱ��β�ѯ�Ķ�̬��ҳ��ѯ
	 * @param params : Map
	 * @return List<Patent>
	 */
	@SelectProvider(type=PatentDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPatent", 
        		fetchType=FetchType.EAGER))
	})
	public List<Patent> listDynaPageMap(Map<String, Object> params);

}
