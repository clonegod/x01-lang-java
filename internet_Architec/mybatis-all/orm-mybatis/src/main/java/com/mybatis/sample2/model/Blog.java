package com.mybatis.sample2.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Blog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5470737243596301036L;
	private int id;
	private String title; // 博客标题
	private Date createTime;
	
	private Author author;	// 博客作者
	private List<Post> posts; // 博客中的多篇博文
	
	public Blog() {
		super();
	}
	
	public Blog(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Blog(String title) {
		super();
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
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
