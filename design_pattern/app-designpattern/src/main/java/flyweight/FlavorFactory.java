
package flyweight;

public class FlavorFactory {
	
	// 工厂内部使用聚集维护已创建的享元对象
	private Order[] flavors = new Order[10]; // 容量可以放大点
	
	private int ordersMade = 0;
	private int totalFlavors = 0;
	
	public Order getOrder(String flavorToGet) {
		
		// 否已经存在
		if(ordersMade > 0) {
			for(int i=0; i<ordersMade; i++) {
				if(flavorToGet.equals(flavors[i].getFlavor()))
					return flavors[i];
			}
		}
		
		// 没有，再新建
		flavors[ordersMade] = new Flavor(flavorToGet);
		
		totalFlavors++;
		
		return flavors[ordersMade++];
	}
	
	public int getTotalFlavors() {
		return totalFlavors;
	}

}
