package baeldung.synchronization.synchronize;

public class BaeldungSynchronizedBlocks {

    private int count = 0;
    private static int staticCount = 0;

    /**
     * 实例方法的锁-this
     */
    void performSynchronisedTask() {
        synchronized (this) {
            setCount(getCount() + 1);
        }
    }

    /**
     * 静态方法的锁-Class.class
     */
    static void performStaticSyncTask() {
        synchronized (BaeldungSynchronizedBlocks.class) {
            setStaticCount(getStaticCount() + 1);
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    static int getStaticCount() {
        return staticCount;
    }

    private static void setStaticCount(int staticCount) {
        BaeldungSynchronizedBlocks.staticCount = staticCount;
    }
}
