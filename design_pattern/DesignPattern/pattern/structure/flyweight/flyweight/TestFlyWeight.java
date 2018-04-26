package flyweight;

import org.junit.Assert;

public class TestFlyWeight {
	/**
	 * 确保每次返回同一个对象 - 单例模式？
	 */
	public static void main(String[] args) {
		City city1 = CityFactory.getCityByName("beijing");
		City city2 = CityFactory.getCityByName("beijing");
		System.out.println(city1);
		System.out.println(city2);
		Assert.assertEquals(city1, city2);

		City city3 = CityFactory.getCityByName("chongqing");
		City city4 = CityFactory.getCityByName("chongqing");
		System.out.println(city3);
		System.out.println(city4);
		Assert.assertEquals(city3, city4);
	}
}
