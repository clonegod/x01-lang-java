package adapter.defaultadapter;

public class Client {
	public static void main(String[] args) {
		Service service = new MyService();
		
		service.action1();
		service.action2();
		service.action3();
		
	}
}
