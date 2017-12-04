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
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.BookDynaSqlProvider;
import cn.nchu.lims.domain.Book;
import cn.nchu.lims.util.Constant;

@Repository
public interface BookDao {

	/**
	 * 根据参数对象Book，向数据库插入一条book记录
	 * @param book : Book
	 */
	@Insert("INSERT INTO "+ Constant.BOOK_TABLE +"("
			+ "name, publisher, publishTime, teacherId, ranking,"
			+ "totalWords, referenceWords, sponsor, abstracts ) "
			+ "VALUES(#{name}, #{publisher}, #{publishTime}, #{teacher.id}, #{ranking},"
			+ "#{totalWords}, #{referenceWords}, #{sponsor}, #{abstracts})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Book book);
	
	/**
	 * 根据id删除book信息
	  * @param book : Book
	 */
	@Delete("DELETE FROM " + Constant.BOOK_TABLE +" WHERE ID = #{id}")
	public void delete(Book book);
	
	/**
	 * 根据参数信息，动态更新Book信息
	  * @param book : Book
	 */
	@UpdateProvider(type=BookDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Book book);
	
	/**
	 * 根据id查询Book信息
	 * @param book : Book
	 * @return Book
	 */
	@Select("SELECT * FROM " + Constant.BOOK_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="teacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class)
	})
	public Book get(Book book);
	
	/**
	 * 根据参数动态分页查询查询Book
	 * @param params Map
	 * @return list 根据参数查询的Book信息
	 */
	@SelectProvider(type=BookDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="teacherId", property="teacher",  // one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class)
	})
	public List<Book> listDynaPageMap(Map<String, Object> params);
}
