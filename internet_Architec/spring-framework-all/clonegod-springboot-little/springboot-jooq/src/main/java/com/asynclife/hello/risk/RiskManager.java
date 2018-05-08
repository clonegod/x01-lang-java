package com.asynclife.hello.risk;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RiskManager {
	
	private static RiskManager self;
	private RiskManager() {}
	public static synchronized RiskManager getInstance() {
		if(self == null) {
			self = new RiskManager();
		}
		return self;
	}
	
	
	private List<Risk> riskQueue = new LinkedList<Risk>();
	
	public synchronized List<Risk> getRiskQueue() {
		return new LinkedList<Risk>(riskQueue);
	}
	
	public synchronized RiskManager addRisk(Risk risk) {
		if(!riskQueue.contains(risk)) {
			riskQueue.add(risk);
			Collections.sort(riskQueue);
			risk.initFilters();
		}
		return this;
	}
	
	public synchronized RiskManager addRisks(Collection<Risk> risks) {
		for(Risk risk : risks) {
			addRisk(risk);
		}
		return this;
	}
	
	
	public synchronized void evaluation(Object...args) {
		Assert.notNull(args, "client model can't be null");
		Assert.notEmpty(args, "you must assign at least one client model which used by the risk-controller");
		for(Risk risk : riskQueue) {
			risk.check(args);
		}
	}
	
	

	
}
