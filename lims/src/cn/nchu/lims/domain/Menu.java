package cn.nchu.lims.domain;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
public class Menu implements Serializable {

	private int id;
	private String name;
	private String code;
	private String url;
	private int parentId;
	// OneToMany，One 管理关系。声明属性用来得到子菜单
	private Set<Menu> menus;
	private String icon;
	private int seq;
	private int state;
	
	public Menu() {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> sons) {
		this.menus = sons;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getState() {
		return state;
	}

	public void setState(int staet) {
		this.state = staet;
	}
	
	@Override
	public String toString() {
		return "ID " + this.id + " name " + this.name + " URL " + this.url
				+ " menus " + this.menus.size() + " icon " + this.icon
				+ " seq " + this.seq + " state " + this.state;
	}

}
