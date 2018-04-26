package demo.guava.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 
 * LoadingCache
 * 	1. 缓存过期时间设置
 *  2. 缓存数据量设置
 *  3. 缓存未命中时，自动调用CacheLoader从外部查询数据
 *	4. 缓冲池其它参数的配置
 */
public class CachingTester {
	
	public static void main(String[] args) throws InterruptedException {
		// create a cache for employees based on their employee id
		LoadingCache<String, Employee>employeeCache = 
		CacheBuilder.newBuilder()
			.initialCapacity(50)
			.maximumSize(100) // maximum 100 records can be cached --- 最多缓存100条数据
			.expireAfterAccess(30, TimeUnit.MINUTES)
			.concurrencyLevel(4) // 并发更新缓存的线程数量-太大，浪费内存；太小，会引起多线程竞争
			// cache will expire after 30 minutes of access
			.build(new CacheLoader<String, Employee>(){
				// build the cacheloader
				@Override
				public Employee load(String empId) throws Exception {
					// make the expensive call
					return getFromDatabase(empId); // 缓存不命中时，从数据库查询，并将对结果进行缓存
				}
		});
		
		try {
			//on first invocation, cache will be populated with corresponding
			//employee record
			System.out.println("Invocation #1");
			System.out.println(employeeCache.get("100"));
			System.out.println(employeeCache.get("103"));
			System.out.println(employeeCache.get("110"));
			
//			TimeUnit.MINUTES.sleep(30);
			
			//second invocation, data will be returned from cache
			System.out.println("\nInvocation #2");
			System.out.println(employeeCache.get("100"));
			System.out.println(employeeCache.get("103"));
			System.out.println(employeeCache.get("110"));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
		
	private static Employee getFromDatabase(String empId) {
		Employee e1 = new Employee("Mahesh", "Finance", "100");
		Employee e2 = new Employee("Rohan", "IT", "103");
		Employee e3 = new Employee("Sohan", "Admin", "110");
		Map<String, Employee> database = new HashMap<String, Employee>();
		database.put("100", e1);
		database.put("103", e2);
		database.put("110", e3);
		System.out.println("---> From Database, empId:" + empId);
		return database.get(empId);
	}
}

class Employee {
	String name;
	String dept;
	String emplD;

	public Employee(String name, String dept, String empID) {
		this.name = name;
		this.dept = dept;
		this.emplD = empID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmplD() {
		return emplD;
	}

	public void setEmplD(String emplD) {
		this.emplD = emplD;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(Employee.class)
				.add("Name", name)
				.add("Department", dept)
				.add("Emp Id", emplD)
				.toString();
	}
}