package cn.nchu.lims.domain;

import java.io.Serializable;

public class MDStudent implements Serializable {

	/**
	 * version 1.0
	 * @author Boking
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String photo;
	private String name;
	private String type;
	private String major;
	private String college;
	private String researchInterest;
	private String email;
	private String description;
	
	public MDStudent() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getResearchInterest() {
		return researchInterest;
	}

	public void setResearchInterest(String researchInterest) {
		this.researchInterest = researchInterest;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "id " + id + " photo " + photo + " major " + major + " type " + type 
				+ " college " + college + " researchInterest " + researchInterest
				+ " email " + email + " description " + description; 
	}
}
