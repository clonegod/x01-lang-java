package com.clonegod.demo.service;

import java.util.Map;

public interface UserService {
	
	Map<String, Object> getTableData(int pageNum, int pageSize, String username);
}
