package cn.nchu.lims.dao;

import java.util.List;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.domain.Menu;
import cn.nchu.lims.util.Constant;

@Repository
public interface MenuDao {

	/**
	 * 根究parentId 得到一对多的多的对象信息
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM " + Constant.MENU_TABLE + " WHERE parentId = #{id}")
	// 具体的参数映射设置
	@Results({
		// id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(id=true, column="ID", property="id"),
		// one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
		@Result(column="id", property="menus",
				many=@Many(select="cn.nchu.lims.dao.MenuDao.listSonsByParentId",
				fetchType=FetchType.EAGER))
	})
	public List<Menu> listSonsByParentId(int id);
	
	/**
	 * 得到所有的menu 一级菜单对象（下级菜单根据Menu中的munes属性获得）
	 * @param id
	 * @return list
	 */
	@Select("SELECT * FROM " + Constant.MENU_TABLE + " WHERE parentId IS NULL")
	// 具体的参数映射设置
	@Results({
		// id=true 表示id为主键，column 为数据库的字段名，property 为对象中的属性名
		@Result(id=true, column="ID", property="id"),
		// one=@One 表示OneToMany 中的One，select 为所引用关联对象的查询方法，fetchType 级联加载方式
		@Result(column="ID", property="menus",
				many=@Many(select="cn.nchu.lims.dao.MenuDao.listSonsByParentId",
				fetchType=FetchType.EAGER))
	})
	public List<Menu> listLevelOne();
	
}
