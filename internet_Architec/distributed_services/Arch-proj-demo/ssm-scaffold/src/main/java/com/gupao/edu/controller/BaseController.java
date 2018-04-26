package com.gupao.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gupao.edu.controller.support.ResponseData;
import com.gupao.edu.controller.support.ResponseEnum;

public class BaseController {
	public Logger LOG = LoggerFactory.getLogger(BaseController.class);

	protected String redirectTo(String url) {
		StringBuilder rto = new StringBuilder("redirect:");
		rto.append(url);
		return rto.toString();
	}

	protected ResponseData setEnumResult(ResponseEnum anEnum, Object data) {
		ResponseData res = new ResponseData();
		res.setStatus(anEnum.getCode());
		res.setData(data);
		res.setMessage(anEnum.getMsg());
		return res;
	}
}
