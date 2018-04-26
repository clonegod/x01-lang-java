package juc.sapmles;

public class Test02Volatile {
	
	public static void main(String[] args) throws Exception {
		final CountingSheep cs = new CountingSheep();
		
		new Thread(new Runnable() {
			public void run() {
				cs.tryToSleep();
			}
		}).start();
		
		Thread.sleep(20);
		cs.asleep = true;
	}
	
}

class CountingSheep {
    boolean asleep = false;

    public void tryToSleep() {
        while (!asleep) {
        	countSomeSheep();
        }
    }
    
    private void countSomeSheep() {
        System.out.println("count...");
    }
    
}