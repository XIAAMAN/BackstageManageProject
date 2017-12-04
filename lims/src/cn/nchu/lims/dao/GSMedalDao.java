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
	 * 根据GSMedal中的参数，向T_GSMedal表中插入一条记录
	 * @param patent : GSMedal
	 */
	@Insert("INSERT INTO "+ Constant.GSMEDAL_TABLE +"("
			+ "name, teacherId, studentAll, level, grade, winningTime, abstracts) "
			+ "VALUES(#{name}, #{teacher.id} ,#{studentAll}, #{level}, "
			+ "#{grade}, #{winningTime}, #{abstracts})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(GSMedal gsmedal);
	
	/**
	 * 根据ID 删除gsmedal信息
	 * @param gsmedal : GSMedal
	 */
	@Delete("DELETE FROM " + Constant.GSMEDAL_TABLE + " WHERE ID = #{id}")
	public void delete(GSMedal GSMedal);
	
	/**
	 * 根据参数信息，动态更新GSMedal信息
	  * @param gsmedal : GSMedal
	 */
	@UpdateProvider(type=GSMedalDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(GSMedal gsMedal);
	
	
	/**
	 * 根据ID 查询GSMedal信息
	 * @param GSMedal : GSMedal
	 * @return  GSMedal
	 */
	@Select("SELECT * FROM " + Constant.GSMEDAL_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfGSMedal", 
        		fetchType=FetchType.EAGER)),
		@Result(column="teacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public GSMedal get(GSMedal patGSMedalent);
	
	
	/**
	 * 带有起始日期时间段查询的动态分页查询
	 * @param params : Map
	 * @return List<Paper>
	 */
	@SelectProvider(type=GSMedalDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfGSMedal", 
        		fetchType=FetchType.EAGER)),
		@Result(column="teacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public List<GSMedal> listDynaPageMap(Map<String, Object> params);
}
