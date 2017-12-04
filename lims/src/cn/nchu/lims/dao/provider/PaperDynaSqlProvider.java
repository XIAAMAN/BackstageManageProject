package cn.nchu.lims.dao.provider;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.nchu.lims.domain.Paper;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class PaperDynaSqlProvider {
	/**
	 * 根据paper分装的参数，动态修改数据库信息。
	 *   id 为查询条件
	 * @param paper : Paper
	 * @return String
	 */
	public String updateDyna(final Paper paper) {
		return new SQL() {
			{
				UPDATE(Constant.PAPER_TABLE);
				if(!StringUtil.isNullOrEmpty(paper.getName())) {
					SET("name = #{name}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getAuthor())) {
					SET("author = #{author}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getAuthorComm())) {
					SET("authorComm = #{authorComm}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getAuthorAll())) {
					SET("authorAll = #{authorAll}");
				}
				if(paper.getPublishTime() != null) {
					SET("publishTime = #{publishTime}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getPaperType())) {
					SET("paperType = #{paperType}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getIndexType())) {
					SET("indexType = #{indexType}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getJournal())) {
					SET("journal = #{journal}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getVol())) {
					SET("vol = #{vol}");
				}
				if(!StringUtil.isNullOrEmpty(paper.getAbstracts())) {
					SET("abstracts = #{abstracts}");
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
		@SuppressWarnings("unchecked")
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(Constant.PAPER_TABLE);
				if(!StringUtil.isNullOrEmpty((String)params.get("name"))) {
					WHERE("name LIKE CONCAT ('%',#{name},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("author"))) {
					WHERE("author LIKE CONCAT ('%',#{author},'%') OR authorAll LIKE CONCAT ('%',#{author},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("authorComm"))) {
					WHERE("authorComm LIKE CONCAT ('%',#{authorComm},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("paperType"))) {
					WHERE("paperType LIKE CONCAT ('%',#{paperType},'%')");
				}
				// indexType 的多条件查询
				if(params.get("indexType") != null) {
					List<String> indexTypeList = (List<String>) params.get("indexType");
					String indexTypeSql = "indexType LIKE CONCAT('%','" + indexTypeList.get(0) + "','%')";
					for (int i = 1; i < indexTypeList.size(); i++) {
						indexTypeSql += " OR indexType LIKE CONCAT ('%','" + indexTypeList.get(i) + "','%')";
					}
					WHERE(indexTypeSql);
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("journal"))) {
					WHERE("jaurnal LIKE CONCAT ('%',#{journal},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("vol"))) {
					WHERE("vol LIKE CONCAT ('%',#{vol},'%')");
				}
				
				if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(publishTime) between " + params.get("startDate_s") + " AND " + params.get("startDate_e"));
				} else if(!StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(publishTime) >= " + params.get("startDate_s"));
				} else if(StringUtil.isNullOrEmpty((String)params.get("startDate_s")) 
						&& !StringUtil.isNullOrEmpty((String)params.get("startDate_e"))) {
					WHERE("year(publishTime) <= " + params.get("startDate_e"));
				}
	
			}
		}.toString();
		
		if(params.get("pageModel") != null){
			sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
		}
		
		return sql;
	}
}
