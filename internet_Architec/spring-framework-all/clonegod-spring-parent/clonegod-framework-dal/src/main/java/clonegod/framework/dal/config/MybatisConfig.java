package clonegod.framework.dal.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;

import clonegod.framework.dal.plugins.ClonegodPrintQuerySQLPlugin;

@Configuration
@MapperScan(basePackages = "clonegod.framework.dal.dao")
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfig {
    @Autowired
    @Qualifier("dataSource")
    public DataSource dataSource;


	@Lazy(false)
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory localSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        
        // type handler
//        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{new ClonegodCurrencyEnumTypeHandler()});
        sqlSessionFactoryBean.setTypeHandlersPackage("com.clonegod.dal.typehandlers");
        
        // add plugins 
        System.err.println("setPlugins...................");
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor(), new ClonegodPrintQuerySQLPlugin()});
        
        SqlSessionFactory factory = sqlSessionFactoryBean.getObject();
        //lazy loading switch
        factory.getConfiguration().setLazyLoadingEnabled(true);
        factory.getConfiguration().setAggressiveLazyLoading(false);
        factory.getConfiguration().setProxyFactory(new CglibProxyFactory());
        return factory;
    }

    private PageInterceptor pageInterceptor() {
    	PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("dialect", PageHelper.class.getName());
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Primary
    @Lazy(false)
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(localSessionFactoryBean(), ExecutorType.SIMPLE);
    }

    @Lazy(false)
    @Bean(name = "batchSst")
    public SqlSessionTemplate batchSst() throws Exception {
        return new SqlSessionTemplate(localSessionFactoryBean(), ExecutorType.BATCH);
    }

    /**
     * spring 事务管理器
     */
    @Bean(name = "txManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
