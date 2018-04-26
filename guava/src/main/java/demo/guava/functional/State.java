package demo.guava.functional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class State {
	private String name;
	private String code;
	private Set<City> mainCities = new HashSet<City>();
	private Region region;
	
	public State(String name, String code, Set<City> mainCities, Region region) {
		super();
		this.name = name;
		this.code = code;
		this.mainCities = mainCities;
		this.region = region;
	}
	
	public static Map<String, State> getStates() {
		Map<String, State> stateMap = Maps.newHashMap();
		
		City city1 = new City("Austin,TX", "12345", 250000, Climate.SUB_TROPICAL, 45.3);
		State texas = new State("Texas", "TX", Sets.newHashSet(city1), Region.SOUTHWEST);
		stateMap.put(texas.getCode(), texas);
		
		City city2 = new City("New York,NY", "12345", 2000000,Climate.TEMPERATE, 48.7);
		State newYork = new State("New York", "NY", Sets.newHashSet(city2), Region.NORTHEAST);
		stateMap.put(newYork.getCode(), newYork);
		
		return stateMap;
	}
	
	public State addCity(City city) {
		mainCities.add(city);
		return this;
	}
	
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public Set<City> getMainCities() {
		return mainCities;
	}

	public Region getRegion() {
		return region;
	}
	
}