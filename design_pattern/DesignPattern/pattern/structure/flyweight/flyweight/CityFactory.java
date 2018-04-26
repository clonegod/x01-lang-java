package flyweight;


import java.util.HashMap;
import java.util.Map;

public class CityFactory {
	private CityFactory() {
	}

	private static final Map<String, City> CITY_MAP = new HashMap<>();

	/**
	 * 设置内部类，并实现接口。外部通过接口来获取需要的对象信息。
	 *
	 */
	private static class CityImpl implements City {

		private String code;
		private String name;

		public CityImpl(String code, String name) {
			super();
			this.code = code;
			this.name = name;
		}

		@Override
		public String getCode() {
			return this.code;
		}

		@Override
		public String getName() {
			return this.name;
		}

	}

	/**
	 * 静态初始化大量的细粒度对象，可确保这些对象只初始化1次。
	 */
	static {
		CITY_MAP.put("beijing", new CityImpl("1001", "北京"));
		CITY_MAP.put("chongqing", new CityImpl("5000", "重庆"));
		// ... 更多细粒度的对象
	}

	public static City getCityByName(String name) {
		return CITY_MAP.get(name.toLowerCase());
	}

}
