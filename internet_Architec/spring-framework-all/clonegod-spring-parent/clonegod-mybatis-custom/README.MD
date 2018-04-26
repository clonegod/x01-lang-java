# 手写mybatis，实现主干流程的功能：
```
1、将sql与接口做关联，注册到MapperRegistry缓存中（初始化阶段）
2、sqlSession提供获取接口的动态代理方法
3、客户端使用代理接口进行操作
4、在代理类的invoke方法中
	通过当前调用的方法名从MapperRegistry获取对应的sql
	将sql交给sqlSession去执行
5、sqlSession委托executor去执行sql
6、executor委托StatementHandler执行sql
7、StatementHandler将ResultSet委托给ResultSetHandler处理
8、ResultSetHandler将结果映射到Bean中返回。
```

## v1 - 简化版
```
Configuration
MapperProxy
SqlSession
Executor
SimpleExecutor
```

## v2 - 升级版（在接口方法上使用注解声明sql，将sql执行的不同阶段交给不同的handler进行处理）
```
Configuration
MapperRegistry
MapperProxy
SqlSession
SimpleExecutor
CachingExecutor
StatementHandler
ResultSetHandler
``