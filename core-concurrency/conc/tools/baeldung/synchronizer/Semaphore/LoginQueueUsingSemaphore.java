package baeldung.synchronizer.Semaphore;

import java.util.concurrent.Semaphore;

/**
 * a simple login queue to limit number users in the system
 * 使用Semaphore对系统同时可登陆用户数进行限制
 */
class LoginQueueUsingSemaphore {

    private final Semaphore semaphore;

    LoginQueueUsingSemaphore(int slotLimit) {
        semaphore = new Semaphore(slotLimit); // 构造多个permit
    }

    /**
     * 非阻塞获取permit
     * @return 获取成功，则返回true，否则返回false
     */
    boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    /**
     * 释放1个permit
     */
    void logout() {
        semaphore.release();
    }

    /**
     * 
     * @return 当前剩余的permit个数
     */
    int availableSlots() {
        return semaphore.availablePermits();
    }

}
