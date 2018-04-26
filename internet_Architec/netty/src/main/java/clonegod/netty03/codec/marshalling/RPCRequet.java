package clonegod.netty03.codec.marshalling;

import java.io.Serializable;

public class RPCRequet implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id ;
	private String content ;
	private byte[] attachment;
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
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	@Override
	public String toString() {
		return "RPCRequet [id=" + id + ", content=" + content + "]";
	}
	
}
