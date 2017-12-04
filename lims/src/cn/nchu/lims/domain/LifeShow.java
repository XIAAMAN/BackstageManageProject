package cn.nchu.lims.domain;

import java.io.Serializable;

public class LifeShow implements Serializable {
	
	private static final long serialVersionUID = -4864660393111948822L;
	
	private int id;
	private String fileName;
	private String type;
	
	public LifeShow() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id " + id + " fileName " + fileName + " type " + type;
	}
}
