package juc.sapmles;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import juc.GuardedBy;
import juc.NotThreadSafe;
import juc.ThreadSafe;

/**
 * MonitorVehicleTracker
 * <p/>
 * Monitor-based vehicle tracker implementation
 *	
 *	基于Java内置监视器锁来实现的车辆为追踪
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
 public class Test0801MonitorVehicleTracker {
    @GuardedBy("this") private final Map<String, MutablePoint> locations;

    public Test0801MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    /**
     * 返回Map的深度复制数据，确保了调用者对结果的修改不会对原有Map造成影响。
     * @return
     */
    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null)
            throw new IllegalArgumentException("No such ID: " + id);
        loc.x = x;
        loc.y = y;
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();

        for (String id : m.keySet())
            result.put(id, new MutablePoint(m.get(id)));

        return Collections.unmodifiableMap(result);
    }
}



/**
 * MutablePoint
 * <p/>
 * Mutable Point class similar to java.awt.Point
 *
 * @author Brian Goetz and Tim Peierls
 */
@NotThreadSafe
class MutablePoint {
    public int x, y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}