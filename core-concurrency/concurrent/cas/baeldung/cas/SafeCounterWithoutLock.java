package baeldung.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeCounterWithoutLock {
    private final AtomicInteger counter = new AtomicInteger(0);
    
    int getValue() {
        return counter.get();
    }
    
    void increment() {
        while(true) {
            int existingValue = getValue();
            int newValue = existingValue + 1;
            // 快速失败，再重试
            if(counter.compareAndSet(existingValue, newValue)) {
                return;
            }
        }
    }
}
