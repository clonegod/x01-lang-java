package clonegod.dubbo.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import clonegod.dubbo.api.order.IOrderService;
import clonegod.dubbo.api.order.OrderRequest;
import clonegod.dubbo.api.order.OrderResponse;

@Service("orderServiceV2")
public class OrderServiceImplV2 implements IOrderService {

	@Override
	public OrderResponse doOrder(OrderRequest request) {
		System.out.println(Thread.currentThread().getName() + "：开始处理订单请求");
		
		OrderResponse res = new OrderResponse();
		res.setStatus("V2:00000");
		res.setMessage("V2:success");
		res.setOrderNo(UUID.randomUUID().toString());
		
		return res;
	}

}
