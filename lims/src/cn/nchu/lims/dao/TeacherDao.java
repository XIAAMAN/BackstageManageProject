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
	 * 向T_Teacher表中插入一条记录，id为自增长
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
	 * 根据参数信息，动态添加teacher
	 * @param teacher : Teacher
	 */
	@InsertProvider(type=TeacherDynaSqlProvider.class, method="save")
	public void save(Teacher teacher);
	
	/**
	 * 根据id删除teacher信息
	  * @param teacher : Teacher
	 */
	@Delete("DELETE FROM " + Constant.TEACHER_TABLE + " WHERE ID = #{id}")
	public void delete(Teacher teacher);
	
	/**
	 * 根据id虚拟删除
	 * @param teacher
	 */
	@Update("UPDATE " + Constant.TEACHER_TABLE + " SET state = '0' WHERE ID = #{id}")
	public void deleteState(Teacher teacher);
	
	/**
	 * 将符合条件的记录photo属性置空
	 * @param photo : String
	 */
	@Update("UPDATE " + Constant.TEACHER_TABLE + " SET photo = null WHERE photo = #{photo}")
	public void updatePhotoSetNull(String photo);
	
	/**
	 * 根据参数信息，动态更新teacher信息
	  * @param teacher : Teacher
	 */
	@UpdateProvider(type=TeacherDynaSqlProvider.class, method="update")
	public void update(Teacher teacher);
	
	/**
	 * 根据id查询teacher信息，用于外键关联查询
	 * @param teacher : Teacher
	 * @return Teacher
	 */
	@Select("select ID, Name, College from " + Constant.TEACHER_TABLE   // 数据库T_Teacher表的常量
			+ " where id = #{id}")
	public Teacher getToFK(Teacher teacher);
	
	/**
	 * 根据id查询teacher信息
	 * @param teacher : Teacher
	 * @return Teacher
	 */
	@Select("select "
			+ "ID, Name, photo, Major, College, GraduateCollege, ResearchInterest, "
			+ "Title, Position, Director, Email, Description "
			+ "from " + Constant.TEACHER_TABLE + " "  // 数据库T_Teacher表的常量
			+ "where id = #{id}")
	public Teacher get(Teacher teacher);
	
	/**
	 * 静态查询所有teacher信息
	 * @return List<Teacher>
	 */
	@Select("SELECT * FROM " + Constant.TEACHER_TABLE)
	public List<Teacher> list();
	
	/**
	 * 动态查询teacher信息,Teacher传参
	 * @param teacher : Teacher
	 * @return List<Teacher>
	 */
	@SelectProvider(type=TeacherDynaSqlProvider.class, method="listDyna")
	public List<Teacher> listDyna(Teacher teacher);
	
	/**
	 * 动态分页查询teacher信息,Teacher传参
	 * @param ajrdpp : AjaxJsonRequestDynaPageParam
	 * @return List<Teacher>
	 */
	@SelectProvider(type=TeacherDynaSqlProvider.class, method="listDynaPage")
	public List<Teacher> listDynaPage(AjaxJsonRequestDynaPageParam<Teacher> ajrdpp);
	
}
