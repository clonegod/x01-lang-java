package juc.demos;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import juc.anno.Immutable;
import juc.anno.ThreadSafe;

/**
 * DelegatingVehicleTracker
 * <p/>
 * Delegating thread safety to a ConcurrentHashMap
 * 
 * ConcurrentHashMap 管理了所有对状态的访问。
 *  委托线程安全类：基于线程安全的ConcurrentHashMap来实现，没有使用任何显示的同步。
 *	同时，使用不可变的Point类取代MutablePoint类，来存储location信息，防止对象逸出。
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class Test0802DelegatingVehicleTracker {
    private final ConcurrentMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public Test0802DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<String, Point>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }
    
    /**
     * 返回Map的不可变拷贝，调用者无法添加或删除车辆信息，由于Point是不可变的，因此也保证了调用者无法修改机动车的位置！
     * @return
     */
    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    /**
Replaces the entry for a key only if currently mapped to some value. This is equivalent to 
 
 if (map.containsKey(key)) {
   return map.put(key, value);
 } else
   return null;
 }
 
except that the action is performed atomically.
     */
    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null)
            throw new IllegalArgumentException("invalid vehicle name: " + id);
    }

    // Alternate version of getLocations (Listing 4.8)
    public Map<String, Point> getLocationsAsStatic() {
        return Collections.unmodifiableMap(
                new HashMap<String, Point>(locations));
    }
}

@Immutable
class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
