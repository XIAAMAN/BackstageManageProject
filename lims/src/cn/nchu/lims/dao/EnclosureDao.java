package cn.nchu.lims.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.EnclosureDynaSqlProvider;
import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.util.EnclosureREUDModel;
import cn.nchu.lims.util.Constant;

@Repository
public interface EnclosureDao {
	
	
	/************************* Enclosure *************************/
	
	@InsertProvider(type=EnclosureDynaSqlProvider.class, method="saveDyna")
	@Options(useGeneratedKeys=true, keyProperty="enclosure.id")
	public void saveDyna(EnclosureREUDModel eModel);
	
	/**
	 * 根据ID从T_xxxEnclosure删除Enclosure信息
	 * @param edm : EnclosureDatabaseManageModel
	 */
	@DeleteProvider(type=EnclosureDynaSqlProvider.class, method="deleteDyna")
	public void deleteDyna(EnclosureREUDModel edmm);
	
	/**
	 * 根据ID从T_xxxEnclosure修改Enclosure信息
	 * @param edm : EnclosureDatabaseManageModel
	 */
	@UpdateProvider(type=EnclosureDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(EnclosureREUDModel edmm);
	
	/**
	 * 根据ID从T_xxxEnclosure查询Enclosure
	 * @param downloadMap : Map
	 * @return Enclosure
	 */
	@SelectProvider(type=EnclosureDynaSqlProvider.class, method="get")
	public Enclosure get(Map<String, String> downloadMap);
	
	/**
	 * 根据ID从T_xxxEnclosure查询Enclosure
	 * @param edm : EnclosureDatabaseManageModel
	 * @return Enclosure
	 */
	@SelectProvider(type=EnclosureDynaSqlProvider.class, method="getDyna")
	public Enclosure getDyna(EnclosureREUDModel edmm);
	
	/*********************************** NewsEnclosure ***********************************/

	/**
	 * 从T_NewsEnclosure查询符合外键参数的Enclosure
	 * @param id : int
	 * @return Enclosure
	 */
	@Select("SELECT * FROM " + Constant.NEWSENCLOSURE_TABLE + " WHERE newsId = #{forginKey}")
	@Results({  
		@Result(id=true, column="ID", property="id"),  
		@Result(column="publishTime", property="publishTime", 
			javaType=java.sql.Date.class) 
	})
	public List<Enclosure> getByForginKeyOfNews(int forginKey);
	
	
	/************************************ NoticeEnclosure ***********************************/
	
	/**
	 * T_NoticeEnclosure
	 * @param id : int
	 * @return Enclosure
	 */
	@Select("SELECT * FROM " + Constant.NOTICEENCLOSURE_TABLE + " WHERE noticeId = #{forginKey}")
	@Results({  
		@Result(id=true, column="ID", property="id"),  
		@Result(column="publishTime", property="publishTime", 
			javaType=java.sql.Date.class) 
	})
	public List<Enclosure> getByForginKeyOfNotice(int forginKey);

	
	/*********************************** PatentEnclosure ***********************************/
	
	/**
	 * 从T_PatentEnclosure查询符合外键参数的Enclosure
	 * @param id : int
	 * @return Enclosure
	 */
	@Select("SELECT * FROM " + Constant.PATENTENCLOSURE_TABLE + " WHERE patentId = #{forginKey}")
	@Results({  
		@Result(id=true, column="ID", property="id"),  
		@Result(column="publishTime", property="publishTime", 
			javaType=java.sql.Date.class) 
	})
	public List<Enclosure> getByForginKeyOfPatent(int forginKey);
	
	
	/*********************************** PaperEnclosure ***********************************/
	
	/**
	 * 从T_PaperEnclosure查询符合外键参数的Enclosure
	 * @param id : int
	 * @return Enclosure
	 */
	@Select("SELECT * FROM " + Constant.PAPERENCLOSURE_TABLE + " WHERE paperId = #{forginKey}")
	@Results({  
		@Result(id=true, column="ID", property="id"),  
		@Result(column="publishTime", property="publishTime", 
			javaType=java.sql.Date.class) 
	})
	public List<Enclosure> getByForginKeyOfPaper(int forginKey);

	/*********************************** ProjectEnclosure ***********************************/
	
	/**
	 * 从T_ProjectEnclosure查询符合外键参数的Enclosure
	 * @param id : int
	 * @return Enclosure
	 */
	@Select("SELECT * FROM " + Constant.PROJECTENCLOSURE_TABLE + " WHERE projectId = #{forginKey}")
	@Results({  
		@Result(id=true, column="ID", property="id"),  
		@Result(column="publishTime", property="publishTime", 
			javaType=java.sql.Date.class) 
	})
	public List<Enclosure> getByForginKeyOfProject(int forginKey);
	
/*********************************** GSMedalEnclosure ***********************************/
	
	/**
	 * 从T_GSMedalEnclosure查询符合外键参数的Enclosure
	 * @param id : int
	 * @return Enclosure
	 */
	@Select("SELECT * FROM " + Constant.GSMEDALENCLOSURE_TABLE + " WHERE gsmedalId = #{forginKey}")
	@Results({  
		@Result(id=true, column="ID", property="id")
	})
	public List<Enclosure> getByForginKeyOfGSMedal(int forginKey);
}
