package com.mybatis.sample2.model;

import java.io.Serializable;
import java.util.Date;

public class Author implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6175052198520351239L;
	private int id;
	private String username;
	private String password;
	private String email;
	private Gender gender;
	private Date createTime;
	
	
	public Author() {
		super();
	}

	public Author(String username, String password, String email, Gender gender) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
