package jvm.argument.setting;

import java.util.ArrayList;
import java.util.List;

/**
 * 调试JVM虚拟机参数
 * @author clonegod@163.com
 *	
 *	-Xms10M -Xmx20M  -Xmn10M 
 *
 *
 */
public class TestJVMArguments {
	static List collection = new ArrayList<>();
	static byte[] _1M = new byte[1 * 1024 * 1024];
	public static void main(String[] args) throws Exception {
		for(;;) {
			collection.add(_1M);
			Thread.sleep(100);
		}
	}
	
}
