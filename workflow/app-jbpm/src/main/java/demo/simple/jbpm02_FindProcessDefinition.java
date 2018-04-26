package demo.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.junit.Test;

import app.jbpm.manager.JBPM4Manager;

public class jbpm02_FindProcessDefinition {
	/**
	 * 查询数据库中已有的流程定义
	 * 通过name查询/模糊查询
	 * 通过key查询
	 * 通过id查询
	 * 不能通过version进行查询，因为不同的流程定义可能存在同一个version值
	 * 
	 * 注意：对于一个流程定义而言，一旦创建，其Name和Key属性不能进行改变
	 * 		Name和Key确定了，不能修改，再次部署流程定义将自动改变该类流程定义的版本VERSION字段
	 * 		一个Name唯一对应一个Key，Name与Key确定一个流程定义，但版本可以不同
	 * 
	 * Name与Key的关系
	 * 		如果没有给Key指名取值，则默认与name相同(key可以通过在xml配置文件中设置)
	 * id为key与version的组合
	 * version可以自定义，但一般由系统自动生成
	 * (id不能重复，version可以相同，则限制了不同的流程定义key必须唯一)
	 */
	
	// 演示：查询流程定义相关的数据
	
	/**
	 * 根据流程定义的Name属性进行精确查询
	 */
	@Test
	public void findProcessDefinitionByName01() {
		
		ProcessDefinitionQuery query = JBPM4Manager.getInstance().getProcessDefinitionQuery();
		
		List<ProcessDefinition> pdfs = query.processDefinitionName("请假流程").list();
		
		descProcessDef(pdfs);
	}
	
	
	/**
	 * 根据流程定义的Name属性进行模糊查询
	 */
	@Test
	public void findProcessDefinitionByName02() {
		ProcessDefinitionQuery query = JBPM4Manager.getInstance().getProcessDefinitionQuery();
		
		List<ProcessDefinition> pdfs = query.processDefinitionNameLike("%请假%").list();
		
		descProcessDef(pdfs);
	}
	
	
	/**
	 * 根据流程定义的Name属性进行模糊查询
	 * 查询最新的流程定义
	 */
	@Test
	public void findProcessDefinitionByName03() {
		ProcessDefinitionQuery query = 
				JBPM4Manager.getInstance().getProcessDefinitionQuery();
		
		ProcessDefinition pdf = query.processDefinitionNameLike("%请假%")
				.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION)//按版本倒序排列
				.page(0, 1)//firstResult=0，MaxResult=1
				.uniqueResult();//唯一结果集
		
		descProcessDef(pdf);
	}
	
	/**
	 * 根据流程定义的id属性进行查询
	 */
	@Test
	public void findProcessDefinitionById() {
		ProcessDefinitionQuery query = 
				JBPM4Manager.getInstance().getProcessDefinitionQuery();
		
		ProcessDefinition pdf = query.processDefinitionId("Leave-1").uniqueResult();
		
		descProcessDef(pdf);
	}
	
	/**
	 * 根据流程定义的key属性进行查询
	 */
	@Test
	public void findProcessDefinitionByKey() {
		ProcessDefinitionQuery query = 
				JBPM4Manager.getInstance().getProcessDefinitionQuery();
		
		List<ProcessDefinition> pdfs = query.processDefinitionKey("Leave").list();
		
		descProcessDef(pdfs);
	}
	
	
	private void descProcessDef(ProcessDefinition pdf) {
		List<ProcessDefinition> list = new ArrayList<ProcessDefinition>();
		list.add(pdf);
		descProcessDef(list);
	}
	
	private void descProcessDef(List<ProcessDefinition> pdfs) {
		//根据流程定义的Name属性进行查询
		for (Iterator<ProcessDefinition> iterator = pdfs.iterator(); iterator.hasNext();) {
			ProcessDefinition pdf = iterator.next();
			System.out.println("流程定义名称，\t name="+pdf.getName());
			System.out.println("流程定义key，\t key="+pdf.getKey());
			System.out.println("流程定义id，\t id="+pdf.getId());
			System.out.println("流程定义版本，\t version="+pdf.getVersion());
			System.out.println("流程部署id，\t id="+pdf.getDeploymentId());
			
			System.out.println("\n");
		}
	}

}
