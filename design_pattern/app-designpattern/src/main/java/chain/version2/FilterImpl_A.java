package chain.version2;

public class FilterImpl_A extends AbstractFilter {

	public FilterImpl_A() {
		this.next = new FilterImpl_B();
	}
	
	@Override
	boolean filter(Object... args) {
		System.out.println(this.getClass().getName()+" args:" + args);
		return false;
	}

}
