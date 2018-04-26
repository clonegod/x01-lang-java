package com.asynclife.jbpm;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.processengine.ProcessEngineImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JBPM_EventListener02_Test {
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
		newDeploy.addResourceFromClasspath("event02.jpdl.xml")
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
		
		String processDefinitionKey = "event02"; // 流程定义的key
		String processInstanceKey = "1"; // 设置为业务对象的id-贷款申请对应业务对象的数据库id
		
		ProcessInstance processInstance = engine.getExecutionService()
				.startProcessInstanceByKey(processDefinitionKey, processInstanceKey);
		
		String processInstanceId = processInstance.getId();
		
		// 流程实例id生成规则： 流程定义的key.流程实例的key
		logger.info("流程启动成功，流程实例id={}", processInstanceId);
	}
	
	
	/**
	 * 查询我的任务，完成任务，提交给下一个人审批 - LEADER审批
	 */
	@Test
	public void test05_FindAndCompleteMyTask() {
		// 获取流程引擎对象
		ProcessEngineImpl engine = (ProcessEngineImpl) new Configuration()
				.setResource("jbpm.cfg.xml").buildProcessEngine();
		
		
		String assignee = "LEADER";
		
		List<Task> tasks = engine.getTaskService().findPersonalTasks(assignee);
		
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task task = iterator.next();
			
			// 流程实例的id(流程定义中没有fork-join的情况下，可以将executionId作为processInstanceId)
			String processInstanceId = task.getExecutionId();
			
			// 根据流程实例id获取流程实例对象
			ProcessInstance pi = engine.getExecutionService().findProcessInstanceById(processInstanceId);
			
			// 根据流程实例对象获取流程实例的key
			String key = pi.getKey();
			
			logger.info("待审批业务对象id={}", key);
			
			String taskId = task.getId();
			
			// 将outcomes显示在页面上，由审批者选择
			Set<String> outcomes = engine.getTaskService().getOutcomes(taskId);
			
			
			// 模拟选择第一个outcome
			String next = outcomes.iterator().next();
			
			/**
			 * 完成任务：
			 * 	1. 删除Task对象，更新数据到历史Task
			 *  2. 触发流程按照指定的Transition往下流转
			 */
			engine.getTaskService().completeTask(taskId, next);
		}
		
	}
}
