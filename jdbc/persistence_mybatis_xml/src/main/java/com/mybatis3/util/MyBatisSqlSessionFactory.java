package com.mybatis3.util;

import java.io.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

public class MyBatisSqlSessionFactory {
	
	private static SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 单例模式只创建一个SqlSessionFactory实例
	 * @return
	 */
	private static SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream("mybatis-config.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			} catch (IOException e) {
				throw new RuntimeException(e.getCause());
			}
		}
		return sqlSessionFactory;
	}

	public static SqlSession openSession() {
		return getSqlSessionFactory().openSession();
	}
}
