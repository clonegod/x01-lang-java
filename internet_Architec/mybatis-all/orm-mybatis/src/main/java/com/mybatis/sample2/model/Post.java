package com.mybatis.sample2.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7884727918284104205L;
	private int id;
	private String subject;
	private String body;
	private Date createTime;

	private Author author; // 博文作者
	private Blog blog; // 博文所属的博客
	
	private List<Tag> tags;	// 1篇博文有多个Tag
	private List<Comment> comments; // 1篇博文有多个评论
	
	public Post() {
		super();
	}

	public Post(String subject, String body) {
		super();
		this.subject = subject;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
