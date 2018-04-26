package chain.version2;

public class TestFilterChain {
	public static void main(String[] args) {
		FilterImpl_A filterA = new FilterImpl_A();
		boolean hit = filterA.doFilter("测试责任链模式");
		System.out.println(hit);
	}
}
