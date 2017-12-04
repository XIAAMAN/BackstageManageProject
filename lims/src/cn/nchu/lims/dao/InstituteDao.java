package cn.nchu.lims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.domain.Institute;
import cn.nchu.lims.util.Constant;

@Repository
public interface InstituteDao {
	
	@Select("SELECT name FROM " + Constant.INSTUTE_TABLE + " WHERE id = #{id}")
	public String getName(int id);
	
	@Select("SELECT id, name FROM " + Constant.INSTUTE_TABLE)
	public List<Institute> list();
}
