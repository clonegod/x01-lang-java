package com.mybatis.sample1.model;

import java.io.Serializable;
import java.util.Date;

/**
 *	读者用户的访问信息 
 */
public class UserVisit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8895068729542386414L;
	
	private int id;
	private int userId;
	private Date visitDate;
	private String visitIp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getVisitIp() {
		return visitIp;
	}

	public void setVisitIp(String visitIp) {
		this.visitIp = visitIp;
	}

	@Override
	public String toString() {
		return "UserVisit [id=" + id + ", userId=" + userId + ", visitDate=" + visitDate + ", visitIp=" + visitIp + "]";
	}

}
