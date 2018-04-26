package simplefactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class 简单工厂方法的演变 {
	/**
	 * 工厂
	 * 抽象产品
	 * 具体产品
	 * 
	 * ---> 抽象产品的省略
	 * 	系统仅有1个具体产品，则可省略抽象产品角色，由工厂直接返回具体产品
	 * 
	 * ---> 工厂与抽象产品的合并
	 * 	一个抽象产品类同时是子类的工厂，如DateFormat类--简单工厂与工厂方法模式都用到了
	 * 
	 * ---> 工厂、抽象产品、具体产品的合并
	 * 	一个具体产品类为自身的工厂，负责创建自己并返回
	 * 
	 * 
	 * ---> 单例模式
	 * 	向外界提供唯一对象
	 * 
	 * ---> 多例模式
	 * 	循环使用固定数目的一组产品对象----线程池？数据库连接池？
	 * 
	 */
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
	}
}
