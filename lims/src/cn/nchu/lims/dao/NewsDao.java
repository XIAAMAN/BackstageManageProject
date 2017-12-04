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

import cn.nchu.lims.dao.provider.NewsDynaSqlProvider;
import cn.nchu.lims.domain.News;
import cn.nchu.lims.util.Constant;

@Repository
public interface NewsDao {
	/**
	 * 根据参数向T_News表中插入一条新记录
	 * @param news : News
	 */
	@Insert("INSERT INTO "+ Constant.NEWS_TABLE +"("
			+ "title, author, photo, content, publishTime, keyword, seq) "
			+ "VALUES(#{title}, #{author}, #{photo}, #{content}, "
			+ "#{publishTime}, #{keyword}, #{seq})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(News news);
	
	/**
	 * 根据ID 删除news信息
	 * @param news : News
	 */
	@Delete("DELETE FROM " + Constant.NEWS_TABLE + " WHERE ID = #{id}")
	public void delete(News news);

	/**
	 * 将符合条件的记录photo属性置空
	 * @param photo : String
	 */
	@Update("UPDATE " + Constant.NEWS_TABLE + " SET photo = null WHERE photo = #{photo}")
	public void updatePhotoSetNull(String photo);
	
	/**
	 * 根据news所包含的参数修改MNews相关信息
	 * @param news : News
	 */
	@UpdateProvider(type=NewsDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(News news);
	
	/**
	 * 根据Id修改seq（置顶）状态
	 * @param news : News
	 */
	@Update("UPDATE " + Constant.NEWS_TABLE + " SET seq = #{seq} WHERE ID = #{id}")
	public void updateState(News news);
	
	/**
	 * 根据ID 查询news信息
	 * @param news : News
	 * @return 
	 */
	@Select("SELECT * FROM " + Constant.NEWS_TABLE + " WHERE ID = #{id}")
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
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNews", 
        		fetchType=FetchType.EAGER))
	})
	public News get(News news);

	/**
	 * 带有起始日期时间段查询的动态分页查询
	 * @param params : Map
	 * @return List<News>
	 */
	@SelectProvider(type=NewsDynaSqlProvider.class, method="listDynaPageMap")
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
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNews", 
        		fetchType=FetchType.EAGER))
	})
	public List<News> listDynaPageMap(Map<String, Object> params);

}
