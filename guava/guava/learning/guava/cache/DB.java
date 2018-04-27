package guava.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class DB {
	
	static ConcurrentMap<String, Employee> empMap = new ConcurrentHashMap<>();
	
	static {
		empMap.put("100", new Employee("Mahesh", "Finance", "100"));
		empMap.put("101", new Employee("Rohan", "IT", "102"));
		empMap.put("102", new Employee("Sohan", "Admin", "113"));
	}
	
	/**
	 * 模拟从数据库查询
	 * 
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public static Employee getFromDatabase(String empId) throws Exception {
		System.out.println("---> query from Database: key=" + empId);
		TimeUnit.SECONDS.sleep(1);
		return empMap.get(empId);
	}
}
