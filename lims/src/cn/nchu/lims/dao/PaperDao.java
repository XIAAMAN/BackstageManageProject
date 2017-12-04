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
	 * ���ݲ�������Paper�������ݿ����һ��paper��¼
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
	 * ����IDɾ��Paper��Ϣ
	 * @param paper : Paper
	 */
	@Delete("DELETE FROM " + Constant.PAPER_TABLE + " WHERE ID = #{id}")
	public void delete(Paper paper);
	
	/**
	 * ����paper�������Ĳ����޸�Paper�����Ϣ
	 */
	@UpdateProvider(type=PaperDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Paper paper);
	
	/**
	 * ����ID��ѯPaper��Ϣ
	 * @param paper : Paper
	 * @return Notice
	 */
	@Select("SELECT * FROM " + Constant.PAPER_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPaper", 
        		fetchType=FetchType.EAGER))
	})
	public Paper get(Paper paper);

	/**
	 * ������ʼ����ʱ��β�ѯ�Ķ�̬��ҳ��ѯ
	 * @param params : Map
	 * @return List<Paper>
	 */
	@SelectProvider(type=PaperDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfPaper", 
        		fetchType=FetchType.EAGER))
	})
	public List<Paper> listDynaPageMap(Map<String, Object> params);
}
