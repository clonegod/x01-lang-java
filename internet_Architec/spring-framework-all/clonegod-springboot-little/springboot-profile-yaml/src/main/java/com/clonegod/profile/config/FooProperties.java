package com.clonegod.profile.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("foo")
public class FooProperties {

    private final List<MyPojo> list = new ArrayList<>();

    public List<MyPojo> getList() {
        return this.list;
    }
    
    public static class MyPojo {
    	private String name;
    	private String description;
    	
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    	public String getDescription() {
    		return description;
    	}
    	public void setDescription(String description) {
    		this.description = description;
    	}
    	
    	@Override
    	public String toString() {
    		return "MyPojo [name=" + name + ", description=" + description + "]";
    	}
    	
    }


}