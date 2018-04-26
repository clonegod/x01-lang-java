package com.clonegod.respository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.clonegod.api.User;

@Repository("userRepository")
public class UserRepository {
	
    private final ConcurrentMap<Long, User> repository = new ConcurrentHashMap<>();
    
    
    @PostConstruct
    public void init() {
    	repository.put(100L, User.builder().id(100L).name("ClongGod").build());
    }

    private final AtomicLong idGenerator = new AtomicLong();

    public Boolean save(User user) {
        // ID 从 1 开始
        long id = idGenerator.incrementAndGet();
        user.setId(id);
        // 1 -> user
        // 1 -> user1 -> user return
        System.out.printf("[Thread: %s] save user: %s\n", 
        		Thread.currentThread().getName(), user);
        return repository.put(id, user) == null;
    }

    // ValuesView - 对返回的集合不支持add/addAll操作
    public Collection<User> findAll() {
    	System.out.println("-------findAll:" + repository);
        return repository.values();
    }

	public User findOne(Long id) {
		return repository.get(id);
	}

}
