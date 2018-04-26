package decorator.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestDecorator {
	public static void main(String[] args) {
		InputStream in = null;
		int c;
		try {
			
			String filepath = "pattern/structure/decorator/decorator/io/test.txt";
			
			in = new LowerCaseInputStream(
								 new BufferedInputStream(
									 new FileInputStream(filepath)));
			while((c=in.read()) != -1) {
				System.out.print((char)c);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try { in.close(); } catch (IOException e) { e.printStackTrace(); }
		}
	}
}
