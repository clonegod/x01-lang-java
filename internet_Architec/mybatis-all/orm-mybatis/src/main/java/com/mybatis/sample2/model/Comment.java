package com.mybatis.sample2.model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2519352771880970318L;
	private int id;
	private String commentText;
	private Date createTime;
	
	private Post post;
	
	
	public Comment() {
		super();
	}
	public Comment(String commentText) {
		super();
		this.commentText = commentText;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
