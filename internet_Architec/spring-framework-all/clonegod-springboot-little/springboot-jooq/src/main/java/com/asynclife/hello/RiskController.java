package com.asynclife.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asynclife.hello.risk.Customer;
import com.asynclife.hello.risk.Risk;
import com.asynclife.hello.risk.RiskFactory;
import com.asynclife.hello.risk.RiskManager;

@RestController
public class RiskController {
	
	@Autowired
	RiskManager rm;
	
	@RequestMapping("/risk/filter")
    public String execute() {
    	rm.addRisks(RiskFactory.createRisksWithOrderless());
    	rm.evaluation(new Customer());
    	
    	StringBuilder builder = new StringBuilder();
    	for(Risk risk :  rm.getRiskQueue()) {
    		builder.append(risk.toString());
    		builder.append("<br/><hr/><br/>");
    	}
    	
    	return builder.toString();
    }

}
