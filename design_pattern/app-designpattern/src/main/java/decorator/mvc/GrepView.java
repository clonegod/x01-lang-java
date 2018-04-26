package decorator.mvc;

import java.io.PrintStream;

/**
 * 视图
 *
 */
public class GrepView {
	PrintStream out;
	
	public GrepView() {
		out = System.out;
	}
	
	public void println(String line) {
		out.println(line);
	}
	
}	
