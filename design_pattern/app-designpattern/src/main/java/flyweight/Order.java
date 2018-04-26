package flyweight;

/**
 * 声明享元对象需要对客户端提供的接口 
 *
 */
public interface Order {
	void serve(Table table);
	String getFlavor();
}
