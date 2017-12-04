package cn.nchu.lims.dao.provider;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import cn.nchu.lims.domain.Patent;
import cn.nchu.lims.util.Constant;
import cn.nchu.lims.util.lang.StringUtil;

public class PatentDynaSqlProvider {

	/**
	 * 根据patent分装的参数，动态修改数据库信息。
	 *   id 为查询条件
	 * @param patent : Patent
	 * @return String
	 */
	public String updateDyna(final Patent patent) {
		return new SQL() {
			{
				UPDATE(Constant.PATENT_TABLE);
				if(!StringUtil.isNullOrEmpty(patent.getName())) {
					SET("name = #{name}");
				}
				if(!StringUtil.isNullOrEmpty(patent.getAuthor())) {
					SET("author = #{author}");
				}
				if(!StringUtil.isNullOrEmpty(patent.getAuthorAll())) {
					SET("authorAll = #{authorAll}");
				}
				
				if(!StringUtil.isNullOrEmpty(patent.getCode())) {
					SET("code = #{code}");
				}
				
				if(patent.getPublishTime() != null) {
					SET("publishTime = #{publishTime}");
				}
				
				if(StringUtil.isNullOrEmpty(patent.getProcessingTime())) {
					SET("processingTime = #{processingTime}");
				}
				
				if(!StringUtil.isNullOrEmpty(patent.getPatentType())) {
					SET("patentType = #{patentType}");
				}
				if(!StringUtil.isNullOrEmpty(patent.getStatus())) {
					SET("status = #{status}");
				}
				if(!StringUtil.isNullOrEmpty(patent.getAbstracts())) {
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
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(Constant.PATENT_TABLE);
				if(!StringUtil.isNullOrEmpty((String)params.get("name"))) {
					WHERE("name LIKE CONCAT ('%',#{name},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("author"))) {
					WHERE("author LIKE CONCAT ('%',#{author},'%') OR authorAll LIKE CONCAT ('%',#{author},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("patentType"))) {
					WHERE("patentType LIKE CONCAT ('%',#{patentType},'%')");
				}
				if(!StringUtil.isNullOrEmpty((String)params.get("status"))) {
					WHERE("status LIKE CONCAT ('%',#{status},'%')");
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
