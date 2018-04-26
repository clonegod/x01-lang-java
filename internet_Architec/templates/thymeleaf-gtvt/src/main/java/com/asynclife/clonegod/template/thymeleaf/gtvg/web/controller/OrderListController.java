package com.asynclife.clonegod.template.thymeleaf.gtvg.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.asynclife.clonegod.template.thymeleaf.gtvg.business.entities.Order;
import com.asynclife.clonegod.template.thymeleaf.gtvg.business.services.OrderService;

@Controller
@RequestMapping("/gtvg")
public class OrderListController {
	
	@RequestMapping("/order/list")
    public ModelAndView process(ModelAndView mav) {
        
        final OrderService orderService = new OrderService();
        final List<Order> allOrders = orderService.findAll(); 
        
        mav.addObject("orders", allOrders);
        mav.setViewName("gtvg/order/list");
        
        return mav;
    }

}
