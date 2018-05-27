package jvm.TLAB;

/*
TLAB : 在Eden区，为每个线程分配一个专用的内存块（TLAB），用于分配新创建的对象。这样可以避免多线程在申请内存发生竞争。可以理解为TLAB能加速对象在内存中的分配。

-XX:+UseTLAB    开启TLAB
-XX:+PrintTLAB  打印TLAB信息
-XX:+TLABSize   设置TLAB大小
-XX:ResizeTLAB  自调整TLABRefillWasteFraction 阀值，默认true

 */
public class TestTLAB {
	
	public static void alloc(){
        byte[] b = new byte[100];
        byte[] b2 = new byte[100];
    }
    
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        for(int i=0; i<200000000;i++){
            alloc();
        }
        System.out.println("耗时"+(System.currentTimeMillis() - begin));
        // -XX:+UseTLAB   	耗时8099
        // -XX:-UseTLAB 	耗时10250
    }
}
