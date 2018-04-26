package jvm.asm.aop;

/**
 * 每次需要删除之前旧的class文件，对原始的字节码文件进行修改。否则将以追加的方式写入修改。
 * 
 */
public class Main { 
	 public static void main(String[] args) { 
		 String targetClass = "E:/source/practice-java/basic-jvm/target/classes/jvm/asm/aop/Account.class";
		 try {
			ClassGenerator.modifyClass(targetClass);
		} catch (Exception e) {
			throw new RuntimeException("改写Account.class发生错误",e);
		}
		 
		 Account account = new Account(); 
		 account.operation();
	 } 
 }