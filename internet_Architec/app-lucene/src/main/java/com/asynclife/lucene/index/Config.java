package com.asynclife.lucene.index;

import java.util.HashMap;
import java.util.Map;

public class Config {

	public static Map<String, Float> BOOST_MAP = new HashMap<String, Float>();

	// 数字越高，权重越大
	// 影响权值的主要因素：1. 权值的大小 2. 目标出现的次数
	static {
		BOOST_MAP.put("126.com", 1.0f);
		BOOST_MAP.put("163.com", 2.0f);
	}

}
