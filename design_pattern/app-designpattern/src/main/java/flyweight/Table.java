package flyweight;

/**
 * 外蕴状态，需在外部传入给享元对象
 *
 */
public class Table {
	
	private int tableNumber;
	
	public Table(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	
}
