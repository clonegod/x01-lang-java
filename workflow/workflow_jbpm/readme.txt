【JBPM工作流引擎】
创建审批任务->审批者审批并提交到下一个审批者(任务流转)->审批结束(通过/拒绝)


用户 User： 申请人、审批者
申请事项 LoanRequest: 贷款申请
审批信息 ApproveInfo: 通过/拒绝


1.定义流程(status=START) - 创建新的流程，进行流程定义
	创建者：具体某个人
	申请者：具体某个人
	
2.提交任务(status=PROCESSING) - 执行流程，建立待审关联，开始任务的流转
	审批者:具体某个人，某个角色，某个岗位，某个部门. 参与者-assignee
	联合审批：fork-join
	JPDL：通过条件来决定流转规则
		
3.流程结束(status=END) - 审批完成


===============================================================
【流程定义】
	1个流程定义可以进行多次部署，每次递增，按版本号进行区分
	1个流程定义，可能会有多个流程实例对象
	流程定义的ID生成规则： 流程定义的KEY-流程定义的VERSION
	
【流程实例】
	流程实例的ID生成规则：流程定义的KEY.流程实例的KEY（流程实例的KEY一般都设置为业务对象的ID，这样就能将工作流与业务审批对象关联起来）
	1个流程实例，可能会有多个流转状态，即有多个Task会与其关联
	当该流程实例下的所有任务都完成后，该记录会从jbpm4_execution表中删除。相关数据会同步到jbpm4_hist_procinst表中。

【任务】
	每个Task通过EXECUTION_ID_与流程实例的ID_进行关联，或者通过PROCINST_和EXECUTION_与流程实例的DBID_进行关联。
	每个Task在完成后，都会从jbpm4_task表中删除。相关数据会同步更新到jbpm4_hist_task表中。
	

===============================================================
jbpm4_deployment	流程部署信息:流程名称，部署时间，流程状态(active)
jbpm4_deployprop	流程部署对应的明细：langid,key,version,pdid
jbpm4_lob 存储流程定义的二进制文件

jbpm4_execution	流程实例
jbpm4_task	流转任务

jbpm4_hist_actinst	历史流转信息，主要记录Transition
jbpm4_hist_detail
jbpm4_hist_procinst 历史流程实例
jbpm4_hist_task	历史任务，主要记录审批人，审批开始时间，审批结束时间，任务状态等




==================================================
Execution 与 ProcessInstance的关系
	public interface ProcessInstance extends Execution {}
	ProcessInstance 是 Execution的子类！
	

1. execution表达某个任务的执行，task与execution绑定的。
2. processInstance表示某个流程实例，1个流程实例在某个时刻可能会关联多个Execution。
	当流程定义中某个节点是fork-join的时候，会出现subExecution. ProcessInstance将作为"根Execution"。
	也就是1个ProcessInstance对应了多个subExecution, 此时就不能将ExecutionId作为ProcessInstanceId来使用了。

简单流程(1个ProcessInstance对应1个Execution)：
ProcessInstance
	Execution

会签流程(1个ProcessInstance对应多个subExecution)：
ProcessInstance
	SubExecution1
	SubExecution2
	...

错误的 用法：
String processInstanceId = task.getExecutionId(); // error: 在fork-join节点上，executionId不等于procInstId
ProcessInstance pi = engine.getExecutionService().findProcessInstanceById(processInstanceId);

===>

正确的用法：
ProcessInstance pi = (ProcessInstance) engine.getExecutionService().findExecutionById(task.getExecutionId())
					.getProcessInstance();

