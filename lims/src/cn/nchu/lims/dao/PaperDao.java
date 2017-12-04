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

import cn.nchu.lims.dao.provider.PaperDynaSqlProvider;
import cn.nchu.lims.domain.Paper;
import cn.nchu.lims.util.Constant;

@Repository
public interface PaperDao {

	/**
	 * 根据参数对象Paper，向数据库插入一条paper记录
	 * @param paper : Paper
	 */
	@Insert("INSERT INTO " + Constant.PAPER_TABLE + "("
			+ "name, author, authorComm, authorAll, paperType, indexType, publishTime"
			+ ", journal, vol, abstracts)"
			+ "VALUES(#{name}, #{author}, #{authorComm}, #{authorAll}, #{paperType}"
			+ ", #{indexType}, #{publishTime}, #{journal}, #{vol}, #{abstracts})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Paper paper);
	
	/**
	 * 根据ID删除Paper信息
	 * @param paper : Paper
	 */
	@Delete("DELETE FROM " + Constant.PAPER_TABLE + " WHERE ID = #{id}")
	public void delete(Paper paper);
	
	/**
	 * 根据paper所包含的参数修改Paper相关信息
	 */
	@UpdateProvider(type=PaperDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Paper paper);
	
	/**
	 * 根据ID查询Paper信息
	 * @param paper : Paper
	 * @return Notice
	 */
	@Select("SELECT * FROM " + Constant.PAPER_TABLE + " WHERE ID = #{id}")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPaper", 
        		fetchType=FetchType.EAGER))
	})
	public Paper get(Paper paper);

	/**
	 * 带有起始日期时间段查询的动态分页查询
	 * @param params : Map
	 * @return List<Paper>
	 */
	@SelectProvider(type=PaperDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // 具体的参数映射设置
		@Result(id=true, column="ID", property="id"),  // id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPaper", 
        		fetchType=FetchType.EAGER))
	})
	public List<Paper> listDynaPageMap(Map<String, Object> params);
}
