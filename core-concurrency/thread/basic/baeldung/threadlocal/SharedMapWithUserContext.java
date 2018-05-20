package baeldung.threadlocal;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Storing User Data in a Map
 * 
 */
public class SharedMapWithUserContext implements Runnable {
	// 共享MAP，因此使用ConcurrentHashMap
    final static Map<Integer, Context> userContextPerUserId = new ConcurrentHashMap<>();
    private final Integer userId;
    private UserRepository userRepository = new UserRepository();

    SharedMapWithUserContext(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContextPerUserId.put(userId, new Context(userName));
    }
}
