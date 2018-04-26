package com.mybatis.sample1.model;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 
 */
// 注：别名不区分大小写，如果没有配置Alias，则默认使用类名。
@Alias("USER")
public class User implements Serializable {

	private static final long serialVersionUID = -2969002634491206520L;

	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private List<UserVisit> visitList;
	private Gender gender;

	public User() {
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(Integer id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<UserVisit> getVisitList() {
		return visitList;
	}

	public void setVisitList(List<UserVisit> visitList) {
		this.visitList = visitList;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", visitList=" + visitList + ", gender=" + gender + "]";
	}
	
}
