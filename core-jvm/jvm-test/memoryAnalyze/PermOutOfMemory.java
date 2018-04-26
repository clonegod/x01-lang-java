package com.aysnclife.dataguru.jvm.memoryAnalyze;

import javassist.ClassPool;
import javassist.CtClass;
/*
永久区溢出
-XX:PermSize=3M
-XX:MaxPermSize=3M
 * */
public class PermOutOfMemory {
	public static void main(String[] args) throws Exception {
		for(int i=0; i<800000;i++) {
			ClassPool clsPool = ClassPool.getDefault();
			CtClass cls = clsPool.makeClass("flskdja"+i);
			System.out.println(cls.getName());
		}
	}
}
