package cn.nchu.lims.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.nchu.lims.domain.News;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class NewsDynaSqlProvider {

	/**
	 * 根据参数动态修改News信息
	 * @param news : News
	 * @return String : sql语句
	 */
	public String updateDyna(final News news) {
		return new SQL() {
			{
				UPDATE(Constant.NEWS_TABLE);
				if(!StringUtil.isNullOrEmpty(news.getTitle())) {
					SET("title = #{title}");
				}
				if(!StringUtil.isNullOrEmpty(news.getAuthor())) {
					SET("author = #{author}");
				}
				if(!StringUtil.isNullOrEmpty(news.getPhoto())) {
					SET("photo = #{photo}");
				}
				if(!StringUtil.isNullOrEmpty(news.getContent())) {
					SET("content = #{content}");
				}
				if(!StringUtil.isNullOrEmpty(news.getKeyword())) {
					SET("keyword = #{keyword}");
				}
				SET("ID = #{id}");
				WHERE("ID = #{id}");
			}
		}.toString();
	}
	
	/**
	 * 根据参包括起始时间段数动态查询
	 * @param param : Map
	 * @return sql语句 : String
	 */
	public String listDynaPageMap(final Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(Constant.NEWS_TABLE);
				if (!StringUtil.isNullOrEmpty((String)params.get("id"))) {
					WHERE("ID = #{id}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("title"))) {
					WHERE("title LIKE CONCAT ('%',#{title},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("author"))) {
					WHERE("author LIKE CONCAT ('%',#{author},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("keyword"))) {
					WHERE("keyword LIKE CONCAT ('%',#{keyword},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("seq"))) {
					WHERE("seq = #{seq}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("publishTime >= '" + (String)params.get("startDate_s") 
						+ "' and publishTime < '" + (String)params.get("startDate_e") + "'");
				} else if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("publishTime >= '" + (String)params.get("startDate_s") + "'");
				} else if(StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("publishTime < " + params.get("startDate_e"));
				}
				
				ORDER_BY("seq DESC, publishTime DESC");
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}
}
