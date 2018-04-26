package demo.simple;

import java.io.IOException;

import org.junit.Test;

import app.jbpm.manager.JBPM4Manager;

public class jbpm01_DeployProcessDefinition {
	/**
	 * 将流程定义文件中的数据存储到数据库中
	 * ---部署流程
	 * @throws IOException 
	 */
	
	@Test
	public void testDeploy() {
		
		JBPM4Manager.getInstance().deployProcess("leave.jpdl.xml");
	}
}
