package demo.guava.functional;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class City {
	private String name;
	private String zipCode;
	private int population;
	private Climate climate;
	private double averageRainfall;
	
	
	public City(String name, String zipCode, int population, Climate climate, double averageRainfall) {
		super();
		this.name = name;
		this.zipCode = zipCode;
		this.population = population;
		this.climate = climate;
		this.averageRainfall = averageRainfall;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", name)
				.add("zipCode", zipCode)
				.add("population", population)
				.add("climate", climate)
				.add("averageRainfall", averageRainfall)
				.toString();
	}

	public String getName() {
		return name;
	}

	public String getZipCode() {
		return zipCode;
	}

	public int getPopulation() {
		return population;
	}

	public Climate getClimate() {
		return climate;
	}

	public double getAverageRainfall() {
		return averageRainfall;
	}

}