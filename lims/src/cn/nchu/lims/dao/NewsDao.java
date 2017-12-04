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
	 * ���ݲ�����T_News���в���һ���¼�¼
	 * @param news : News
	 */
	@Insert("INSERT INTO "+ Constant.NEWS_TABLE +"("
			+ "title, author, photo, content, publishTime, keyword, seq) "
			+ "VALUES(#{title}, #{author}, #{photo}, #{content}, "
			+ "#{publishTime}, #{keyword}, #{seq})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(News news);
	
	/**
	 * ����ID ɾ��news��Ϣ
	 * @param news : News
	 */
	@Delete("DELETE FROM " + Constant.NEWS_TABLE + " WHERE ID = #{id}")
	public void delete(News news);

	/**
	 * �����������ļ�¼photo�����ÿ�
	 * @param photo : String
	 */
	@Update("UPDATE " + Constant.NEWS_TABLE + " SET photo = null WHERE photo = #{photo}")
	public void updatePhotoSetNull(String photo);
	
	/**
	 * ����news�������Ĳ����޸�MNews�����Ϣ
	 * @param news : News
	 */
	@UpdateProvider(type=NewsDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(News news);
	
	/**
	 * ����Id�޸�seq���ö���״̬
	 * @param news : News
	 */
	@Update("UPDATE " + Constant.NEWS_TABLE + " SET seq = #{seq} WHERE ID = #{id}")
	public void updateState(News news);
	
	/**
	 * ����ID ��ѯnews��Ϣ
	 * @param news : News
	 * @return 
	 */
	@Select("SELECT * FROM " + Constant.NEWS_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(
				id=true, 
				column="ID", 
				property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
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
	 * ������ʼ����ʱ��β�ѯ�Ķ�̬��ҳ��ѯ
	 * @param params : Map
	 * @return List<News>
	 */
	@SelectProvider(type=NewsDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // ����Ĳ���ӳ������
		@Result(
				id=true, 
				column="ID", 
				property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
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
