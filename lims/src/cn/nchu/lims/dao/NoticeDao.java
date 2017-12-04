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
	 * ������Ϣ�����ݿ����Notice��Ϣ
	 * @param notice
	 */
	@Insert("INSERT INTO " + Constant.NOTICE_TABLE + "("
			+ "title, author, content, count, publishTime, seq)"
			+ "VALUES(#{title}, #{author}, #{content}, #{count}, #{publishTime}, #{seq})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Notice notice);
	
	/**
	 * ����IDɾ��Notice��Ϣ
	 * @param notice : Notice
	 */
	@Delete("DELETE FROM " + Constant.NOTICE_TABLE + " WHERE ID = #{id}")
	public void delete(Notice notice);
	
	/**
	 * �޸�notice���ö���Ϣ
	 * @param notice : Notice
	 */
	@Update("UPDATE " + Constant.NOTICE_TABLE + " SET seq = #{seq} WHERE ID = #{id}")
	public void updateState(Notice notice);
	
	/**
	 * ����Notice�����޸����ݿ��е���Ϣ
	 * @param notice : Notice
	 */
	@UpdateProvider(type=NoticeDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Notice notice);
	
	/**
	 * ����ID��ѯNotice��Ϣ
	 * @param notice : Notice
	 * @return Notice
	 */
	@Select("SELECT * FROM " + Constant.NOTICE_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Timestamp.class),
		@Result(property="enclosures",column="id",javaType=List.class, 
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public Notice get(Notice notice);
	
	/**
	 * ���ݲ�ѯ������̬��ѯ���ݿ��з���������¼
	 * @param notice : Notice
	 * @return List<Notice>
	 */
	@SelectProvider(type=NoticeDynaSqlProvider.class, method="listDyna")
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
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public List<Notice> listDyna(Notice notice);
	
	/**
	 * ���ݲ�ѯ������̬��ҳ��ѯ���ݿ��з���������¼
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam
	 * @return List<Notice>
	 */
	@SelectProvider(type=NoticeDynaSqlProvider.class, method="listDynaPage")
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
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public List<Notice> listDynaPage(AjaxJsonRequestDynaPageParam<Notice> ajrdpp);

	/**
	 * ������ʼ����ʱ��β�ѯ�Ķ�̬��ҳ��ѯ
	 * @param params : Map
	 * @return List<Notice>
	 */
	@SelectProvider(type=NoticeDynaSqlProvider.class, method="listDynaPageMap")
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
        		many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfNotice", 
        		fetchType=FetchType.EAGER))
	})
	public List<Notice> listDynaPageMap(Map<String, Object> params);
}
