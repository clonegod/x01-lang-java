package clonegod.commons.log;

import java.time.LocalDateTime;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

public final class Console {
	
	private Console() {}
	
	public static void log(Object data) {
		Preconditions.checkNotNull(data, "data不能为null");
		
		System.out.printf("%s - [%s] => %s\n", 
				LocalDateTime.now(), Thread.currentThread().getName(), data.toString());
	}

	public static void error(Throwable t) {
		System.out.printf("%s - [%s] => %s\n", 
				LocalDateTime.now(), 
				Thread.currentThread().getName(), 
				Throwables.getStackTraceAsString(t));
	}
	
}
