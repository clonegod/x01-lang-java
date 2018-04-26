package clonegod.rmi.ws.restful;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {
	
	private int code;
	
	private String msg;
	
	@XmlElementWrapper(name="list")
	@XmlElement(name="item")
	private List<User> data;
	
	public Response() {
		super();
	}

	public static Response success() {
		Response res = new Response();
		res.setCode(0);
		res.setMsg("success");
		return res;
	}
	
	public static Response fail() {
		Response res = new Response();
		res.setCode(1);
		res.setMsg("failed");
		return res;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<User> getData() {
		return data;
	}

	public void setData(List<User> data) {
		this.data = data;
	}
	public void setData(User data) {
		this.data = Arrays.asList(data);
	}

}
