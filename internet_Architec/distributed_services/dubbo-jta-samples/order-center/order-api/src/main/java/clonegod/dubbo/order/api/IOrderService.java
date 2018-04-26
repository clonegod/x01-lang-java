package clonegod.dubbo.order.api;

import clonegod.dubbo.order.dto.OrderRequest;
import clonegod.dubbo.order.dto.OrderResponse;

public interface IOrderService {
	
	public OrderResponse doOrder(OrderRequest request);
	
}
