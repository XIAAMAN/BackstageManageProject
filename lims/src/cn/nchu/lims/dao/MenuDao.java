package cn.nchu.lims.dao;

import java.util.List;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import cn.nchu.lims.domain.Menu;
import cn.nchu.lims.util.Constant;

@Repository
public interface MenuDao {

	/**
	 * ����parentId �õ�һ�Զ�Ķ�Ķ�����Ϣ
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM " + Constant.MENU_TABLE + " WHERE parentId = #{id}")
	// ����Ĳ���ӳ������
	@Results({
		// id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(id=true, column="ID", property="id"),
		// one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
		@Result(column="id", property="menus",
				many=@Many(select="cn.nchu.lims.dao.MenuDao.listSonsByParentId",
				fetchType=FetchType.EAGER))
	})
	public List<Menu> listSonsByParentId(int id);
	
	/**
	 * �õ����е�menu һ���˵������¼��˵�����Menu�е�munes���Ի�ã�
	 * @param id
	 * @return list
	 */
	@Select("SELECT * FROM " + Constant.MENU_TABLE + " WHERE parentId IS NULL")
	// ����Ĳ���ӳ������
	@Results({
		// id=true ��ʾidΪ������column Ϊ���ݿ���ֶ�����property Ϊ�����е�������
		@Result(id=true, column="ID", property="id"),
		// one=@One ��ʾOneToMany �е�One��select Ϊ�����ù�������Ĳ�ѯ������fetchType �������ط�ʽ
		@Result(column="ID", property="menus",
				many=@Many(select="cn.nchu.lims.dao.MenuDao.listSonsByParentId",
				fetchType=FetchType.EAGER))
	})
	public List<Menu> listLevelOne();
	
}
