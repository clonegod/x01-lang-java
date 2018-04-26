package prototype.lowClone;

public class Client {
	public static void main(String[] args) {
		Person prototype = new Person();
		prototype.name = "张三";
		prototype.age = 10;
		
		Address addr = new Address();
		addr.location = "xxx路";
		
		prototype.address = addr;
		
		System.out.println("原始对象Person: " + prototype);
		
		// Clone
		Person cloned = prototype.cloneMe();
		cloned.name = "李四";
		cloned.address.location = "未知"; // 浅复制，是直接复制的引用，这里的修改将影响到原始对象
		System.out.println("克隆对象Person: " + cloned);
		
		System.out.println("原始对象Person: " + prototype);
		
	}
}
