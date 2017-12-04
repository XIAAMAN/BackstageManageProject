package cn.nchu.lims.domain;

import java.io.Serializable;
import java.sql.Date;

public class Institute implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String leader;
	private Date creatTime;
	
	public Institute() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
}
