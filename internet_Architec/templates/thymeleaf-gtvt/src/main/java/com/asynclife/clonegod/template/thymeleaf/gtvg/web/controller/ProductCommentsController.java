package com.asynclife.clonegod.template.thymeleaf.gtvg.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.asynclife.clonegod.template.thymeleaf.gtvg.business.entities.Product;
import com.asynclife.clonegod.template.thymeleaf.gtvg.business.services.ProductService;

@Controller
@RequestMapping("/gtvg/")
public class ProductCommentsController {
	
	@RequestMapping("/product/comments")
    public ModelAndView process(ModelAndView mav, @RequestParam Integer prodId) {
        
        //final Integer prodId = Integer.valueOf(request.getParameter("prodId"));
        
        final ProductService productService = new ProductService();
        final Product product = productService.findById(prodId);
        
        mav.addObject("prod", product);
        mav.setViewName("gtvg/product/comments");
        
        return mav;
    }

}
