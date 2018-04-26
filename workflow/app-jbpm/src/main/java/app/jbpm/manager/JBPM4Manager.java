package app.jbpm.manager;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.Configuration;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.ProcessInstanceQuery;
import org.jbpm.api.task.Task;

import com.asynclife.util.DateTimeHelper;
import com.asynclife.util.FileHelper;

public class JBPM4Manager {
	private static final JBPM4Manager INSTANCE = new JBPM4Manager();
	private JBPM4Manager() {
		initProcessEngine();
	}
	public static JBPM4Manager getInstance() {
		return  INSTANCE;
	}
	
	private static final String JBPM4_DEFAULT_CONFIG_FILE = "jbpm.cfg.xml";
	
	ProcessEngine engine;
	
	public ProcessEngine getEngine() {
		return engine;
	}
	
	// ###########################################################################
	// 							部署流程定义文件
	// ###########################################################################
	/**
	 * 实例化流程引擎
	 * @param jbpmCfgFileName
	 * @return
	 */
	private JBPM4Manager initProcessEngine() {
		return this.createProcessEngine(JBPM4_DEFAULT_CONFIG_FILE);
	}
	
	private JBPM4Manager createProcessEngine(String jbpmCfgFileName) {
		InputStream is = FileHelper.getInputStreamFromClasspath(jbpmCfgFileName);
		engine = new Configuration().setInputStream(is).buildProcessEngine();
		return this;
	}
	
	/**
	 * 部署设计好的某个流程定义文件到数据库
	 * @param processDefFileName 流程定义的文件名称
	 * @return
	 */
	public boolean deployProcess(String processDefFileName) {
		
		return deployProcess(processDefFileName, processDefFileName);
		
	}
	
	/**
	 * 部署设计好的某个流程定义文件到数据库
	 * @param processDefFileName 流程定义的文件名称
	 * @param processName 流程定义的名称
	 * @return
	 */
	public boolean deployProcess(String processDefFileName, String processName) {
		boolean isSucess = true;
		try {
			engine.getRepositoryService()
			.createDeployment()
			.addResourceFromClasspath(processDefFileName)
			.setName(processName) // 将流程定义文件名存入流程定义表jbpm4_deployment中,将来可以读取出来作其它用途
			.setTimestamp(DateTimeHelper.now())
			.deploy();
		} catch(Exception e) {
			isSucess = false;
			e.printStackTrace();
		}
		return isSucess;
	}
	
	// ###########################################################################
	// 								查询数据库存储的流程定义相关信息
	// ###########################################################################
	
	/**
	 * 可根据Name,Key,id等查询流程定义
	 * @return
	 */
	public ProcessDefinitionQuery getProcessDefinitionQuery() {
		ProcessDefinitionQuery query = engine.getRepositoryService().createProcessDefinitionQuery();
		return query;
	}
	
	// ###########################################################################
	// 								启动流程
	// ###########################################################################
	
	/**
	 * 启动某个流程
	 * 
	 * @param processDefinitionKey 流程定义的KEY
	 * @param variables	流程定义中decision节点需要的判断条件
	 * @param processInstanceKey	流程实例的Key---一般将业务对象的ID绑定到此字段上---businessID
	 * @return
	 */
	// jbpm4_execution	流程执行相关 : 通过PROCDEFID_ 关联jbpm4_deployprop表，KEY_ 保存业务对象的ID(认为约定此字段存放业务对象的ID) 
	// jbpm4_task		当前任务，通过EXECUTION_ID_ 关联jbpm4_execution表
	
	public ProcessInstance startProcess(String processDefinitionKey, Map<String,?> variables, String processInstanceKey) {
		ProcessInstance pi = null;
		try {
			pi = engine.getExecutionService()
					.startProcessInstanceByKey(processDefinitionKey, variables, processInstanceKey);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	// ###########################################################################
	// 								查询流程实例对象
	// ###########################################################################
	public ProcessInstance findProcessInstanceById(String processInstanceId) {
		return engine.getExecutionService().findProcessInstanceById(processInstanceId);
		
	}
	
	public Set<String> findActiveActivityNames(String processInstanceId) {
		return findProcessInstanceById(processInstanceId).findActiveActivityNames();
		
	}
	
	public ProcessInstanceQuery createProcessInstanceQuery() {
		return engine.getExecutionService().createProcessInstanceQuery();
	}
	
	
	public Execution findExecutionById(String executionId) {
		return engine.getExecutionService().findExecutionById(executionId);
		
	}
	
	// ###########################################################################
	// 								查询我的代办任务
	// ###########################################################################
	public List<Task> findMyTasks(String assignee) {
		return engine.getTaskService().findPersonalTasks(assignee);
	}
	
	// ###########################################################################
	// 								完成我的代办任务
	// ###########################################################################
	/**
	 * 查询当前节点所有可以离开的线，页面上提供给供客户选择---选择下一步提交给谁？
	 * @param taskId
	 * @return
	 */
	public Set<String> findOutcomes(String taskId) {
		return engine.getTaskService().getOutcomes(taskId);
	}
	
	public void completeTask(String taskId, String nextTransitionName) {
		engine.getTaskService().completeTask(taskId, nextTransitionName);
	}
}
