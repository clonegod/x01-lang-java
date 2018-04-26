package com.asynclife.jooq.demo.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAuthor {
	public long id;
	public String firstName;
	public String lastName;
	public Date dateOfBirth;
	public int yearOfBirth;
	public boolean distinguished;
	
	public List<MyBook2> books = new ArrayList<MyBook2>(); 
	
}
