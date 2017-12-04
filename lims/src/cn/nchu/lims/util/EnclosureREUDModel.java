package cn.nchu.lims.util;

import cn.nchu.lims.domain.Enclosure;

/**
 * ���ڸ������ݿ���ʵĹ���
 * ������ͨ��һ�������һ��Dao�㴦������
 * @author Boking
 * @param enclosure : Enclosure ���������������Enclosure����������
 * @Param database : String s�����ʵ� �������������ݿ���
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
