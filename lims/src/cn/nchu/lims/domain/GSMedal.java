package cn.nchu.lims.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GSMedal implements Serializable {

	/**
	 * version 1.0
	 * @author Boking
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private Teacher teacher;
	private String level;
	private String grade;
	private String studentAll;
	private Date winningTime;
	private String abstracts;
	private List<Enclosure> enclosures;
	
	public GSMedal() {
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStudentAll() {
		return studentAll;
	}

	public void setStudentAll(String studentAll) {
		this.studentAll = studentAll;
	}

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public List<Enclosure> getEnclosures() {
		return enclosures;
	}

	public void setEnclosures(List<Enclosure> enclosures) {
		this.enclosures = enclosures;
	}

	@Override
	public String toString() {
		return "id " + id + " name " + name + " studentAll " + studentAll 
				+ " level " + level + " grade " + grade
				+ " winningTime " + winningTime + " abstract " + abstracts;
	}
	
}
