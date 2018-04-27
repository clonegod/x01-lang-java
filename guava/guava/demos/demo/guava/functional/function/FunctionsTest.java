package demo.guava.functional.function;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import demo.guava.functional.City;
import demo.guava.functional.Climate;
import demo.guava.functional.Region;
import demo.guava.functional.State;

/**
 * Function
 * 	input -> value
 * 
 * @author clonegod@163.com
 *
 */
public class FunctionsTest {
	
	Map<String, State> stateMap = Maps.newHashMap();
	
	@Before
	public void setUp() {
		City city1 = new City("Austin,TX", "12345", 250000, Climate.SUB_TROPICAL, 45.3);
		State texas = new State("Texas", "TX", Sets.newHashSet(city1), Region.SOUTHWEST);
		stateMap.put(texas.getCode(), texas);
		
		City city2 = new City("New York,NY", "12345", 2000000,Climate.TEMPERATE, 48.7);
		State newYork = new State("New York", "NY", Sets.newHashSet(city2), Region.NORTHEAST);
		stateMap.put(newYork.getCode(), newYork);
	}
	
	/**
	 * Where the Function interface is used to transform objects,
	 * the Predicate interface is used to filter objects. 
	 */
		
	@Test
	public void testFuctionComposed() {
		
		Function<String, State> lookup = Functions.forMap(stateMap);
		Function<State, String> stateFunction = new StateToCityStringFunction();
		
		// 1 composed Function takes the NY parameter and calls lookup.apply().
		// 2 The return value from the lookup.apply() method is used as a parameter to stateFunction.apply()
		// 3 Finally, the result of the stateFunction.apply() method is returned to the caller. 
		Function<String, String> composed = 
				Functions.compose(stateFunction, lookup);
		String cities = composed.apply("NY");
		System.out.println(cities);
		
		// Without the use of our composed function, the previous example would look as follows:
		String cities2 = stateFunction.apply(lookup.apply("NY"));
		System.out.println(cities2);
		
	}

}
