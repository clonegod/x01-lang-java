package demo.guava.functional.predicate;

import com.google.common.base.Predicate;

import demo.guava.functional.City;

public class PopulationPredicate implements Predicate<City> {
	@Override
	public boolean apply(City input) {
		return input.getPopulation() <= 10000;
	}
}