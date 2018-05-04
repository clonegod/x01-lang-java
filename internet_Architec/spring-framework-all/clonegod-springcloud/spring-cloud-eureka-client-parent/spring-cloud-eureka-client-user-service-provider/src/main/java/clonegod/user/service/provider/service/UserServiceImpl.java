package clonegod.user.service.provider.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import clonegod.user.domain.User;
import clonegod.user.service.UserService;
import clonegod.user.service.provider.repository.UserRepository;

/**
 * 用户服务提供方实现
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean saveUser(User user) {
		return userRepository.saveUser(user);
	}

	@Override
	public Collection<User> findAll() {
		return userRepository.findAll();
	}

}
