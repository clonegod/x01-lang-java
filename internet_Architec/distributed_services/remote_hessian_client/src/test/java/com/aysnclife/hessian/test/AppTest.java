package com.aysnclife.hessian.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.asynclife.hessian.App;
import com.asynclife.hessian.service.impl.LocalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={App.class})
//@WebIntegrationTest
public class AppTest {

	@Autowired
	LocalService remoteService;
	
	@Test
	public void testRemoteServiceByHessian() {
		String name = "测试Hessian";
		String retText = remoteService.call(name);
		System.out.println("message from remote: "+retText);
	}
	
}