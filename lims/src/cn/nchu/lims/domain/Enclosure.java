package cn.nchu.lims.domain;

import java.io.Serializable;

public class Enclosure implements Serializable{
	/**
	 * version 1.0
	 * @author Boking
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Integer forginKey;
	private String fileName;
	private String oldName;
	private float size;
	
	public Enclosure() {
		super();
		this.forginKey = null;
	}
	
	public Enclosure(String oldName, String fileName, float size) {
		super();
		this.forginKey = null;
		this.fileName = fileName;
		this.oldName = oldName;
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getForginKey() {
		return forginKey;
	}

	public void setForginKey(Integer forginKey) {
		this.forginKey = forginKey;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "id " + id + " fileName " + fileName + " oldName " + oldName + " size " + size;
	}
}
