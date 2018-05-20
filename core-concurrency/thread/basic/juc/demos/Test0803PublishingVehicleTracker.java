package juc.demos;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import juc.anno.GuardedBy;
import juc.anno.ThreadSafe;

/**
 * PublishingVehicleTracker
 * <p/>
 * Vehicle tracker that safely publishes underlying state
 *	发布了底层状态的机动车追踪器
 *	使用可变但是线程安全的Point类
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class Test0803PublishingVehicleTracker {
    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public Test0803PublishingVehicleTracker(Map<String, SafePoint> locations) {
        this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    /**
     * 返回Map的不可变拷贝，调用者无法添加或删除车辆信息，却可以通过修改返回Map中的SafePoint的值，来修改机动车的位置！
     * @return
     */
    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id))
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        locations.get(id).set(x, y);
    }
}

/**
 * SafePoint
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
class SafePoint {
    @GuardedBy("this") private int x, y;

    private SafePoint(int[] a) {
        this(a[0], a[1]);
    }

    public SafePoint(SafePoint p) {
        this(p.get());
    }

    public SafePoint(int x, int y) {
        this.set(x, y);
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}