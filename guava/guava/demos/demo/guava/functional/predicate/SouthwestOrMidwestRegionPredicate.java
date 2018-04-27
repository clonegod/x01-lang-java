package demo.guava.functional.predicate;

import com.google.common.base.Predicate;

import demo.guava.functional.Region;
import demo.guava.functional.State;

public class SouthwestOrMidwestRegionPredicate implements Predicate<State> {
	@Override
	public boolean apply(State input) {
		return input.getRegion().equals(Region.MIDWEST) || input.getRegion().equals(Region.SOUTHWEST);
	}
}