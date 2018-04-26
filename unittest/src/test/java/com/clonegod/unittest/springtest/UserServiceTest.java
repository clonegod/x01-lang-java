package com.clonegod.unittest.springtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.clonegod.unittest.dao.UserDao;
import com.clonegod.unittest.model.User;
import com.clonegod.unittest.service.UserService;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from the static inner Config class
@ContextConfiguration
public class UserServiceTest {

    @Configuration
    static class Config {
        // this bean will be injected into the UserServiceTest class
        @Bean
        public UserService userService() {
        	UserService userService = new UserService();
            // set properties, etc.
            return userService;
        }
        
        @Bean
        public UserDao userDao() {
        	UserDao userDao = new UserDao();
        	// set properties, etc.
        	return userDao;
        }
    }

    @Autowired
    private UserService userService;

    @Test
    public void testUserService() {
        // test the orderService
    	User user  = userService.findUser("");
    	assertNotNull(user);
    }

}