package clonegod.mybatis.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    
    static String mybatisConfig = 
    		Paths.get(System.getProperty("user.dir"), "src/test/resources/mybatis-config.xml").toString();
    
    public static SqlSession getSqlSession() throws Exception {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }

    public static SqlSessionFactory getSqlSessionFactory() throws Exception {
    	if (null != sqlSessionFactory) {
    		return sqlSessionFactory;
    	}
//    	String resource = "src/test/resources/mybatis-config.xml";
//    	InputStream configFile = Resources.getResourceAsStream(resource);
        InputStream configFile = new FileInputStream(mybatisConfig);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configFile);
        configFile.close();
        return sqlSessionFactory;
    }
}
