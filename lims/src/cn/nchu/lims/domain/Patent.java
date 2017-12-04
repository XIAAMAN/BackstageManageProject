package cn.nchu.lims.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Patent implements Serializable {
	
	/**
	 * version 1.0
	 * @author Boking
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String author;
	private String authorAll;
	private String patentType;
	private String status;
	private String code;
	private Date publishTime;
	private String processingTime;
	private String abstracts;
	private List<Enclosure> enclosures;
	
	public Patent() {
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorAll() {
		return authorAll;
	}

	public void setAuthorAll(String authorAll) {
		this.authorAll = authorAll;
	}

	public String getPatentType() {
		return patentType;
	}

	public void setPatentType(String patentType) {
		this.patentType = patentType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public String getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(String processingTime) {
		this.processingTime = processingTime;
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
		return "id " + id + " name " + name +  " author " + author 
				+ " authorAll " + authorAll + " patentType " + patentType 
				+ " status " + status + " publishTime " + publishTime
				+ " processingTime " + processingTime +  " abstracts " + abstracts ;   
	}

}
