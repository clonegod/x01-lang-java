package clonegod.conc04.desigin.future;

/**
 * 1、客户端发出请求，得到一个FutureResult。
 * 2、客户端执行其它任务
 * 3、当需要结果时，再调用getResult()获取结果。---如果异步任务尚未执行完毕，则阻塞客户端线程，直到异步任务执行完毕。
 *
 */
public class TestFutureResult {
	
	public static void main(String[] args) {
		FutureClient fc = new FutureClient();
		Result result1 = fc.request("请求参数1");
		Result result2 = fc.request("请求参数2");
		
		System.out.println("客户端发送请求成功!");
		System.out.println("客户端继续执行其它任务...");
		
		String data1 = result1.getResult();
		System.out.println("客户端得到查询结果1：" + data1);
		
		String data2 = result2.getResult();
		System.out.println("客户端得到查询结果2：" + data2);
	}
}
