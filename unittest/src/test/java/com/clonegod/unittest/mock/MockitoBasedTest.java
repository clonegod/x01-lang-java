package com.clonegod.unittest.mock;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public abstract class MockitoBasedTest {
	
    @Before
    public void setUp() throws Exception {
    	// 初始化测试用例类中由Mockito的注解标注的所有模拟对象
       // MockitoAnnotations.initMocks(this);
    }
    
}