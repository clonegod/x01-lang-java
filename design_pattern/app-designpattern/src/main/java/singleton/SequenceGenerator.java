package singleton;

import com.hqh.util.AppUtil;

/**
 * 为系统产生唯一序列
 *
 */
public class SequenceGenerator {
	
	private static volatile long seqNumber;
	
	private static final SequenceGenerator instance = new SequenceGenerator();
	
	public static SequenceGenerator getInstance() {
		return instance;
	}
	
	private SequenceGenerator() {
		loadFromDB();
	}
	
	private void loadFromDB() {
		// query previous seqNumber from database
		int previousNumber = 0;
		seqNumber = Math.abs(previousNumber);
	}

	public synchronized String getNextSquence() {
		return AppUtil.parseCurrentDate() + zerofill(++seqNumber, 8);
	}
	
	private static String zerofill(long number, int len) {
		String numStr = String.valueOf(number);
		int currentLen = numStr.length();
		if(currentLen < len) {
			StringBuilder apperder = new StringBuilder();
			for(int i=0; i<(len - currentLen); i++) {
				apperder.append("0");
			}
			numStr = apperder.toString().concat(numStr);
		}
		return numStr;
	}
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++) {
			new Thread(new Runnable() {
				public void run() {
					String next = SequenceGenerator.getInstance().getNextSquence();
					System.out.println(Thread.currentThread().getName() + ": " + next);
				}
			}).start();
		}
	}
}
