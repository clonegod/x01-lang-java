package demo.guava.collection.table;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;

public class HashBasedTableTest {
	
	/**
	 * Map of Map
	 * 二级map结构，可以方便的获取到第二个map中的数据
	 */
	@Test
	public void testHBasedTable() {
		HashBasedTable<Integer,Integer,String> table =
				HashBasedTable.create();
		// rowKey - columnKey:value
		table.put(1, 1, "Rook");
		table.put(1, 2, "Knight");
		table.put(1, 3, "Bishop");
		
		assertThat(table.containsRow(1), is(true));
		assertThat(table.containsColumn(2), is(true));
		assertThat(table.contains(1,2), is(true));
		assertThat(table.containsValue("Rook"), is(true));
		
		assertThat(table.get(1, 3), is("Bishop"));
		
		table.remove(1,3);
		assertThat(table.get(1,3), nullValue());
		
		System.out.println(table);
	}
	
	/**
	 *  different views of the underlying data in the table
	 */
	@Test
	public void testTableView() {
		HashBasedTable<Integer,Integer,String> table =
				HashBasedTable.create();
		table.put(1, 1, "A");
		table.put(1, 2, "B");
		table.put(1, 3, "C");
		table.put(2, 1, "D");
		table.put(2, 2, "E");
		table.put(2, 3, "F");
		
		Map<Integer,String> rowMap = table.row(1);
		System.out.println(rowMap); // return columnKey & value
		
		Map<Integer,String> columnMap = table.column(2);
		System.out.println(columnMap); // return rowKey & value
		
	}
	
}
