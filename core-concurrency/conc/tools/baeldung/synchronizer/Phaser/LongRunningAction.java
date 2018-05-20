package baeldung.synchronizer.Phaser;

import java.util.concurrent.Phaser;

class LongRunningAction implements Runnable {
    private String threadName;
    private Phaser ph;

    LongRunningAction(String threadName, Phaser ph) {
        this.threadName = threadName;
        this.ph = ph;
        ph.register(); // 绑定新的party到Phaser
    }

    @Override
    public void run() {
        System.out.println("This is phase " + ph.getPhase() + ", " + threadName + " before long running action");
        ph.arriveAndAwaitAdvance();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        	System.out.println("干完活了，解除与Phaser的绑定, phase=" + ph.getPhase());        	
        	ph.arriveAndDeregister(); // 解除绑定，Phaser中的注册数减少1
        }
    }
}