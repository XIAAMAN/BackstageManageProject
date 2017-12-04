package cn.nchu.lims.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
public class Project implements Serializable {

	private int id;
	private String name;
	// 外键，Project和Teacher是多对一的关系，即一个Project只属于一个Teacher
	private Teacher teacher; 
	private String member;
	private String code;
	private float fund;
	private String source;
	private String level;
	private Date startDate;
	private Date endDate;
	private String description;
	private String file;
	private List<Enclosure> enclosures;
	
	public Project() {
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

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getFund() {
		return fund;
	}

	public void setFund(float fund) {
		this.fund = fund;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public List<Enclosure> getEnclosures() {
		return enclosures;
	}

	public void setEnclosures(List<Enclosure> enclosures) {
		this.enclosures = enclosures;
	}

	@Override
	public String toString() {
		return this.id + " " + this.name + " " + this.teacher.getId() 
				+ " " + this.member + " " + this.code 
				+ " " + this.fund + " " + this.source
				+ " " + this.level + " " + this.startDate
				+ " " + this.endDate + " " + this.description;
	}
	
}
