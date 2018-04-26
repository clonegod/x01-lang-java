package com.asynclife.jooq.demo.pojo;

import java.beans.ConstructorProperties;

public class MyBook2 {
	
	public Long id;
	public long aid;
	public String title;
	public long publishedIn;
	public long lanId;
	
	public MyAuthor author;
	
	@ConstructorProperties({"id","author_id","title","published_in","language_id"})
	public MyBook2(Long id, long aid, String title, long publishedIn, long lanId) {
		super();
		this.id = id;
		this.aid = aid;
		this.title = title;
		this.publishedIn = publishedIn;
		this.lanId = lanId;
	}
	
	@Override
	public String toString() {
		return "MyBook2 [id=" + id + ", aid=" + aid + ", title=" + title
				+ ", publishedIn=" + publishedIn + ", lanId=" + lanId + "]";
	}

}
