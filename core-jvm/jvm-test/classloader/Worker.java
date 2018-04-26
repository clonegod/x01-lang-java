package com.aysnclife.dataguru.jvm.classloader;

import java.text.MessageFormat;
import java.util.Date;

public class Worker implements IWorker {

	@Override
	public void doit() {
		System.out.println(MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:SS}", new Date()) 
				+ " ..."
				+ this.getClass().hashCode()
				+ " ... versionE");
	}

}
