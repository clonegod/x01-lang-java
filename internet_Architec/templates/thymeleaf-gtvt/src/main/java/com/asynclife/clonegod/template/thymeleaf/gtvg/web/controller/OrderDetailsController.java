package com.asynclife.clonegod.template.thymeleaf.gtvg.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.asynclife.clonegod.template.thymeleaf.gtvg.business.entities.Order;
import com.asynclife.clonegod.template.thymeleaf.gtvg.business.services.OrderService;

@Controller
@RequestMapping("/gtvg")
public class OrderDetailsController {

    
	@RequestMapping("/order/details")
    public ModelAndView process(ModelAndView mav, @RequestParam Integer orderId) {
        
        //final Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        
        final OrderService orderService = new OrderService();
        final Order order = orderService.findById(orderId);
        
        mav.addObject("order", order);
        mav.setViewName("gtvg/order/details");
        
        return mav;
        
    }

}
