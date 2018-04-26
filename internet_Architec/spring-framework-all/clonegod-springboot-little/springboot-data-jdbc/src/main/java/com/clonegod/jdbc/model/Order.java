package com.clonegod.jdbc.model;

import java.util.Date;

public class Order {
	private int orderId;
	private short expressType;
	private int userId;
	private Date addTime;
	
	public Order() {
		super();
	}
	public Order(short expressType, int userId, Date addTime) {
		super();
		this.expressType = expressType;
		this.userId = userId;
		this.addTime = addTime;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public short getExpressType() {
		return expressType;
	}
	public void setExpressType(short expressType) {
		this.expressType = expressType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
}
