package clonegod.dubbo.service.consumer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcContext;

import clonegod.dubbo.api.order.IOrderService;
import clonegod.dubbo.api.order.OrderRequest;
import clonegod.dubbo.api.order.OrderResponse;

public class OrderServiceInvoker {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		// dubbo协议，调用服务版本0.0.1
//		testOrderServiceMultiVersion("META-INF/spring/dubbo-service-consumerV1.xml", 5);
		
		Thread.sleep(1000);
		
		// hessian协议，调用服务版本0.0.2
//		testOrderServiceMultiVersion("META-INF/spring/dubbo-service-consumerV2.xml", 5);
		
		Thread.sleep(1000);
		// 服务的异步调用
//		testOrderServiceAsync("META-INF/spring/dubbo-service-consumer-async.xml");
		
		Thread.sleep(1000);
		// 服务超时配置
		testOrderServiceMultiVersion("META-INF/spring/dubbo-service-consumer-timeout.xml", 1);
		
	}

	private static void testOrderServiceMultiVersion(String configLocation, int loopCount) {
		// start spring container
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{configLocation});
        
		context.refresh();
        
        // obtain proxy object for remote invocation
        IOrderService orderService = context.getBean("orderService", IOrderService.class);
        
        for(int i = 0; i < loopCount; i++) {
        	OrderRequest request = new OrderRequest("A001", i, "Alice");
        	OrderResponse response = orderService.doOrder(request);
        	System.out.println(response);
        }
        
        context.close();
	}
	
	private static void testOrderServiceAsync(String configLocation) throws InterruptedException, ExecutionException {
		// start spring container
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{configLocation});
		
		context.refresh();
		
		// obtain proxy object for remote invocation
		IOrderService orderService = context.getBean("orderService", IOrderService.class);
		
		// 异步调用服务
		OrderRequest request = new OrderRequest("A001", 3, "Alice");
		orderService.doOrder(request);
		Future<OrderResponse> future = RpcContext.getContext().getFuture();
		
		System.out.println("处理其它业务。。。。。。");
		
		OrderResponse response = future.get(); // 阻塞等待异步结果返回
		System.out.println("得到异步结果：" + response);
		
		context.close();
	}
	
	
}
