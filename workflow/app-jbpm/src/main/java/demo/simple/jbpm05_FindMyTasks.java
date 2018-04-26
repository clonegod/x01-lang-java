package demo.simple;



import java.util.Iterator;
import java.util.List;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;

import app.jbpm.manager.JBPM4Manager;


public class jbpm05_FindMyTasks {
	/**
	 * 查询某个人的待办任务信息
	 */
	@Test
	public void findMyTasks01() {
		String assignee = "张三";
		
		List<Task> tasks = JBPM4Manager.getInstance().findMyTasks(assignee);
		
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			String taskId = task.getId();
			String activityName = task.getActivityName();
			//获取流程实例id
			String processInstanceId =  task.getExecutionId();
			//获取流程实例
			ProcessInstance pi =
				JBPM4Manager.getInstance().findProcessInstanceById(processInstanceId);
			//获取业务对象的id---processKey==businessId
			String processKey = pi.getKey();
			//通过业务对象id查询对应的业务
			//.....
			System.out.println("任务Id:"+taskId+",Name:"+activityName);
			System.out.println("当前流转到【"+assignee+"】,业务对象id为:"+processKey);
		}
	}
	
	@Test
	public void findMyTasks02() {
		
		String assignee = "李四";
		
		List<Task> tasks = JBPM4Manager.getInstance().findMyTasks(assignee);
		
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			String taskId = task.getId();
			String activityName = task.getActivityName();
			//获取流程实例id
			String processInstanceId =  task.getExecutionId();
			//获取流程实例
			ProcessInstance pi =
					JBPM4Manager.getInstance().findProcessInstanceById(processInstanceId);
			//获取业务对象的id---processKey==businessId
			String processKey = pi.getKey();
			//通过业务对象id查询对应的业务
			//.....
			System.out.println("任务Id:"+taskId+",Name:"+activityName);
			System.out.println("当前流转到【"+assignee+"】,业务对象id为:"+processKey);
		}
	}
	
	
}
