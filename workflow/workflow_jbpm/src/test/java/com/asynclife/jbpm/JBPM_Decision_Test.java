package com.asynclife.jbpm;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JBPM_Decision_Test {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 部署流程定义文件的相关信息到数据库
	 */
	@Test
	public void test01_DeployProcessDefinition() {
		// 获取流程引擎对象
		ProcessEngine engine = new Configuration()
				.setResource("jbpm.cfg.xml").buildProcessEngine();
		
		// 从流程引擎中获取流程部署相关的服务
		RepositoryService repoService = engine.getRepositoryService();
		
		// 获取新的流程定义对象
		NewDeployment newDeploy = repoService.createDeployment();
		newDeploy.addResourceFromClasspath("decision02.jpdl.xml")
			.setName("贷款审批流程")
			.setTimestamp(System.currentTimeMillis())
			.deploy(); // 执行流程部署
		
	}
	
	
	/**
	 * 启动流程定义-触发流程开始执行
	 */
	@Test
	public void test03_StartProcessInstance() {
		// 获取流程引擎对象
		ProcessEngine engine = new Configuration()
				.setResource("jbpm.cfg.xml").buildProcessEngine();
		
		String processDefinitionKey = "decision02"; // 流程定义的key
		String processInstanceKey = "13"; // 设置为业务对象的id-贷款申请对应业务对象的数据库id
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("loanAmount", 12000d);
		
		ProcessInstance processInstance = engine.getExecutionService()
				.startProcessInstanceByKey(processDefinitionKey, variables, processInstanceKey);
		
		String processInstanceId = processInstance.getId();
		
		// 流程实例id生成规则： 流程定义的key.流程实例的key
		logger.info("流程启动成功，流程实例id={}", processInstanceId);
	}
	
}
