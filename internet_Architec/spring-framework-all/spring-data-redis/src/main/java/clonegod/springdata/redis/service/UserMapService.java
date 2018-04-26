package clonegod.springdata.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.stereotype.Service;

import clonegod.springdata.redis.domain.Cachable;
import clonegod.springdata.redis.domain.User;


@Service("userMapService")
public class UserMapService implements IService<User> {
	
	@Autowired
	DefaultRedisMap<String, Cachable> userRedisMap;
	
	@Override
	public void put(User user) {
		userRedisMap.put(user.getKey(), user);
	}

	@Override
	public void delete(User key) {
		userRedisMap.remove(key.getKey());
	}

	@Override
	public User get(User key) {
		return (User) userRedisMap.get(key.getKey());
	}
}