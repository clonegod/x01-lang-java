package clonegod.zookeeper.sessionid;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * zookeeper内部生成会话id的算法
 *
 */
public class GenerateSessionID {
	
	final static AtomicInteger serverId = new AtomicInteger(0);
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(initializeNextSession(serverId.incrementAndGet()));
				}
			}).start();
		}
	}
	
	/**
     * Generates an initial sessionId. High order byte is serverId, next 5
     * 5 bytes are from timestamp, and low order 2 bytes are 0s.
     */
    public static long initializeNextSession(long id) {
        long nextSid;
        nextSid = (Time.currentElapsedTime() << 24) >>> 8;
        nextSid =  nextSid | (id <<56);
        return nextSid;
    }
    
    static class Time {
        /**
         * Returns time in milliseconds as does System.currentTimeMillis(),
         * but uses elapsed time from an arbitrary epoch more like System.nanoTime().
         * The difference is that if somebody changes the system clock,
         * Time.currentElapsedTime will change but nanoTime won't. On the other hand,
         * all of ZK assumes that time is measured in milliseconds.
         * @return  The time in milliseconds from some arbitrary point in time.
         */
        public static long currentElapsedTime() {
            return System.nanoTime() / 1000000;
        }

        /**
         * Explicitly returns system dependent current wall time.
         * @return Current time in msec.
         */
        public static long currentWallTime() {
            return System.currentTimeMillis();
        }

        /**
         * This is to convert the elapsedTime to a Date.
         * @return A date object indicated by the elapsedTime.
         */
        public static Date elapsedTimeToDate(long elapsedTime) {
            long wallTime = currentWallTime() + elapsedTime - currentElapsedTime();
            return new Date(wallTime);
        }
    }
	
}
