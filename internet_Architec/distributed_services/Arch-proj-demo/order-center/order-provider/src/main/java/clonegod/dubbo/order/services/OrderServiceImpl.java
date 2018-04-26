package clonegod.dubbo.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

import clonegod.dubbo.order.api.IOrderService;
import clonegod.dubbo.order.dal.persistence.OrderMapper;
import clonegod.dubbo.order.dto.DoOrderRequest;
import clonegod.dubbo.order.dto.DoOrderResponse;

@Service(value = "orderService")
public class OrderServiceImpl implements IOrderService {
//
//    @Autowired
//    JtaTransactionManager springTransactionManager;

//    @Autowired
//    OrderMapper orderMapper;


    public DoOrderResponse doOrder(DoOrderRequest request) {
        DoOrderResponse response=new DoOrderResponse();
        response.setCode("000000");
        return response;
    }
}
