package cn.nchu.lims.domain;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("serial")
public class Admin  implements Serializable {
	
	private int id;	
	private String instituteName;
	private String userName;
	private String nickName;
	private int instituteId;
	private String password;
	private String phone;
	private String email;
	private Date lastLogin;
	private int state;
	
	public Admin() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteld) {
		this.instituteId = instituteld;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "id:" + this.id+ " userName:" + this.userName + " nickName:" + this.nickName + " instituteId:" + this.instituteId + " password:" + this.password 
				+ " phone:" + this.phone + " email:" + this.email + " lastLogin:" + this.lastLogin + " state:" + this.state;
	}
}
