package cn.nchu.lims.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Medal implements Serializable {

	/**
	 * version 1.0
	 * @author Boking
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Teacher teacher;
	private String name;
	private String level;
	private String type;
	private String grade;
	private int ranking;
	private Date winningTime;
	private String sponsor;
	private String abstracts;
	private String photo;
	
	public Medal() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAbstracts() {
		return abstracts;
	}

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "id " + id + " name " + name + " level " 
			+ level + " type " + type  + " grade " + grade + " ranking "  + ranking
			+ " winningTime " +  winningTime + "sponsor" + sponsor + " abstracts " 
			+ abstracts + " photo " + photo;
	}
}
