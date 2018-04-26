package demo.simple;


import java.util.Set;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.ProcessInstanceQuery;
import org.jbpm.api.model.Activity;
import org.jbpm.pvm.internal.client.ClientProcessInstance;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.processengine.ProcessEngineImpl;
import org.junit.Test;

import app.jbpm.manager.JBPM4Manager;

public class jbpm04_FindProcessInstanceStatus {
	/**
	 * 查询流程实例ProcessInstance当前流转到哪个节点
	 * 获取流程实例的当前状态，通过id唯一锁定流程实例对象
	 * 业务对象已确定:id=1
	 * 流程定义已确定：key="Leave"
	 * 则可确定流程实例对应的id=ProcessDefinition_Key.ProcessInstance_Key,即:"Leave.1"
	 * 这样就可以通过id获取到流程实例对象
	 */
	@Test
	public void findProcessInstanceStatus01() {

		//流程实例的id: 流程定义的KEY.业务对象的ID
		String processInstanceId = "Leave.1001";
		
		
		//通过id查找对应的流程实例对象
		ProcessInstance pi = JBPM4Manager.getInstance().findProcessInstanceById(processInstanceId);
		
		//获取流程实例当前流转状态(会签时会有多个查询结果，所以返回值为Set集合)
		Set<String> names = pi.findActiveActivityNames();
		System.out.println(names);
		
	}
	
	@Test
	public void findProcessInstanceStatus02() {
		
		ProcessInstanceQuery query = JBPM4Manager.getInstance().createProcessInstanceQuery();
		
		//设置流程实例的id
		String processInstanceId = "Leave.1001";
		
		//获取流程实例当前流转状态
		ProcessInstance pi = query.processInstanceId(processInstanceId).uniqueResult();
		
		//(会签时会有多个查询结果，所以返回值为Set集合)
		Set<String> names = pi.findActiveActivityNames();
		System.out.println(names);
	}
	
	@Test
	public void findProcessInstanceStatus03() {
		//获取流程部署引擎
		ProcessEngine engine = JBPM4Manager.getInstance().getEngine();
		
		//转换为具体实现类
		ProcessEngineImpl engineImpl = (ProcessEngineImpl)engine;
		
		//保持session有效
		EnvironmentImpl env = engineImpl.openEnvironment();
		
		//设置流程实例的id
		String processInstanceId = "Leave.1001";
		
		ProcessInstance pi = 
			engine.getExecutionService().findProcessInstanceById(processInstanceId);
		
		//转换为具体实现类
		ClientProcessInstance cpi = (ClientProcessInstance)pi;
		
		//调用具体实现类中的getActivity()获取流程节点
		Activity currentNode = cpi.getActivity();
		
		System.out.println(currentNode.getName());
		
		//关闭session
		env.close();
		
	}
	
}
