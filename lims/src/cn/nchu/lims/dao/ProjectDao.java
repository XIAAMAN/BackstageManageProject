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

import cn.nchu.lims.dao.provider.ProjectDynaSqlProvider;
import cn.nchu.lims.domain.Project;
import cn.nchu.lims.util.Constant;

@Repository
public interface ProjectDao {
	
	/**
	 * 向T_Project表中插入一条新记录
	 * @param project : Project
	 */
	@Insert("INSERT INTO "+ Constant.PROJECT_TABLE +"("
			+ "Name, TeacherId, Member, Code, Fund, "
			+ "Source, Level, StartDate, EndDate, Description ) "
			+ "VALUES(#{name}, #{teacher.id}, #{member},#{code}, #{fund}, "
			+ "#{source}, #{level}, #{startDate}, #{endDate}, #{description})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Project project);
	
	/**
	 * 根据id删除Project信息
	 * @param proejct : Project
	 */
	@Delete("DELETE FROM " + Constant.PROJECT_TABLE +" WHERE  ID= #{id}")
	public void delete(Project project);
	
	/**
	 * 动态更新teacher信息
	 * @param project
	 */
	@UpdateProvider(type=ProjectDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Project project);
	
	/**
	 * 根据id查询Project
	 * @param project : Project
	 * @return Project
	 */
	@Select("SELECT * FROM " + Constant.PROJECT_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="TeacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="StartDate", property="startDate", javaType=java.sql.Date.class),  // javaType 为设置映射的类型
		@Result(column="EndDate", property="endDate", javaType=java.sql.Date.class),  // javaType 为设置映射的类型
		@Result(property="enclosures",column="id",javaType=List.class, 
				many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfProject", 
				fetchType=FetchType.EAGER))
	})
	public Project get(Project project);
	
	/**
	 * 根据参数动态查询Project
	 * @param project
	 * @return list 根据参数查询的project信息
	 */
	@SelectProvider(type=ProjectDynaSqlProvider.class, method="listDyna")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="TeacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="StartDate", property="startDate", javaType=java.sql.Date.class),  // javaType 为设置映射的类型
		@Result(column="EndDate", property="endDate", javaType=java.sql.Date.class),  // javaType 为设置映射的类型
		@Result(property="enclosures",column="id",javaType=List.class, 
				many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfProject", 
				fetchType=FetchType.EAGER))
	})
	public List<Project> listDyna(Project project);
	
	/**
	 * 根据参数动态分页查询查询Project
	 * @param params Map
	 * @return list 根据参数查询的project信息
	 */
	@SelectProvider(type=ProjectDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="TeacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="StartDate", property="startDate", javaType=java.sql.Date.class),  // javaType 为设置映射的类型
		@Result(column="EndDate", property="endDate", javaType=java.sql.Date.class),  // javaType 为设置映射的类型
		@Result(property="enclosures",column="id",javaType=List.class, 
				many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfProject", 
				fetchType=FetchType.EAGER))
		
	})
	public List<Project> listDynaPageMap(Map<String, Object> params);

}
