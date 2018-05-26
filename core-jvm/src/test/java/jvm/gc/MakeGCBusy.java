package jvm.gc;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MakeGCBusy {
	
	private static final Collection<Object> leak = new ArrayList<Object>();
	
	public static void start() {
		
		for (int i=0; i<100; i++) {
			try {
				Date date = new Date();
				leak.add(date);
				System.out.println(
						MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss.SSS},{1}", date,
								Thread.currentThread().getName())
						);
			} catch (OutOfMemoryError e) {
				leak.clear();
			}
		}
	}
}
