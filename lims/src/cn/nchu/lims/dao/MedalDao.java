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
	 * 根据参数对象Medal，向数据库插入一条medal记录
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
	 * 根据id删除Medal信息
	  * @param medal : Medal
	 */
	@Delete("DELETE FROM " + Constant.MEDAL_TABLE +" WHERE ID = #{id}")
	public void delete(Medal medal);
	
	/**
	 * 将符合条件的记录Medal属性置空
	 * @param photo : String
	 */
	@Update("UPDATE " + Constant.MEDAL_TABLE + " SET photo = null WHERE photo = #{photo}")
	public void updatePhotoSetNull(String photo);
	
	/**
	 * 根据参数信息，动态更新Medal信息
	  * @param medal : Medal
	 */
	@UpdateProvider(type=MedalDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Medal medal);
	
	/**
	 * 根据id查询Medal信息
	 * @param medal : Medal
	 * @return Medal
	 */
	@Select("SELECT * FROM " + Constant.MEDAL_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="teacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public Medal get(Medal medal);
	
	/**
	 * 根据参数动态分页查询查询Project
	 * @param params Map
	 * @return list 根据参数查询的project信息
	 */
	@SelectProvider(type=MedalDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="teacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="winningTime", property="winningTime", javaType=java.sql.Date.class)
	})
	public List<Medal> listDynaPageMap(Map<String, Object> params);
}
