package juc.demos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import juc.anno.NotThreadSafe;
import juc.anno.ThreadSafe;

public class Test09AddNewFeatureToThreadSafe {

}


/**
 * ListHelder
 * <p/>
 * Examples of thread-safe and non-thread-safe implementations of
 * put-if-absent helper methods for List
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
class BadListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    // 该方法使用的锁，与同步list内部的锁不是同一个锁。同步list内部使用this作为锁。
    // 因此无法保证putIfAbsent方法的原子性。
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent)
            list.add(x);
        return absent;
    }
}

@ThreadSafe
class GoodListHelper <E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());
    
    // 使用相同锁，确保了同步的正确性。
    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}