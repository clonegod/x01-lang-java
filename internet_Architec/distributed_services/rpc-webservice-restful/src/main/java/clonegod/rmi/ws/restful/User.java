package clonegod.rmi.ws.restful;

import java.util.Date;

//import javax.xml.bind.annotation.XmlTransient;

public class User {
	private int id;
	
	private String name;
	
	//@XmlTransient
	private Date birth;
	
	public User() {
		super();
	}

	public User(int id, String name, Date birth) {
		super();
		this.id = id;
		this.name = name;
		this.birth = birth;
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
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
}
