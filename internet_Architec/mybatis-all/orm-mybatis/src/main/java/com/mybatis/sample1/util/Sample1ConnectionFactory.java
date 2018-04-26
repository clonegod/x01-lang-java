package com.mybatis.sample1.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Sample1ConnectionFactory {

    private static SqlSessionFactory sessionFactory;
    
    static{
    	Reader reader = null;
        try{
        	String resource = "sample1-mybatis-config.xml"; 
        	reader = Resources.getResourceAsReader(resource);
        	sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        } finally {
        	try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    public static SqlSessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
