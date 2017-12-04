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
	 * ����ID��T_xxxEnclosureɾ��Enclosure��Ϣ
	 * @param edm : EnclosureDatabaseManageModel
	 */
	@DeleteProvider(type=EnclosureDynaSqlProvider.class, method="deleteDyna")
	public void deleteDyna(EnclosureREUDModel edmm);
	
	/**
	 * ����ID��T_xxxEnclosure�޸�Enclosure��Ϣ
	 * @param edm : EnclosureDatabaseManageModel
	 */
	@UpdateProvider(type=EnclosureDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(EnclosureREUDModel edmm);
	
	/**
	 * ����ID��T_xxxEnclosure��ѯEnclosure
	 * @param downloadMap : Map
	 * @return Enclosure
	 */
	@SelectProvider(type=EnclosureDynaSqlProvider.class, method="get")
	public Enclosure get(Map<String, String> downloadMap);
	
	/**
	 * ����ID��T_xxxEnclosure��ѯEnclosure
	 * @param edm : EnclosureDatabaseManageModel
	 * @return Enclosure
	 */
	@SelectProvider(type=EnclosureDynaSqlProvider.class, method="getDyna")
	public Enclosure getDyna(EnclosureREUDModel edmm);
	
	/*********************************** NewsEnclosure ***********************************/

	/**
	 * ��T_NewsEnclosure��ѯ�������������Enclosure
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
	 * ��T_PatentEnclosure��ѯ�������������Enclosure
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
	 * ��T_PaperEnclosure��ѯ�������������Enclosure
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
	 * ��T_ProjectEnclosure��ѯ�������������Enclosure
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
	 * ��T_GSMedalEnclosure��ѯ�������������Enclosure
	 * @param id : int
	 * @return Enclosure
	 */
	@Select("SELECT * FROM " + Constant.GSMEDALENCLOSURE_TABLE + " WHERE gsmedalId = #{forginKey}")
	@Results({  
		@Result(id=true, column="ID", property="id")
	})
	public List<Enclosure> getByForginKeyOfGSMedal(int forginKey);
}
