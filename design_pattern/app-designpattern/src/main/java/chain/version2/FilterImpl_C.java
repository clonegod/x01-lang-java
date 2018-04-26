package chain.version2;

public class FilterImpl_C extends AbstractFilter {

	public FilterImpl_C() {
		this.next = null;
	}
	
	@Override
	boolean filter(Object... args) {
		System.out.println(this.getClass().getName()+" args:" + args);
		return false;
	}

}
