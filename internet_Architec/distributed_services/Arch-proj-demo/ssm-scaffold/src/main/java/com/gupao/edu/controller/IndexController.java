package com.gupao.edu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gupao.edu.controller.support.ResponseData;
import com.gupao.edu.controller.support.ResponseEnum;

import clonegod.dubbo.order.api.IOrderService;
import clonegod.dubbo.order.dto.DoOrderRequest;
import clonegod.dubbo.order.dto.DoOrderResponse;
import clonegod.dubbo.user.api.IUserCoreService;

/**
 */
@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController {

	@Autowired
	IOrderService orderService;

	@Autowired
	IUserCoreService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return "/login";
		}
		return "/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/login";
	}

	@RequestMapping(value = "/submitLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData submitLogin(HttpServletRequest request, String loginname, String password) {
		// post 测试: curl -d "loginname=root&password=root" "http://192.168.1.103:8080/scaffold/index/submitLogin.shtml"
		System.out.println(loginname + "-------" + password);
		
		DoOrderRequest orderReq = new DoOrderRequest();
		orderReq.setPrice(10);
		DoOrderResponse response = orderService.doOrder(orderReq);
		
		/*
		UserLoginRequest request1 = new UserLoginRequest();
		request1.setUsername(loginname);
		request1.setPassword(password);
		UserLoginResponse response = userService.login(request1);
		*/
		
		// login success
		if ("000000".equals(response.getCode())) {
			request.getSession().setAttribute("user", "user");
			return setEnumResult(ResponseEnum.SUCCESS, "/");
		}
		
		// login failed
		ResponseData resData = setEnumResult(ResponseEnum.FAILED, null);
		resData.setMessage(response.getMemo());
		return resData;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute("user");
		} catch (Exception e) {
			LOG.error("errorMessage:" + e.getMessage());
		}
		return redirectTo("/index/login.shtml");
	}
}
