package com.asynclife.hello.risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.Assert;

public class Risk implements Comparable<Risk> {

	public String level;

	public String description;

	public List<Formular> formulars = new ArrayList<Formular>();
	
	private List<Filter> filterQueue = new LinkedList<Filter>();
	
	public Risk(String level, String description, List<Formular> formulars) {
		this.level = level;
		this.description = description;
		this.formulars = formulars;
		validate();
	}

	private void validate() {
		Assert.notNull(this.level, "level can't be null");
		Assert.notNull(this.formulars, "formulars can't be null");
	}

	void initFilters() {
		Collections.sort(formulars);
		for(Formular f : formulars) {
			filterQueue.add(new BshFilter(f));
		}
	}


	@Override
	public int compareTo(Risk o) {
		return this.level.compareTo(o.level);
	}

	public boolean check(Object[] args) {
		boolean match = false;
		for(Filter f : filterQueue) {
			match = f.eval(args);
			if(match) break;
		}
		return match;
	}

	@Override
	public String toString() {
		return "Risk [level=" + level + ", description=" + description
				+ ", filterQueue=" + filterQueue + "]";
	}

}