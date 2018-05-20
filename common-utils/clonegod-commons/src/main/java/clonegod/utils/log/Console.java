package clonegod.utils.log;

import java.time.LocalDateTime;

import com.google.common.base.Throwables;

public final class Console {
	
	private Console() {}
	
	public static void log(Object data) {
		if(data == null) {
			data = "";
		}
		LocalDateTime now = LocalDateTime.now();
		System.out.printf("%tF %tT - [%s] => %s\n", 
				now, now, Thread.currentThread().getName(), data.toString());
	}

	public static void error(Throwable t) {
		LocalDateTime now = LocalDateTime.now();
		System.out.printf("%tF %tT - [%s] => %s\n", 
				now,
				now,
				Thread.currentThread().getName(), 
				Throwables.getStackTraceAsString(t));
	}
	
	public static void error(String message, Throwable t) {
		LocalDateTime now = LocalDateTime.now();
		System.out.printf("%tF %tT - [%s] => %s, %s\n", 
				now,
				now,
				Thread.currentThread().getName(), 
				message,
				Throwables.getStackTraceAsString(t));
	}
	
	public static void main(String[] args) {
		log(null);
		log("hello");
		
		error(new NullPointerException());
	}
	
}
