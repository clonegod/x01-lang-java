/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package com.asynclife.clonegod.template.thymeleaf.gtvg.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.asynclife.clonegod.template.thymeleaf.gtvg.business.entities.Product;
import com.asynclife.clonegod.template.thymeleaf.gtvg.business.services.ProductService;

@Controller
@RequestMapping("/gtvg")
public class ProductListController {
    
	@RequestMapping("/product/list")
    public ModelAndView process(ModelAndView mav) throws Exception {
        
        final ProductService productService = new ProductService();
        final List<Product> allProducts = productService.findAll(); 
        
        mav.addObject("prods", allProducts);
        mav.setViewName("gtvg/product/list");
        
        return mav;
        
    }

}
