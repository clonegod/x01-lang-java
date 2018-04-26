package decorator.mvc;

import java.io.BufferedReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 模型-负责业务逻辑处理
 * 
 * 此例：
 * 	BufferedReader->装饰模式 + MVC模式
 */
public class GrepReaderMode extends FilterReader {
	
	protected String substring;
	protected BufferedReader in;
	private int lineNumber;

	protected GrepReaderMode(Reader in, String substring) {
		super(in);
		this.in = new BufferedReader(in); // 装饰模式的应用
		this.substring = substring;
		lineNumber = 0;
	}

	public final String readLine() throws IOException {
		String line;
		do {
			line = in.readLine();
			lineNumber++;
		} while((line != null) && 
				line.indexOf(substring) == -1);
		return line;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
}
