JPA的定义
	JPA(Java Persistence API)是SUN官方提出的Java持久化规范。
	它为Java开发人员提供了一种对象/关系映射工具来管理Java中的关系数据。
	
持久化
	即把数据（内存中的对象）保存到可永久保存的存储设备（磁盘）中。
	内存中的瞬时状态变为为磁盘上的持久状态。
	持久化的主要应用是将内存中的数据存储到数据库，或以某种格式（如XML）存储在磁盘文件。
	JDBC是一种持久化机制；文件IO也是一种持久化机制。
	
Spring-Data 模块
	简化数据库的访问，提供了更简洁的数据操作接口。
	提供了各种数据源的访问接口，如mysql, redis, mongodb ...
	
Spring-Data-JPA
	Spring-Data-JPA 简化传统JPA访问数据库的开发编码，只需要继承一个接口就能实现数据库的CRUD操作。
	
JPA与Hibernate的关系
	JPA是1个数据持久化的接口规范；
	Hibernate是JPA规范的一个实现，但Hibernate本身提供了JPA之外的更多功能。
	除了Hibernate，还有其他的JPA实现框架，如EclipseLink,OpenJPA等。
	
-------------------------------------------------------------------------------
// CrudRepository： 提供基本增删改查的接口
public interface UserRepository extends CrudRepository<User, Integer> {

}

// PagingAndSortingRepository： 继承自CrudRepository，并支持排序、分页功能的接口
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

}