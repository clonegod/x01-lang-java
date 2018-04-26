package com.clonegod.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clonegod.demo.dao.UserMapper;
import com.clonegod.demo.model.User;
import com.clonegod.demo.service.UserService;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userRepository;

	@Override
	public Map<String, Object> getTableData(int pageNum, int pageSize, String username) {
		Map<String, Object> tableData = new HashMap<>();
		try {
			// 设置分页参数
			PageHelper.startPage(pageNum, pageSize);
			
			List<User> userList = userRepository.findUserByUsername(username);
			int count = userRepository.getCount(username);
			
			tableData.put("list", userList);
			tableData.put("count", count);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return tableData;
	}
}
