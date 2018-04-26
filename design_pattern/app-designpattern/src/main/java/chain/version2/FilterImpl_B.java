package chain.version2;

import java.util.Random;

public class FilterImpl_B extends AbstractFilter {

	public FilterImpl_B() {
		this.next = new FilterImpl_C();
	}
	
	@Override
	boolean filter(Object... args) {
		System.out.println(this.getClass().getName()+" args:" + args);
		
		boolean hit = new Random().nextInt() % 2 == 0;
		
		return hit;
	}

}
