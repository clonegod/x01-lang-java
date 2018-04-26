package jvm.oom;

public class StackOverflowExceptionTest {
	public static void main(String[] args) {
		for(int i=0; i<10000; i++) {
			final int t = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			System.out.println("Thread " + t +" create");
		}
	}
}
