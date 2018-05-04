package clonegod.user.service.provider.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import clonegod.user.domain.User;

/**
 * 用户信息的仓储
 *
 */
@Repository
public class UserRepository {

	private ConcurrentHashMap<Long, User> repository = new ConcurrentHashMap<>();

	private AtomicLong  idGenerator = new AtomicLong(0);
	
	public boolean saveUser(User user) {
		long id = idGenerator.incrementAndGet();
		user.setId(id);
		return repository.putIfAbsent(id, user) == null;
	}

	public Collection<User> findAll() {
		return repository.values();
	}
	
}
