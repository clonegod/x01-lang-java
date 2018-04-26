package decorator.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 装饰模式在IO中的具体应用实例：
 * 	编写一个装饰者，把输入流内的所有大写字符转成小写。
 */
public class LowerCaseInputStream extends FilterInputStream {
	
	protected LowerCaseInputStream(InputStream in) {
		super(in);
	}
	
	@Override
	public int read() throws IOException {
		int c = super.read();
		return (c == -1 ? c : Character.toLowerCase((char)c));
	}
	
	@Override
	public int read(byte[] b, int offset, int len) throws IOException {
		int result = super.read(b, offset, len);
		for(int i = offset; i < offset+result; i++) {
			b[i] = (byte) Character.toLowerCase((char)b[i]);
		}
		return result;
	}
	
}
