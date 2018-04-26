package demo.guava.functional.predicate;

import com.google.common.base.Predicate;

import demo.guava.functional.City;
import demo.guava.functional.Climate;

public class TemperateClimatePredicate implements Predicate<City> {
	@Override
	public boolean apply(City input) {
		return input.getClimate().equals(Climate.TEMPERATE);
	}
}