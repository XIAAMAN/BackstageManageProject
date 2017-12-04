package cn.nchu.lims.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Paper implements Serializable {

	/**
	 * version 1.0
	 * @author Boking
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String author;
	private String authorComm;
	private String authorAll;
	private String paperType;
	private String indexType;
	private Date publishTime; 
	private String journal;  // ÆÚ¿¯Ãû³Æ
	private String vol;  // ¾íÊý
	private String abstracts;
	private List<Enclosure> enclosures;
	
	public Paper() {
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

	public String getAuthorComm() {
		return authorComm;
	}

	public void setAuthorComm(String authorComm) {
		this.authorComm = authorComm;
	}

	public String getAuthorAll() {
		return authorAll;
	}

	public void setAuthorAll(String authorAll) {
		this.authorAll = authorAll;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getVol() {
		return vol;
	}

	public void setVol(String vol) {
		this.vol = vol;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "id " + id + " name " + name + " author " + author 
				+ " paperType " + paperType + " indexType " + indexType  
				+ " publishTime " + publishTime + " abstract " + abstracts;
	}
}
