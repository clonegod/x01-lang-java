package com.asynclife.jbpm;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyEventListener02 implements EventListener {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void notify(EventListenerExecution execution) throws Exception {
		// 1. 获取流程实例的KEY-业务对象ID
		// String key = execution.getProcessInstance().getKey();
		// 2. 根据业务对象ID，查询任务提交者
		// 3. 根据任务提交者，找到他的领导
		
		
		String leader = "LEADER";
		execution.setVariable("leader", leader);
		
		logger.info("设置任务流转中的assignee变量值，leader={}", leader);
	}

}
