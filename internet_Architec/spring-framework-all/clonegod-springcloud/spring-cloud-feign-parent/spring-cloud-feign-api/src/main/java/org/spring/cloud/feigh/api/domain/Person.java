package org.spring.cloud.feigh.api.domain;

import java.io.Serializable;

public class Person implements Serializable {
	
	private long id;
	
	private String name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
