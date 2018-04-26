package com.mybatis.sample2.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2578141650404196503L;
	private int id;
	private String name;
	private Date createTime;
	
	private List<Post> posts;
	
	public Tag() {
		super();
	}
	public Tag(String name) {
		super();
		this.name = name;
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
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
