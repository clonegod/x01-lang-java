package clonegod.dubbo.order.api;

import clonegod.dubbo.order.dto.DoOrderRequest;
import clonegod.dubbo.order.dto.DoOrderResponse;

public interface IOrderService {
	
	public DoOrderResponse doOrder(DoOrderRequest request);
	
}
