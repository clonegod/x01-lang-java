package clonegod.serialize01.jdk.clone;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import clonegod.serialize01.jdk.SerializeUtil;

public class Student implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 41411489791830374L;

	private int stuNo;
	
	private Date createTime;
	
	private Teacher teacher;

	public Student() {
		super();
	}

	public Student(int stuNo, Date createTime, Teacher teacher) {
		super();
		this.stuNo = stuNo;
		this.createTime = createTime;
		this.teacher = teacher;
	}

	public void setStuNo(int stuNo) {
		this.stuNo = stuNo;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Student [stuNo=" + stuNo + ", createTime=" + new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(createTime) + ", teacher=" + teacher + "]";
	}

	/**
	 * 浅复制
	 */
	@Override
	public Object clone() {
		Object copied = null;
		try {
			copied = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copied;
	}
	
	/**
	 * 深复制
	 */
	public Student deepClone() {
		return SerializeUtil.deepClone(this);
	}
	
}
