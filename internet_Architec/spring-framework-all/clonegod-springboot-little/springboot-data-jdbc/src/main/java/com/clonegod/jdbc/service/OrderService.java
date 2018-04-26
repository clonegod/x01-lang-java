package com.clonegod.jdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clonegod.jdbc.dao.OrderDao;
import com.clonegod.jdbc.model.Order;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Transactional
	public void batchInsert(List<Order> orders) {
		orderDao.batchInsert(orders);
	}
}
