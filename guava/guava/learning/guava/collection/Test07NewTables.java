package guava.collection;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * The Table collection type, which is a very powerful collection that is a replacement for using a map of maps

 * Table  --- 二维表
 * 
 * 二级Map的简写形式, 比如  Map<String,Map<String,String>  
 * 
 * Table represents a special map where two keys can be specified in
 * combined fashion to refer to a single value.
 */

//Table<R,C,V> == Map<R,Map<C,V>>  -- Map of Map
/*
* Company: IBM, Microsoft, TCS
* IBM -> {101:Mahesh, 102:Ramesh, 103:Suresh}
* Microsoft -> {101:Sohan, 102:Mohan, 103:Rohan }
* TCS -> {101:Ram, 102: Shyam, 103: Sunil }
*
* */
public class Test07NewTables {

	@Test
	public void test() {
		Table<String, String, Integer> myTable = TreeBasedTable.create();
//		myTable.put(rowKey, columnKey, value)
		myTable.put("1年级", "1班", 10);
		myTable.put("1年级", "2班", 20);
		myTable.put("1年级", "3班", 30);
		
		myTable.put("2年级", "1班", 40);
		myTable.put("2年级", "2班", 50);
		
		myTable.put("3年级", "1班", 60);
		
		System.out.println(myTable.row("1年级"));
		
		System.out.println(myTable.column("1班"));
		
		
		System.out.println(myTable.row("1年级").get("3班"));
		
		
		Map<String, Map<String, Integer>> toMap = myTable.rowMap();
		System.out.println(toMap);
	}
	
	
	public static void main(String[] args) {
		// create a table
		Table<String, String, String> employeeTable = HashBasedTable.create();
		
		// initialize the table with employee details
		employeeTable.put("IBM", "101", "Mahesh");
		employeeTable.put("IBM", "102", "Ramesh");
		employeeTable.put("IBM", "103", "Suresh");
		employeeTable.put("Microsoft", "101", "Sohan");
		employeeTable.put("Microsoft", "102", "Mohan");
		employeeTable.put("Microsoft", "103", "Rohan");
		employeeTable.put("TCS", "121", "Ram");
		employeeTable.put("TCS", "122", "Shyam");
		employeeTable.put("TCS", "123", "Sunil");
		
		// get Map corresponding to IBM
		Map<String, String> ibmEmployees = employeeTable.row("IBM");
		System.out.println("\nList of IBM Employees:");
		for (Map.Entry<String, String> entry : ibmEmployees.entrySet()) {
			System.out.println("\tEmp Id: " + entry.getKey() + ", Name: " + entry.getValue());
		}
		
		// get all the unique keys of the table
		Set<String> employers = employeeTable.rowKeySet();
		System.out.println("\nAll Employers: ");
		for (String employer : employers) {
			System.out.print(employer + "\t");
		}
		System.out.println();
		
		// get a Map corresponding to 102
		Map<String, String> EmployerMap = employeeTable.column("102");
		System.out.println("\nEmployees of Column 102: ");
		for (Map.Entry<String, String> entry : EmployerMap.entrySet()) {
			System.out.println("\tEmployer: " + entry.getKey() + ", Name: " + entry.getValue());
		}
	}

}
