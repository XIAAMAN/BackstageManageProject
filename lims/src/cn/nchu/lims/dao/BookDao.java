package cn.nchu.lims.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.BookDynaSqlProvider;
import cn.nchu.lims.domain.Book;
import cn.nchu.lims.util.Constant;

@Repository
public interface BookDao {

	/**
	 * ���ݲ�������Book�������ݿ����һ��book��¼
	 * @param book : Book
	 */
	@Insert("INSERT INTO "+ Constant.BOOK_TABLE +"("
			+ "name, publisher, publishTime, teacherId, ranking,"
			+ "totalWords, referenceWords, sponsor, abstracts ) "
			+ "VALUES(#{name}, #{publisher}, #{publishTime}, #{teacher.id}, #{ranking},"
			+ "#{totalWords}, #{referenceWords}, #{sponsor}, #{abstracts})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Book book);
	
	/**
	 * ����idɾ��book��Ϣ
	  * @param book : Book
	 */
	@Delete("DELETE FROM " + Constant.BOOK_TABLE +" WHERE ID = #{id}")
	public void delete(Book book);
	
	/**
	 * ���ݲ�����Ϣ����̬����Book��Ϣ
	  * @param book : Book
	 */
	@UpdateProvider(type=BookDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Book book);
	
	/**
	 * ����id��ѯBook��Ϣ
	 * @param book : Book
	 * @return Book
	 */
	@Select("SELECT * FROM " + Constant.BOOK_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="teacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class)
	})
	public Book get(Book book);
	
	/**
	 * ���ݲ�����̬��ҳ��ѯ��ѯBook
	 * @param params Map
	 * @return list ���ݲ�����ѯ��Book��Ϣ
	 */
	@SelectProvider(type=BookDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="teacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.getToFK",
				fetchType=FetchType.EAGER)),
		@Result(column="publishTime", property="publishTime", javaType=java.sql.Date.class)
	})
	public List<Book> listDynaPageMap(Map<String, Object> params);
}
