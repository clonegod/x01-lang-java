package thread02.singleton;

public class InnerClassSingleton {
	
	private static class Holder {
		private static InnerClassSingleton single = new InnerClassSingleton();
	}
	
	public static InnerClassSingleton getInstance(){
		return Holder.single;
	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(SafeDoubleCheckedSingleton.getDs().hashCode());
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(SafeDoubleCheckedSingleton.getDs().hashCode());
			}
		},"t2");
		
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(SafeDoubleCheckedSingleton.getDs().hashCode());
			}
		},"t3");
		
		t1.start();
		t2.start();
		t3.start();
	}
}
