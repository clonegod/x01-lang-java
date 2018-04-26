package net.jcip03.shareobjects;
/**
 * NoVisibility
 * <p/>
 * Sharing variables without synchronization------共享变量，但没有使用同步，不能保证状态修改后的可见性
 * 	主线程和读线程访问共享状态，但没有使用同步
 * 	有可能导致主线程对共享状态的修改，无法让读线程及时看到---没有同步，不能确保主线程修改后的结果对读线程一定具有可见性！
 * @author Brian Goetz and Tim Peierls
 */

public class NoVisibility {
    private static boolean ready; //共享状态
    private static int number; //共享状态

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) //未同步
                Thread.yield();
            if(number==0)
            	throw new RuntimeException("主线程修改number后的值，没有可见性保证的情况下，读线程读取到默认值0");
            System.out.println(number);//未同步-此处可能输出0. 因为没有同步就不能保证变量修改后的可见性。
            //读线程可能只看到ready的更新值，但对number的更新没有可见性保证，有可能仍然读取到默认值0
            //发生的几率很小，但是仍可能发生！
        }
    }

    public static void main(String[] args) {
		new ReaderThread().start();
		number = 42;//未同步
		ready = true;//未同步
    }
}