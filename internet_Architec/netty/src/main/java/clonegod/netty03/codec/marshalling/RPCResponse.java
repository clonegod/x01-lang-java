package clonegod.netty03.codec.marshalling;

import java.io.Serializable;

public class RPCResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "RPCResponse [id=" + id + ", content=" + content + "]";
	}
	
}
