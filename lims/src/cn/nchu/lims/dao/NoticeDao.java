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
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.NoticeDynaSqlProvider;
import cn.nchu.lims.domain.Notice;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
@Repository
public interface NoticeDao {

	/**
	 * 根据信息向数据库插入Notice信息
	 * @param notice
	 */
	@Insert("INSERT INTO " + Constant.NOTICE_TABLE + "("
			+ "title, author, content, count, publishTime, seq)"
			+ "VALUES(#{title}, #{author}, #{content}, #{count}, #{publishTime}, #{seq})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Notice notice);
	
	/**
	 * 根据ID删除Notice信息
	 * @param notice : Notice
	 */
	@Delete("DELETE FROM " + Constant.NOTICE_TABLE + " WHERE ID = #{id}")
	public void delete(Notice notice);
	
	/**
	 * 修改notice的置顶信息
	 * @param notice : Notice
	 */
	@Update("UPDATE " + Constant.NOTICE_TABLE + " SET seq = #{seq} WHERE ID = #{id}")
	public void updateState(Notice notice);
	
	/**
	 * 根据Notice参数修改数据库中的信息
	 * @param notice : Notice
	 */
	@UpdateProvider(type=NoticeDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Notice notice);
	
	/**
	 * 根据ID查询Notice信息
	 * @param notice : Notice
	 * @return Notice
	 */
	@Select("SELECT * FROM " + Constant.NOTICE_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Timestamp.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public Notice get(Notice notice);
	
	/**
	 * 根据查询参数动态查询数据库中符合条件记录
	 * @param notice : Notice
	 * @return List<Notice>
	 */
	@SelectProvider(type=NoticeDynaSqlProvider.class, method="listDyna")
	@Results({  // 具体的参数映射设置
		@Result(
				id=true, 
				column="ID", 
				property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(
				column="publishTime", 
				property="publishTime", 
				javaType=java.sql.Timestamp.class),
		@Result(
				property="enclosures", 
				column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public List<Notice> listDyna(Notice notice);
	
	/**
	 * 根据查询参数动态分页查询数据库中符合条件记录
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam
	 * @return List<Notice>
	 */
	@SelectProvider(type=NoticeDynaSqlProvider.class, method="listDynaPage")
	@Results({  // 具体的参数映射设置
		@Result(
				id=true, 
				column="ID", 
				property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(
				column="publishTime", 
				property="publishTime", 
				javaType=java.sql.Timestamp.class),
		@Result(
				property="enclosures", 
				column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public List<Notice> listDynaPage(AjaxJsonRequestDynaPageParam<Notice> ajrdpp);

	/**
	 * 带有起始日期时间段查询的动态分页查询
	 * @param params : Map
	 * @return List<Notice>
	 */
	@SelectProvider(type=NoticeDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // 具体的参数映射设置
		@Result(
				id=true, 
				column="ID", 
				property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(
				column="publishTime", 
				property="publishTime", 
				javaType=java.sql.Timestamp.class),
		@Result(
				property="enclosures", 
				column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public List<Notice> listDynaPageMap(Map<String, Object> params);
}
