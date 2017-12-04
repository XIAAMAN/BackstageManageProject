package cn.nchu.lims.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Teacher implements Serializable {

	private int id;
	private String name;
	private String photo;
	private String major;
	private String college;
	private String graduateCollege;
	private String researchInterest;
	private String title;
	private String position;
	private String director;
	private String email;
	private String description;
	private String state;
	
	
	public Teacher() {
		super();
	}

	public int getId() {
		return id;
	}

	// 自定定义反序列化
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getGraduateCollege() {
		return graduateCollege;
	}

	public void setGraduateCollege(String graduateCollege) {
		this.graduateCollege = graduateCollege;
	}

	public String getResearchInterest() {
		return researchInterest;
	}

	public void setResearchInterest(String researchInterest) {
		this.researchInterest = researchInterest;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return this.id + " " + this.major + " " + this.photo + " " + this.college 
				+ " " + this.graduateCollege + " " + this.researchInterest 
				+ " " + this.title + " " + this.position + " state " + this.state
				+ " " + this.director + " " + this.email + " " + this.description;
	}
}
