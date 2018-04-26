package clonegod.dubbox.restful.api;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonProperty;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -95314985977048253L;
	
	@NotNull
	private String id;
	
	@NotNull
	@JsonProperty("username")
	private String name;
	
	@NotNull
    @Size(min = 6, max = 50)
	private String password;
	
	private int age;
	private Date date;
	private String message;
	
	public User() {
		super();
	}

	public User(String id, String name, String password, int age, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.age = age;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
