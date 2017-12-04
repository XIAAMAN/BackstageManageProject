package cn.nchu.lims.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.TeacherDynaSqlProvider;
import cn.nchu.lims.domain.Teacher;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;

@Repository
public interface TeacherDao {
	
	/**
	 * ��T_Teacher���в���һ����¼��idΪ������
	 * @param teacher : Teacher
	 */
	@Insert("INSERT INTO "+ Constant.TEACHER_TABLE +"("
			+ "Name, photo,  Major, College, GraduateCollege, ResearchInterest, "
			+ "Title, Position, Director, Email, Description ) "
			+ "VALUES(#{name},#{photo},#{major},#{college},#{graduateCollege},"
			+ "#{researchInterest},#{title},#{position},"
			+ "#{director},#{email},#{description})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void saveAndReturnId(Teacher teacher);
	
	/**
	 * ���ݲ�����Ϣ����̬���teacher
	 * @param teacher : Teacher
	 */
	@InsertProvider(type=TeacherDynaSqlProvider.class, method="save")
	public void save(Teacher teacher);
	
	/**
	 * ����idɾ��teacher��Ϣ
	  * @param teacher : Teacher
	 */
	@Delete("DELETE FROM " + Constant.TEACHER_TABLE + " WHERE ID = #{id}")
	public void delete(Teacher teacher);
	
	/**
	 * ����id����ɾ��
	 * @param teacher
	 */
	@Update("UPDATE " + Constant.TEACHER_TABLE + " SET state = '0' WHERE ID = #{id}")
	public void deleteState(Teacher teacher);
	
	/**
	 * �����������ļ�¼photo�����ÿ�
	 * @param photo : String
	 */
	@Update("UPDATE " + Constant.TEACHER_TABLE + " SET photo = null WHERE photo = #{photo}")
	public void updatePhotoSetNull(String photo);
	
	/**
	 * ���ݲ�����Ϣ����̬����teacher��Ϣ
	  * @param teacher : Teacher
	 */
	@UpdateProvider(type=TeacherDynaSqlProvider.class, method="update")
	public void update(Teacher teacher);
	
	/**
	 * ����id��ѯteacher��Ϣ���������������ѯ
	 * @param teacher : Teacher
	 * @return Teacher
	 */
	@Select("select ID, Name, College from " + Constant.TEACHER_TABLE   // ���ݿ�T_Teacher��ĳ���
			+ " where id = #{id}")
	public Teacher getToFK(Teacher teacher);
	
	/**
	 * ����id��ѯteacher��Ϣ
	 * @param teacher : Teacher
	 * @return Teacher
	 */
	@Select("select "
			+ "ID, Name, photo, Major, College, GraduateCollege, ResearchInterest, "
			+ "Title, Position, Director, Email, Description "
			+ "from " + Constant.TEACHER_TABLE + " "  // ���ݿ�T_Teacher��ĳ���
			+ "where id = #{id}")
	public Teacher get(Teacher teacher);
	
	/**
	 * ��̬��ѯ����teacher��Ϣ
	 * @return List<Teacher>
	 */
	@Select("SELECT * FROM " + Constant.TEACHER_TABLE)
	public List<Teacher> list();
	
	/**
	 * ��̬��ѯteacher��Ϣ,Teacher����
	 * @param teacher : Teacher
	 * @return List<Teacher>
	 */
	@SelectProvider(type=TeacherDynaSqlProvider.class, method="listDyna")
	public List<Teacher> listDyna(Teacher teacher);
	
	/**
	 * ��̬��ҳ��ѯteacher��Ϣ,Teacher����
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam
	 * @return List<Teacher>
	 */
	@SelectProvider(type=TeacherDynaSqlProvider.class, method="listDynaPage")
	public List<Teacher> listDynaPage(AjaxJsonRequestDynaPageParam<Teacher> ajrdpp);
	
}
