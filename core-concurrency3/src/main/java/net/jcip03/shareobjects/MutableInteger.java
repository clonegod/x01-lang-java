package net.jcip03.shareobjects;
import net.jcip.annotations.*;

/**
 * MutableInteger------未使用同步的可变状态
 * <p/>
 * Non-thread-safe mutable integer holder
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class MutableInteger {
    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}