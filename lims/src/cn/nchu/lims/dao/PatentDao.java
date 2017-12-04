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
	 * 根据patent中的参数，向T_Patent表中插入一条记录
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
	 * 根据ID 删除patent信息
	 * @param patent : Patent
	 */
	@Delete("DELETE FROM " + Constant.PATENT_TABLE + " WHERE ID = #{id}")
	public void delete(Patent patent);
	
	/**
	 * 根据ID 查询patent信息
	 * @param patent : Patent
	 * @return  Patent
	 */
	@Select("SELECT * FROM " + Constant.PATENT_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPatent", 
        		fetchType=FetchType.EAGER))
	})
	public Patent get(Patent patent);

	/**
	 * 根据patent所包含的参数修改Patent相关信息
	 */
	@UpdateProvider(type=PatentDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Patent patent);

	/**
	 * 带有起始日期时间段查询的动态分页查询
	 * @param params : Map
	 * @return List<Patent>
	 */
	@SelectProvider(type=PatentDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPatent", 
        		fetchType=FetchType.EAGER))
	})
	public List<Patent> listDynaPageMap(Map<String, Object> params);

}
