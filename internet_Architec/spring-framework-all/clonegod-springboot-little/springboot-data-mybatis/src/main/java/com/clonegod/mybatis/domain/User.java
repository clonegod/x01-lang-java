package com.clonegod.mybatis.domain;

import java.util.Date;

public class User {
	private int id;
	private String name;
	private int age;
	private Date createTime;
	
	public User() {
		super();
	}

	public User(String name, int age, Date createTime) {
		super();
		this.name = name;
		this.age = age;
		this.createTime = createTime;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", createTime=" + createTime + "]";
	}
	
}
