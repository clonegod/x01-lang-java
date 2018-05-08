package com.asynclife.hello.risk;

public class BshFilter implements Filter {

	private Formular formular;

	public BshFilter(Formular formular) {
		super();
		this.formular = formular;
	}

	@Override
	public boolean eval(Object... objects) {
		System.out.println(this.toString() + " filtering " + formular.toString());
		return false;
	}

	@Override
	public String toString() {
		return "BshFilter [formular=" + formular + "]";
	}
	
}
