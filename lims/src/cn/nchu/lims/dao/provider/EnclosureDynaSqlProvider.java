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
				INSERT_INTO(edmm.getTable());  // ����EnclosureDatabaseManageModel.database �õ�����
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
	
	/**Ϣ
	 * @param edmm : EnclosureData
	 * ���ݲ�����̬ɾ�����ݿ�Enclosure��baseManageModel 
	 *   ����Enclosure��ɾ�������ͱ���
	 * @return String
	 */
	public String deleteDyna(final EnclosureREUDModel edmm) {
		return new SQL() {
			{
				DELETE_FROM(edmm.getTable());  // ����EnclosureDatabaseManageModel��database �õ�����
				Enclosure enclosure = edmm.getEnclosure();
				if(!IntegerUtil.isNullOrZero(enclosure.getId())) {
					WHERE("ID = #{enclosure.id}");
				}
			}
		}.toString();
	}
	
	/**
	 * ���ݲ�����̬����Enclosure��Ϣ
	 * @param enclosure : Enclosure
	 * @return sql��� : String
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
	 * ���ݲ�����̬��ѯ���ݿ�Enclosure��Ϣ
	 * @param map : Map 
	 *   ����Enclosure��table������id
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
	 * ���ݲ�����̬��ѯ���ݿ�Enclosure��Ϣ
	 * @param edmm : EnclosureDatabaseManageModel 
	 *   ����Enclosure�Ĳ�ѯ�����ͱ���
	 * @return String
	 */
	public String getDyna(final EnclosureREUDModel edmm) {
		return new SQL() {
			{
				SELECT("*");
				FROM(edmm.getTable());  // ����EnclosureDatabaseManageModel.database �õ�����
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
