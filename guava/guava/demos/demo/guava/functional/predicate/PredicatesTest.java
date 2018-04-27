package demo.guava.functional.predicate;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import demo.guava.functional.City;
import demo.guava.functional.State;

public class PredicatesTest {
	@Test
	public void testPredicates() {
		Map<String, State> stateMap = State.getStates();
		Function<String, State> lookup = Functions.forMap(stateMap);
		
		/** 
		 * Predicate 提供断言功能，根据入参进行逻辑判断，返回TRUE/FALSE 
		 * 多个Predicate可以组合在一起行为复合条件：and, or, not
		 * */
		Predicate<City> smallPopulationPredicate = new PopulationPredicate();
		Predicate<City> lowRainFallPredicate = new LowRainfallPredicate();
		Predicate<City> temperateClimatePredicate = new TemperateClimatePredicate();
		Predicate<State> southwestOrMidwestRegionPredicate = new SouthwestOrMidwestRegionPredicate();
		
		Predicate smallAndDry =
				Predicates.and(smallPopulationPredicate, lowRainFallPredicate);
		
		Predicate smallTemperate =
				Predicates.or(smallPopulationPredicate, temperateClimatePredicate);
		
		Predicate largeCityPredicate = 
				Predicates.not(smallPopulationPredicate);
		
		Predicate<String> predicate =
				Predicates.compose(southwestOrMidwestRegionPredicate, lookup);
		
		// Predicates.CompositionPredicate<A, B>
		// p.apply(f.apply(a));
		// lookup.apply("TX") => StateOfTexas => southwestOrMidwestRegionPredicate(state) => true/false
		System.out.println(predicate.apply("TX"));  
		System.out.println(predicate.apply("NY"));
	}
	
	
	/**
	 * Predicate用来声明某个过滤条件，当被筛查的元素不满足条件时，将被过滤掉。
	 */
	@Test
	public void testFilterCollection() {
		Predicate<String> startWithX = 
			new Predicate<String>(){
				@Override
				public boolean apply(String paramT) {
					return paramT.contains("x");
				}
			};
		
		Predicate<String> endWithX = 
			new Predicate<String>(){
				@Override
				public boolean apply(String paramT) {
					return paramT.endsWith("x");
				}
			};
		
		Predicate<String> composite = Predicates.and(startWithX, endWithX);
		
		List<String> list = Lists.newArrayList("x","y","x1x","x2");
		// FluentIterable.filter takes a Predicateas as an argument
		FluentIterable<String> fiter = FluentIterable.from(list).filter(composite);
		
		System.out.println(fiter.toList());
	}
}
