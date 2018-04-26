package adapter.defaultadapter;

public class MyService extends ServiceAdapter {

	@Override
	public void action3() {
		System.out.println("我只提供action3的服务，其它服务一概不管");
	}
	
}
