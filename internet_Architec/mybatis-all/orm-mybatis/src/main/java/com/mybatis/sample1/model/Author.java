package com.mybatis.sample1.model;

import java.io.Serializable;

public class Author implements Serializable {
	
	private static final long serialVersionUID = 3364516154807617563L;
	
	private int id;
	private User user;
	private String realName;
	private String idcard;
	
	public Author() {
	}

	public Author(String realName, String idcard) {
		super();
		this.realName = realName;
		this.idcard = idcard;
	}
	
	public Author(Integer id, String realName, String idcard) {
		super();
		this.id = id;
		this.realName = realName;
		this.idcard = idcard;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", user=" + user + ", realName=" + realName + ", idcard=" + idcard + "]";
	}
	
	
}
