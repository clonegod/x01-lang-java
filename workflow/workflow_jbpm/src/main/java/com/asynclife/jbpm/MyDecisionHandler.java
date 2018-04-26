package com.asynclife.jbpm;

import java.util.Map;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class MyDecisionHandler implements DecisionHandler {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 根据流程变量，判断流转到哪个节点
	 */
	@Override
	public String decide(OpenExecution execution) {
		
		Map<String, ?> variableMap = execution.getVariables();
		Object loanAmount = variableMap.get("loanAmount");
		
		if(loanAmount == null) {
			throw new NullPointerException("loanAmount is null");
		}
		
		double amount = (Double)loanAmount;
		if(amount < 50000) {
			return "贷款金额小于50000.00";
		}
		
		return "贷款金额大于等于50000.00";
	}

}
