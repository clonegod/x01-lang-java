Mybatis学习的点：
1. 建立具有一对一和一对多关系的数据模型
2. 配置Mybatis(数据源，事务管理器)
3. 编写mapper
4. 增、删、改
5. 查询：
	一对一查询：联合查询、子查询
	一对多查询：联合查询、子查询

mysql数据库：
1. 建库、建表
2. 插入大量测试数据
3. 创建索引(主键索引，普通索引，联合索引)---增删改频繁的表，不建议创建索引
4. 性能优化（避免长事务，避免跨多表查询）

=================================================================
Mybatis执行sql的三种方式：
1. 直接通过mapper中编写的sql的id字符串执行sql；

2. 编写一个接口，在接口中将方法名称设置为与id字符串相同，这样可以通过接口来调用到对应的sql。
	注：mybatis会使用该接口创建一个代理对象，提供代理对象给客户端进行操作。这样做的好处是：避免在程序中写id字符串。将字符串替换为接口方法。
	
3. 编写一个接口，直接在接口方法上以注解的方式写sql。
	注；这种方式将sql语句转移到了接口类中，不再需要将sql写入到xml文件中了。

以上3中方法的异同点：
第1,2的区别：
	第1种写法，sql写在xml中，根据sql片段的id字符串来绑定被执行的sql。
	第2种写法，sql写在xml中，在接口中声明与sql片段id字符串相同的方法名称来进行绑定。
	相同点：sql都写在xml中。
	
第2,3的区别：
	第2种写法，将sql写在xml中，通过id字符串与接口方法进行绑定。
	第3种写法，则将sql转移到接口方法上通过注解来绑定sql语句，不再需要在xml中写sql.
	相同点：sql都绑定到接口的方法上。

推荐：使用第2种，sql写在xml中，将sql绑定到接口方法上，客户端通过调用接口来完成sql的执行！


=================================================================
SELECT查询上的参数含义：
id				在命名空间中唯一的标识符，用来引用这条sql语句
paramterType	传入这条sql的参数的完全限定类名或者类别名。支持的类型：自定义POJO类，或者hashmap

resultType		这条sql返回值所期望的类的完全限定类名或别名。如果结果集是集合，则为集合中元素的类型。返回结果是多条记录，会自动封装到List中。映射结果到POJO属性上。
resultMap		对于复杂结果集，使用map封装返回结果。注意：resultType与resultMap不能同时使用。resultMap可以解决复杂对象属性映射的问题，比如对象嵌套，属性名与字段名不同的问题。

flushCache		清空缓存。默认false
useCache		缓存本条查询sql的结果。默认true
timeout			本条sql查询的超时时间，默认不设置，由mysql驱动自动处理超时。
fetchSize		建议查询返回的记录条数，默认不设置，由驱动自动处理。

statementType	设置sql语句类型。STATEMENT, PREPARED, CALLABLE
resultSetType	设置结果集的游标：FORWARD_ONLY, SCROLL_SENSITIVE, SCROLL_INSENSITIVE中的任意一种类型


=================================================================
mybatis事务管理
	JDBC	使用JDBC事务管理
	MANAGED	将事务管理托管给第三方框架，如spring

事务配置
	<transactionManager type="JDBC"/>


openSession(false) // 关闭事务自动提交。注：openSession()内部是默认设置自动提交为false的。
session.commit()   // 显示提交事务
session.rollback() // 异常块中回滚事务


=================================================================
高级查询->多表关联查询
查询：
如何构造复杂查询？
	联合查询	select * form author join user where author.user_id = user.id
	子查询		select * from author where author.userId in (select id from user)
	懒加载		真正使用到关联表的数据时，才会发出子查询的sql语句。开启方式，在mybatis-config.xml的settings中启用懒加载。
响应：
如何将多表关联查询的结果集注入到pojo对象？---使用resultMap将结果集映射到嵌套pojo的属性中。

resultMap标签的属性
	id				标识表的主键
	result			绑定column与pojo属性
	association		映射关联表的数据
	collection		处理集合的情况
	constructor		通过构造器注入结果
	discriminator	使用结果的值来决定使用哪个结果映射
	
=================================================================
联合查询与子查询
	联合查询：一条sql语句查所有关联数据，暂用资源大，因为一次性要查询出关联表所有匹配的记录。
	子查询：1+N次查询，占用资源可大可小：
		大，指的是如果没有使用懒加载机制，则会多发出N条sql查询关联表的数据；
		小，指的是如果启用了懒加载机制，则可在真正用到关联表数据时才发出sql查询。---子查询结合懒加载的优势
		

	
=================================================================
枚举类型
	映射枚举类型 Enum，则需要从 EnumTypeHandler 或者 EnumOrdinalTypeHandler 中选一个来使用
	EnumTypeHandler：映射枚举的name名称
	EnumOrdinalTypeHandler：映射枚举的ordinal字段值
	

=================================================================
动态sql
	实现动态拼接sql语句

>条件判断
	if		单条件判断
	choose	多条件选择

>常规
	where	where条件语句
	set		赋值标记,用于update语句
	trim	格式化---where和set对sql进行格式化的底层实现

>循环 	
	foreach		循环	


=================================================================
mybatis中的sql注入问题

sqlMapper.xml中如果使用了${}来引用变量，则可能会发生sql注入问题。
	原因：
		${xxx}参数会直接参与SQL编译，从而不能避免注入攻击。
	解决办法：
		尽量使用#{}来引用变量值，使用preparedStatement预编译sql来执行sql。
		如果某个地方非得使用${xxx}动态设置值，则必须在执行sql前在程序中对xxx的合法性进行检查！
	

