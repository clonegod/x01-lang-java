package clonegod.dubbo.order.impl;

import java.util.UUID;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

import clonegod.dubbo.order.api.IOrderService;
import clonegod.dubbo.order.dao.IOrderDao;
import clonegod.dubbo.order.dto.OrderRequest;
import clonegod.dubbo.order.dto.OrderResponse;
import clonegod.dubbo.user.api.IUserService;
import clonegod.dubbo.user.dto.DebitRequest;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	@Autowired
	IUserService userService;
	
	@Autowired
	IOrderDao orderDao;
	
    @Autowired
    JtaTransactionManager springTransactionManager;
	
	@Override
	public OrderResponse doOrder(OrderRequest request) {
		System.out.println(Thread.currentThread().getName() + "：开始处理订单请求");
		
		OrderResponse res = new OrderResponse();
		res.setCode("000000");
		res.setMemo("success");
		res.setOrderNo(UUID.randomUUID().toString());
		
		// 事务的三种写法：编程式事务、声明式事务、注解类型事务 - 对分布式事务同样适用
		// order下单后，更新user余额
		UserTransaction userTransaction = springTransactionManager.getUserTransaction();
		try {
			userTransaction.begin(); // 开始分布式事务 - 2pc 两阶段提交
			
			orderDao.insertOrder();
			userService.debit(new DebitRequest(10));
			
			userTransaction.commit(); // 提交分布式事务
		} catch (Exception e) {
			e.printStackTrace();
			try {
				userTransaction.rollback(); // 回滚分布式事务
				res.setCode("000001");
				res.setMemo("failed");
				res.setOrderNo(null);
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
		}
		
		return res;
	}

}
