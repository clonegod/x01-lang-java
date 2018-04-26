package com.asynclife.hello.risk;

public class Formular implements Comparable<Formular>{
	
	String sequence;
	String property;
	String expression;
	
	public Formular(String sequence, String property, String expression) {
		super();
		this.sequence = sequence;
		this.property = property;
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "Formular [sequence=" + sequence + ", property=" + property
				+ ", expression=" + expression + "]";
	}

	@Override
	public int compareTo(Formular o) {
		return this.sequence.compareTo(o.sequence);
	}

	
	
}
