package juc.demos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import juc.anno.NotThreadSafe;

/**
 * 多线程共享SimpleDateFormat会导致数据安全问题
 * 
 * 解决办法：
 * 	不共享SimpleDateFormat对象
 * 	1. 使用线程本地变量SimpleDateFormat，每次使用都重新创建新的SimpleDateFormat实例。
 *  2. 通过加锁来控制SimpleDateFormat得并发访问。
 */
@NotThreadSafe
public class Test06SimpleDateFormatNotSafe {
	
	public static void main(String[] args) {
		
		Runnable r = new SimpleDateTask(new String[]{"2016-06-06","2016-06-07"});
		
		new Thread(r).start();
		
		new Thread(r).start();
	}
	
}

class SimpleDateTask implements Runnable {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	String[] dateStrArrays;
	
	public SimpleDateTask(String[] dateStrArrays) {
		this.dateStrArrays = dateStrArrays;
	}

	public void run() {
		while(true) {
			try {
				String dateStr = Thread.currentThread().getName().endsWith("0") ? dateStrArrays[0] : dateStrArrays[1];
				Date dd = format(dateStr);
				String text = parse(dd);
				System.out.println(Thread.currentThread().getName()+"============="+text);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	public /*synchronized*/ String parse(Date date) {
		return sdf.format(date);
	}
	
	public /*synchronized*/ Date format(String date) throws ParseException {
		return sdf.parse(date);
	}
	
	
}
