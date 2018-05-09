package thread.basic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import juc.GuardedBy;

/**
 * HiddenIterator
 * <p/>
 * Iteration hidden within string concatenation
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Test16HiddenIterator {
    @GuardedBy("this") private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            add(r.nextInt());
        //===> 容器的toString()会迭代容器，此时若有其他线程删除该容器中的元素，则会发生ConcurrentModificationException
        // 解决办法：用synchronized同步，锁使用this锁---与add和remove方法使用同一个锁
        System.out.println("DEBUG: added ten elements to " + set.toString());
    }
}
