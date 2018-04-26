package demo.simple;



import java.util.Set;

import org.junit.Test;

import app.jbpm.manager.JBPM4Manager;


public class jbpm06_CompleteMyTasks  {
	/**
	 * 完成待办任务
	 * 流程对象ProcessInstance根据流程定义ProcessDefinition流转到下一个节点
	 */
	@Test
	public void completeMyTasks01() {
		//任务id
		String taskId = "10002";
		/**
		 * completeTask会做两件事：
		 * 1.删除Task对象
		 * 2.触发流程按照指定的transition离开当前节点，到达下一个节点
		 */
		JBPM4Manager.getInstance().completeTask(taskId, "to 李四审批");
	}
	
	/**
	 * 流转到李四，李四完成任务，任务结束
	 */
	@Test
	public void completeMyTasks02() {
		//任务id
		String taskId = JBPM4Manager.getInstance().findMyTasks("李四").get(0).getId();

		//获取当前节点流转到下一个节点的所有线段的名称outcomes
		//因为可能会有多种流转方向，而且方向不能重复，所以返回一个Set集合
		Set<String> names = JBPM4Manager.getInstance().findOutcomes(taskId);
		JBPM4Manager.getInstance().completeTask(taskId,  names.iterator().next());
	}
	
}
