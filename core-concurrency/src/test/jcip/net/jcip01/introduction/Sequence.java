package net.jcip01.introduction;

import net.jcip.annotations.*;

/**
 * Sequence------生成序列值，使用synchronized进行同步，确保每次产生1个新值
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
public class Sequence {
	
	private Sequence() {}
	
	
	
    @GuardedBy("this") private int nextValue;

    public synchronized int getNext() {
        return nextValue++;
    }
}