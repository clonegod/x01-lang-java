package com.clonegod.jdbc.model;

public class OrderExt {
	private int orderId;
	private int userType;
	private String comment;
	
	public OrderExt() {
		super();
	}
	public OrderExt(int orderId, int userType, String comment) {
		super();
		this.orderId = orderId;
		this.userType = userType;
		this.comment = comment;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
