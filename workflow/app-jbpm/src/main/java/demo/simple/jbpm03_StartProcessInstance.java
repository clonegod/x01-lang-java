package demo.simple;


import org.jbpm.api.ProcessInstance;
import org.junit.Test;

import app.jbpm.manager.JBPM4Manager;

public class jbpm03_StartProcessInstance {
	/**
	 * 启动流程实例ProcessInstance---自动流转到第一个环节
	 * 当提交业务时启动流程实例(创建流程实例对象)
	 * 一个流程实例对应一个/零个Activity(活动环节)
	 * ProcessInstance属性
	 * 	key---与业务对象之间建立关联，即key的取值为业务对象(请假单)的id---> 业务对象通过此字段绑定到JBPM流程实例上
	 *  id---流程定义(ProcessDefinition)的key.流程实例(ProcessInstance)的key
	 *  	流程定义(ProcessDefinition)的key--确定了流转规则
	 *  	流程实例(ProcessInstance)的key--确定了业务对象
	 */
	
	/**
	 * 
	 *开启流程实例---可以通过流程定义的Id或Key开启流程实例
	 *(如果需要按旧的流程定义进行执行，则按id开启流程实例)
	 *(一般通过key开启，会自动选择version最大的流程定义进行执行)
	 *1.指定对应的流程定义的key
	 *2.指定对应的业务对象的id
	 *3.指定流转过程中需要的参数 封装到Map集合中(条件判断时用)
	 *无参数的即不需要条件判断的(自动取version最大即最新的流程定义)
	 *startProcessInstanceByKey(ProcessDefinition_Key, ProcessInstance_Key)
	 *带参数的即需要为条件判断时提供数据的(自动取version最大即最新的流程定义)
	 *startProcessInstanceByKey(ProcessDefinition_Key, Map<String,?> variable, ProcessInstance_Key)
	 */
	@Test
	public void startProcessInstance() {
		//流程定义的key为"Leave",ProcessDefinition_Key="Leave"
		//请假单的id为1,ProcessInstance_Key=Business_id=1
		//只能开启一次，如果再次开启，则对应的ProcessInstance_Key应该为另一个Business_id
		
		// 通过KEY启动，第2次则报错：Duplicate entry 'LEAVE.100' for key 'ID_'
		
		Long formID = 1001L;
		
		ProcessInstance pi =
				JBPM4Manager.getInstance().startProcess("LEAVE", null, formID.toString());
		
		System.out.println("流程实例已经创建完毕,id="+pi.getId());
	}
	
}
