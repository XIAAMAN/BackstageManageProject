package cn.nchu.lims.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.nchu.lims.domain.Notice;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.PageModel;
import cn.nchu.lims.util.ajax.AjaxJsonRequestDynaPageParam;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

public class NoticeDynaSqlProvider {

	/**
	 * 动态更新
	 * @param notice : Notice
	 * @return String
	 */
	public String updateDyna(final Notice notice) {
		return new SQL() {
			{
				UPDATE(Constant.NOTICE_TABLE);
				if(!StringUtil.isNullOrEmpty(notice.getTitle())) {
					SET("title = #{title}");
				}
				if(!StringUtil.isNullOrEmpty(notice.getAuthor())) {
					SET("author = #{author}");
				}
				if(!StringUtil.isNullOrEmpty(notice.getContent())) {
					SET("content = #{content}");
				}
				SET("ID = #{id}");
				WHERE("ID = #{id}");
			}
		}.toString();
	}
	
	/**
	 * 动态查询
	 * @param notice : Notice
	 * @return String : sql语句
	 */
	public String listDyna(final Notice notice) {
		return new SQL() {
			{
				SELECT("*");
				FROM(Constant.NOTICE_TABLE);
				if(notice != null) {
					if(!StringUtil.isNullOrEmpty(notice.getTitle())) {
						WHERE("title LIKE CONCAT ('%',#{title},'%')");
					}
					if(!StringUtil.isNullOrEmpty(notice.getAuthor())) {
						WHERE("author LIKE CONCAT ('%',#{author},'%')");
					}
					if(!IntegerUtil.isNullOrZero(notice.getSeq())) {  // 查询置顶信息
						WHERE("seq = #{seq}");
					}
				}
				
				ORDER_BY("seq DESC, publishTime DESC");
			}
		}.toString();
	}
	
	/**
	 * 动态分页查询
	 * @param notice : Notice
	 * @return String : sql语句
	 */
	public String listDynaPage(final AjaxJsonRequestDynaPageParam<Notice> ajrdpp) {
		String sql =  new SQL() {
			{
				SELECT("*");
				FROM(Constant.NOTICE_TABLE);
				Notice notice = ajrdpp.getParam();
				if(notice != null) {
					if(!IntegerUtil.isNullOrZero(notice.getId())) {
						WHERE("ID = #{param.id}");
					}
					if(!StringUtil.isNullOrEmpty(notice.getTitle())) {
						WHERE("title LIKE CONCAT ('%',#{param.title},'%')");
					}
					if(!StringUtil.isNullOrEmpty(notice.getAuthor())) {
						WHERE("author LIKE CONCAT ('%',#{param.author},'%')");
					}
					if(!IntegerUtil.isNullOrZero(notice.getSeq())) {  // 查询置顶信息
						WHERE("seq = #{param.seq}");
					}
				}
				
				ORDER_BY("seq DESC, publishTime DESC");
			}
		}.toString();
		
		PageModel pageModel = ajrdpp.getPageModel();
		if(pageModel != null) {
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}";
		}
		
		return sql;
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
				FROM(Constant.NOTICE_TABLE);
				if (!StringUtil.isNullOrEmpty((String)params.get("id"))) {
					WHERE("ID = #{id}");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("title"))) {
					WHERE("title LIKE CONCAT ('%',#{title},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("author"))) {
					WHERE("author LIKE CONCAT ('%',#{author},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("seq"))) {
					WHERE("seq LIKE CONCAT ('%',#{seq},'%')");
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
			sql += " LIMIT #{pageModel.firstLimitParam} , #{pageModel.pageSize}";
		}
		return sql;
	}
		
}
