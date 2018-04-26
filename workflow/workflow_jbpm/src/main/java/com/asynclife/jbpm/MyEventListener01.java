package com.asynclife.jbpm;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyEventListener01 implements EventListener {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String email; //该字段值会在JPDL流程定义中通过field进行注入
	
	/**
	 * 监听某个特定事件，当该事件发生后，调用该方法执行某个功能
	 */
	@Override
	public void notify(EventListenerExecution execution) throws Exception {
		logger.info("发送邮件通知：{}", email);
	}

}
