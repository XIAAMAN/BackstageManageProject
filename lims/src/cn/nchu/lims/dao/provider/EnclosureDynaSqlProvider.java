package cn.nchu.lims.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import cn.nchu.lims.domain.Enclosure;
import cn.nchu.lims.util.EnclosureREUDModel;
import cn.nchu.lims.util.lang.IntegerUtil;
import cn.nchu.lims.util.lang.StringUtil;

public class EnclosureDynaSqlProvider {

	public String saveDyna(final EnclosureREUDModel edmm) {
		return new SQL() {
			{
				INSERT_INTO(edmm.getTable());  // 根据EnclosureDatabaseManageModel.database 得到表明
				Enclosure enclosure = edmm.getEnclosure();
				if(!StringUtil.isNullOrEmpty(enclosure.getFileName())) {
					VALUES("fileName", "#{enclosure.fileName}");
				}				
				if(!StringUtil.isNullOrEmpty(enclosure.getOldName())) {
					VALUES("oldName", "#{enclosure.oldName}");
				}
					
				VALUES("size", "#{enclosure.size}");
				
			}
		}.toString();
	}
	
	/**息
	 * @param edmm : EnclosureData
	 * 根据参数动态删除数据库Enclosure信baseManageModel 
	 *   包含Enclosure的删除条件和表名
	 * @return String
	 */
	public String deleteDyna(final EnclosureREUDModel edmm) {
		return new SQL() {
			{
				DELETE_FROM(edmm.getTable());  // 根据EnclosureDatabaseManageModel。database 得到表明
				Enclosure enclosure = edmm.getEnclosure();
				if(!IntegerUtil.isNullOrZero(enclosure.getId())) {
					WHERE("ID = #{enclosure.id}");
				}
			}
		}.toString();
	}
	
	/**
	 * 根据参数动态更新Enclosure信息
	 * @param enclosure : Enclosure
	 * @return sql语句 : String
	 * @throws Exception 
	 */
	public String updateDyna(final EnclosureREUDModel edmm) throws Exception {
		return new SQL() {
			{
				UPDATE(edmm.getTable());
				Enclosure enclosure = edmm.getEnclosure();
				if(!IntegerUtil.isNullOrZero((enclosure.getForginKey()))) {
					SET(edmm.getForginKeyName() + " = #{enclosure.forginKey}"); 
				}
				if(!StringUtil.isNullOrEmpty(enclosure.getFileName())) {
					SET("fileName = #{enclosure.fileName}");
				}
				if(!StringUtil.isNullOrEmpty(enclosure.getOldName())) {
					SET("oldName = #{enclosure.oldName}");
				}
				SET("ID = #{enclosure.id}");
				WHERE("ID = #{enclosure.id}");
			}
		}.toString();
	}
	
	/**
	 * 根据参数动态查询数据库Enclosure信息
	 * @param map : Map 
	 *   包含Enclosure的table条件和id
	 * @return String
	 */
	public String get(final Map<String, String> map) {
		return new SQL() {
			{
				SELECT("*");
				FROM((String) map.get("table"));  
				if(!StringUtil.isNullOrEmpty(map.get("id"))) {
					WHERE("ID = #{id}");
				} 
			}
		}.toString();
	}
	
	/**
	 * 根据参数动态查询数据库Enclosure信息
	 * @param edmm : EnclosureDatabaseManageModel 
	 *   包含Enclosure的查询条件和表名
	 * @return String
	 */
	public String getDyna(final EnclosureREUDModel edmm) {
		return new SQL() {
			{
				SELECT("*");
				FROM(edmm.getTable());  // 根据EnclosureDatabaseManageModel.database 得到表明
				Enclosure enclosure = edmm.getEnclosure();
				if(!IntegerUtil.isNullOrZero(enclosure.getId())) {
					WHERE("ID = #{enclosure.id}");
				} else {
					if(!StringUtil.isNullOrEmpty(enclosure.getFileName())) {
						WHERE("fileName = #{enclosure.fileName}");
					}
					if(!StringUtil.isNullOrEmpty(enclosure.getOldName())) {
						WHERE("oldName = #{enclosure.oldName}");
					}
				}
			}
		}.toString();
	}
	
}
