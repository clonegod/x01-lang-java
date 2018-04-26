package com.mybatis.sample2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客与标签是N-N的关系: 
 *  Post * <---> * Tag
 *  
 *	拆分为2个1对多:
 *	PostTag * <---> 1 Post
 *	PostTag * <---> 1 Tag
 */
public class PostTag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6636659989676543408L;
	private int id;
	private Post post;
	private Tag tag;
	private Date createTime;
	
	public PostTag(Post post, Tag tag) {
		super();
		this.post = post;
		this.tag = tag;
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
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}  
	
	
}
