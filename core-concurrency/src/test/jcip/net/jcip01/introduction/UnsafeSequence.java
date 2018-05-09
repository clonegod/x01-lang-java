package net.jcip01.introduction;

import net.jcip.annotations.*;

/**
 * UnsafeSequence-----多线程下，生成序列号将出现重复或者丢失的问题
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}