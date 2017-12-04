package cn.nchu.lims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.LifeShowDynaSqlProvider;
import cn.nchu.lims.domain.LifeShow;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;

@Repository
public interface LifeShowDao {

	@Insert("INSERT INTO " + Constant.LIFESHOW_TABLE + " (type, fileName)"
			+ " VALUES (#{type}, #{fileName})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(LifeShow lifeShow);
	
	@Delete("DELETE FROM " + Constant.LIFESHOW_TABLE + " WHERE id = #{id}")
	public void delete(LifeShow lifeShow);
	
	@Update("UPDATE " + Constant.LIFESHOW_TABLE + " SET type = #{type} WHERE id = #{id}")
	public void updateType(LifeShow lifeShow);
	
	@Select("SELECT * FROM " + Constant.LIFESHOW_TABLE + " WHERE id = #{id}")
	public LifeShow get(LifeShow lifeShow);
	
	@SelectProvider(type=LifeShowDynaSqlProvider.class, method="listDynaPage")
	public List<LifeShow> listDynaPage(AjaxJsonRequestDynaPageParam<LifeShow> ajrdpp);
}
