package cn.nchu.lims.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import cn.nchu.lims.domain.Book;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class BookDynaSqlProvider {
	/**
	 * 动态更新book信息
	 * @param book : Book
	 * @return sql语句 : String
	 */
	public String updateDyna(final Book book) {
		return new SQL() {
			{
				UPDATE(Constant.BOOK_TABLE);
				if(!StringUtil.isNullOrEmpty(book.getName())) {
					SET("name = #{name}");
				}
				if(book.getTeacher().getId() != 0) {
					SET("TeacherId = #{teacher.id}");
				}
				if(!StringUtil.isNullOrEmpty(book.getPublisher())) {
					SET("publisher = #{publisher}");
				}
				if(book.getPublishTime() != null) {
					SET("publishTime = #{publishTime}");
				}
				if(!StringUtil.isNullOrEmpty(book.getRanking())) {
					SET("ranking = #{ranking}");
				}
				if(!StringUtil.isNullOrEmpty(book.getRanking())) {
					SET("ranking = #{ranking}");
				}
				if(book.getTotalWords() > 0) {
					SET("totalWords = #{totalWords}");
				}
				if(book.getReferenceWords() > 0) {
					SET("referenceWords = #{referenceWords}");
				}
				if(!StringUtil.isNullOrEmpty(book.getSponsor())) {
					SET("sponsor = #{sponsor}");
				}
				if(!StringUtil.isNullOrEmpty(book.getAbstracts())) {
					SET("abstracts = #{abstracts}");
				}
				SET("ID = #{id}");
				WHERE("ID = #{id}");
			}
		}.toString();
	}
	

	/**
	 * 根据参数动态查询
	 * @param book : Book
	 * @return sql语句 : String
	 */
	public String listDynaPageMap(final Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(Constant.BOOK_TABLE);
				if(!StringUtil.isNullOrEmpty((String)params.get("name"))) {
					WHERE("Name LIKE CONCAT ('%',#{name},'%')");
				}
				if (!StringUtil.isNullOrEmpty((String)params.get("teacherId"))) {
					WHERE("TeacherId = #{teacherId}");
				}
				if (!StringUtil.isNullOrEmpty((String)params.get("publisher"))) {
					WHERE("publisher = #{publisher}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("sponsor"))) {
					WHERE("sponsor = #{sponsor}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("ranking"))) {
					WHERE("ranking = #{ranking}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(winningTime) between " + params.get("startDate_s") + " AND " + params.get("startDate_e"));
				} else if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(winningTime) >= " + params.get("startDate_s"));
				} else if(StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(winningTime) <= " + params.get("startDate_e"));
				}
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}
}
