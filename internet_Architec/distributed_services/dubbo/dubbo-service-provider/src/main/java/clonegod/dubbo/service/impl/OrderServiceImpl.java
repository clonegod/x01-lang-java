package clonegod.dubbo.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import clonegod.dubbo.api.order.IOrderService;
import clonegod.dubbo.api.order.OrderRequest;
import clonegod.dubbo.api.order.OrderResponse;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	@Override
	public OrderResponse doOrder(OrderRequest request) {
		System.out.println(Thread.currentThread().getName() + "：开始处理订单请求");
		
		try {
			int timeout = 1000;
			TimeUnit.MILLISECONDS.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		OrderResponse res = new OrderResponse();
		res.setStatus("00000");
		res.setMessage("success");
		res.setOrderNo(UUID.randomUUID().toString());
		
		return res;
	}

}
