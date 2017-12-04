package cn.nchu.lims.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.dao.provider.ProjectDynaSqlProvider;
import cn.nchu.lims.domain.Project;
import cn.nchu.lims.util.Constant;

@Repository
public interface ProjectDao {
	
	/**
	 * ��T_Project���в���һ���¼�¼
	 * @param project : Project
	 */
	@Insert("INSERT INTO "+ Constant.PROJECT_TABLE +"("
			+ "Name, TeacherId, Member, Code, Fund, "
			+ "Source, Level, StartDate, EndDate, Description ) "
			+ "VALUES(#{name}, #{teacher.id}, #{member},#{code}, #{fund}, "
			+ "#{source}, #{level}, #{startDate}, #{endDate}, #{description})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void save(Project project);
	
	/**
	 * ����idɾ��Project��Ϣ
	 * @param proejct : Project
	 */
	@Delete("DELETE FROM " + Constant.PROJECT_TABLE +" WHERE  ID= #{id}")
	public void delete(Project project);
	
	/**
	 * ��̬����teacher��Ϣ
	 * @param project
	 */
	@UpdateProvider(type=ProjectDynaSqlProvider.class, method="updateDyna")
	public void updateDyna(Project project);
	
	/**
	 * ����id��ѯProject
	 * @param project : Project
	 * @return Project
	 */
	@Select("SELECT * FROM " + Constant.PROJECT_TABLE + " WHERE ID = #{id}")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="TeacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="StartDate", property="startDate", javaType=java.sql.Date.class),  // javaType Ϊ����ӳ�������
		@Result(column="EndDate", property="endDate", javaType=java.sql.Date.class),  // javaType Ϊ����ӳ�������
		@Result(property="enclosures",column="id",javaType=List.class, 
				many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfProject", 
				fetchType=FetchType.EAGER))
	})
	public Project get(Project project);
	
	/**
	 * ���ݲ�����̬��ѯProject
	 * @param project
	 * @return list ���ݲ�����ѯ��project��Ϣ
	 */
	@SelectProvider(type=ProjectDynaSqlProvider.class, method="listDyna")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="TeacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="StartDate", property="startDate", javaType=java.sql.Date.class),  // javaType Ϊ����ӳ�������
		@Result(column="EndDate", property="endDate", javaType=java.sql.Date.class),  // javaType Ϊ����ӳ�������
		@Result(property="enclosures",column="id",javaType=List.class, 
				many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfProject", 
				fetchType=FetchType.EAGER))
	})
	public List<Project> listDyna(Project project);
	
	/**
	 * ���ݲ�����̬��ҳ��ѯ��ѯProject
	 * @param params Map
	 * @return list ���ݲ�����ѯ��project��Ϣ
	 */
	@SelectProvider(type=ProjectDynaSqlProvider.class, method="listDynaPageMap")
	@Results({  // ����Ĳ���ӳ������
		@Result(id=true, column="ID", property="id"),  // id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(column="TeacherId", property="teacher",  // one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
				one=@One(select="cn.nchu.lims.dao.TeacherDao.get",
				fetchType=FetchType.EAGER)),
		@Result(column="StartDate", property="startDate", javaType=java.sql.Date.class),  // javaType Ϊ����ӳ�������
		@Result(column="EndDate", property="endDate", javaType=java.sql.Date.class),  // javaType Ϊ����ӳ�������
		@Result(property="enclosures",column="id",javaType=List.class, 
				many=@Many(select="cn.nchu.lims.dao.EnclosureDao.getByForginKeyOfProject", 
				fetchType=FetchType.EAGER))
		
	})
	public List<Project> listDynaPageMap(Map<String, Object> params);

}
