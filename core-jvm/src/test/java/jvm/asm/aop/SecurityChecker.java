package jvm.asm.aop;


public class SecurityChecker {

	public static void checkSecurity() {
		System.out.println("Aspect-切面：进行所有接口调用进行安全检查");
	}

}
