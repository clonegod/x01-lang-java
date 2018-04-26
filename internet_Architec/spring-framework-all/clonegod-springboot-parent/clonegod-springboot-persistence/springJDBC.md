# spring JDBC
## 数据源 DataSource
数据库连接池技术：

	apache commons DBCP
		commons-dbcp2
			依赖：commons-pool2
		commons-dbcp (老版本-生产者消费者模型)
			依赖：commons-pool

	Tomcat DBCP

	Alibaba Driud
	
	hikariCP

比较数据库连接池技术：
	
	1、数据库连接的获取是否高效（同步阻塞，异步非阻塞）
	2、数据库连接的统计报告

数据源：

	即管理数据库连接的来源，通过DataSource接口进行定义，对外提供获取连接的功能。

springboot 数据源的自动装配：

	springboot在默认情况下会自动配置hikariCP作为数据源。
	hikariCP作为底层数据库连接池，管理应用系统与数据库的Connection连接。	


数据源的几种类型：

	通用型数据源
		javax.sql.DataSource	
	
	分布式数据源
		javax.sql.XADataSource		

	嵌入式数据源
		H2, HSQL or Derby


常见数据源的使用场景：

org.springframework.boot.autoconfigure.jdbc.DataSourceProperties

	单数据源
		spring.datasource.url = [url]
		spring.datasource.username = [username]
		spring.datasource.password = [password]
		spring.datasource.driverClassName = com.mysql.jdbc.Driver

	多数据源（主从、分布式）
		http://www.baeldung.com/spring-data-jpa-multiple-databases
		主数据源：@Primary
		注入多个数据源时，使用@Qualifier进行区别

Repository与DAO的区别：

	1、DAO 数据库访问对象，一般指操作传统数据库。
	2、Repository表示数据仓储，代表更广泛的数据访问层。
		Repository既可以数据库，也可以是内存等提供对数据增删改查的操作接口。
		Mysql，Nosql，MongoDB, Redis, Hbase...
	
----------

## 事务 Transaction

事务：用于提供数据完整性，在客户端并发操作数据库的情况下要确保数据的一致性。

### 事务的自动提交模式（Auto-commit mode）
默认情况下，新创建的Connection是设置为自动提交模式的。
在自动提交模式下，每个发送到数据库的SQL都作为一个独立的事务得到执行，并提交。

如果要关闭自动提交，明确设置connectionsetAutoCommit(boolean autoCommit)

如果关闭了自动提交，则会将事务中的所有SQL按一个组进行整体执行，要么通过commit全部提交，要么通过rollback整体回滚。

##### @Transactional： spring提供的事务注解 - 基于AOP实现事务控制

JDBC代理-关闭自动提交，执行SQL，提交/回滚
org.springframework.transaction.interceptor.TransactionInterceptor

底层原理：通过代理方式，先将事务自动提交关闭了，等方法执行完成后，再commit或rollback事务。

##### @Transactional： spring事务中异常回滚的粒度控制  
org.springframework.transaction.annotation.Transactional

- 	rollbackFor		发生哪些类型的异常时，才进行回滚
-	noRollbackFor	发生哪些类型的异常时，不回滚

#### ---> spring 对事务回滚的异常配置（默认情况）

	By default, a transaction will be rolling back on RuntimeException 
	and Error but not on checked exceptions (business exceptions). 
	默认对RuntimeException异常和Error异常，执行回滚操作。
	对checked exception，不会回滚事务。
	原因如下：
	受检的异常被视为业务异常, 因此是事务性业务方法的常规预期结果, 
	在对受检异常进行处理时，程序可以提供一个可替代的返回值, 这样仍然可以让事务完成资源操作。

### 事务相关的重要概念

事务特征的传递 - spring采用ThreadLocal实现

	org.springframework.transaction.support.TransactionSynchronizationManager

事务定义 - spring中对事务的定义

	org.springframework.transaction.TransactionDefinition

#### 1、事务的隔离级别 (Transaction isolation levels)
spring中的默认使用的isolation=-1（使用底层数据库的隔离级别）

	TRANSACTION_NONE	不支持事务
	TRANSACTION_READ_UNCOMMITTED 读未提交
		缺点：可能发生脏读
	TRANSACTION_READ_COMMITTED 读已提交
		缺点：可能发生不可重复读，幻想读
	TRANSACTION_REPEATABLE_READ 可重复读 -> Mysql默认的隔离级别
		缺点：可能发生幻想读
	TRANSACTION_SERIALIZABLE  序列化读
		缺点：事务串行，无并发

	脏读 - 读到的数据可能马上就被回滚了	
		A事务读取到B事务尚未提交的数据，这部分数据有可能之后在B事务中被回滚了
	
	不可重复读 - 前后两次查询发现数据不一致
		A事务读取了一行数据，之后B事务修改了这行数据，A事务再次查询发现前后两次查询结果不一致

	幻象读 - 前后两次查询发现多了一条数据
		A事务根据某些条件查询得到一批数据，接着B事务更新或插入了1条件数据，然后A事务再次以相同的条件查询，发现多了一个新的数据。

#### 2、spring中事务的传播特性
	PROPAGATION_REQUIRED （Spring 默认使用该特性）需要在事务环境下运行，如果没有事务，则新建一个事务。
		Support a current transaction; create a new one if none exists.

	PROPAGATION_SUPPORTS 支持事务，也可以没有事务
		Support a current transaction; execute non-transactionally if none exists.

	PROPAGATION_MANDATORY 必须在已有的事务环境下运行
		Support a current transaction; throw an exception if no current transaction exists.

	PROPAGATION_REQUIRES_NEW 挂起已有的事务，并创建一个新的事务来执行当前的程序
		Create a new transaction, suspending the current transaction if one exists.

	PROPAGATION_NOT_SUPPORTED 不支持事务，以非事务方式运行
		Do not support a current transaction; rather always execute non-transactionally.

	PROPAGATION_NEVER 不支持事务，有事务则抛异常
		Do not support a current transaction; throw an exception if a current transaction exists.
	
	PROPAGATION_NESTED 嵌套事务，在一个外部事务中，嵌套允许子事务。对于嵌套事务而言，允许内部嵌套的事务发生失败，但外部事务是可以成功提交的。
		Execute within a nested transaction if a current transaction exists, behave like {@link #PROPAGATION_REQUIRED} else.

#### 3、保护点（Savepoints）
savePoint
	rollback(Savepoint savepoint)
	releaseSavepoint(Savepoint savepoint)

----------
## JDBC API - JDBC 4.0 (JSR-221)


----------
### spring中的JDBC事务实战

事务管理器

	org.springframework.transaction.PlatformTransactionManager
		org.springframework.jdbc.datasource.DataSourceTransactionManager
		org.springframework.transaction.jta.JtaTransactionManager - 分布式事务管理器
		

- 底层JDBC 实现

- JDBCTemplate 实现

- TransactionManger API实现


----------
## 分布式事务 JTA

#### [Distributed Transactions with JTA](https://docs.spring.io/spring-boot/docs/2.0.1.RELEASE/reference/htmlsingle/#boot-features-jta)

#### Using an Atomikos Transaction Manager

#### Using a Bitronix Transaction Manager


----------

### @Transactional Spring利用AOP实现事务控制的几个相关属性的默认值
	事务传播特性	默认配置为PROPAGATION_REQUIRED
	事务隔离级别	未配置，使用数据库的默认级别。比如Mysql默认是REPEATABLE_READ
	事务超时		未配置，默认使用底层事务系统的超时时间
	事务回滚		默认是RuntimeException和Error类型异常，才会回滚事务
	 