
配置数据源DataSource
	spring.datasource.url=jdbc:mysql://192.168.1.201:3306/test
	spring.datasource.driver-class-name=com.mysql.jdbc.Driver
	spring.datasource.username=
	spring.datasource.password=
	# 更多有关具体使用连接池的配置

	
------------------------------------------------------------------------

【基于注解】
配置Mapper扫描路径
	@MapperScan(basePackageClasses=MybatisMapperLocation.class)

编写 Mapper 接口
	public interface UserMapper {
		
		@Select("SELECT * FROM t_user WHERE name LIKE #{name}")
		List<User> getAllUsersByNameLike(String name);
	
		@Select("SELECT * FROM t_user WHERE id = #{id}")
		User getUserById(int id);
		
	}

功能点1	
集成分页插件 pagehelper-spring-boot
	https://github.com/pagehelper/Mybatis-PageHelper
	https://github.com/pagehelper/pagehelper-spring-boot
	
功能点2	
返回插入数据的自增id
	@Insert("INSERT INTO t_user(name, age, create_time) VALUES (#{name}, #{age}, #{createTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // mybatis会自动将返回的id设置到user对象中
	int saveUser(User user);

------------------------------------------------------------------------	

【基于XML】
# 原有的mybatis-config.xml中的配置项可以直接在application.properties中配置
mybatis.mapper-locations=classpath:mapper/*Mapper.xml
mybatis.type-aliases-package=com.clonegod.mybatis.domain

-> 注：mybatis基于注解的方式和基于XML的方式，可以共存。

# 注解和XML的示例
https://github.com/pagehelper/pagehelper-spring-boot/tree/master/pagehelper-spring-boot-samples

------------------------------------------------------------------------	
More To Learn

# Spring Boot集成MyBatis的基础项目
https://github.com/abel533/MyBatis-Spring-Boot


# 用户接口继承 Mybatis Common Mapper
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper</artifactId>
    <version>3.4.5</version>
</dependency>


# MyBatis 代码生成器的使用
	- 命令行
	- Eclipse 插件
	- Maven 插件

