package cn.nchu.lims.util;

import cn.nchu.lims.domain.Enclosure;

/**
 * 用于附件数据库访问的管理
 * 附件都通过一个结果和一个Dao层处理数据
 * @author Boking
 * @param enclosure : Enclosure 各附件所属主体的Enclosure（附件）类
 * @Param database : String s所访问的 附件所属的数据库名
 */
public class EnclosureREUDModel {

	private Enclosure enclosure;
	private String table;
	private String forginKeyName;
	
	public EnclosureREUDModel() {
		super();
	}
	
	public EnclosureREUDModel(Enclosure enclosure, String table) {
		super();
		this.enclosure = enclosure;
		this.table = table;
	}
	
	public EnclosureREUDModel(Enclosure enclosure, String table, String foforginKeyNamerg) {
		super();
		this.enclosure = enclosure;
		this.table = table;
		this.forginKeyName = foforginKeyNamerg;
	}
	
	public Enclosure getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(Enclosure enclosure) {
		this.enclosure = enclosure;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getForginKeyName() {
		return forginKeyName;
	}

	public void setForginKeyName(String forginKeyName) {
		this.forginKeyName = forginKeyName;
	}

}
