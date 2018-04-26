package demo.guava.functional.predicate;

import com.google.common.base.Predicate;

import demo.guava.functional.Region;
import demo.guava.functional.State;

public class RegionPredicate implements Predicate<State> {

	@Override
	public boolean apply(State input) {
		return input.getRegion() == Region.MIDWEST;
	}

}
