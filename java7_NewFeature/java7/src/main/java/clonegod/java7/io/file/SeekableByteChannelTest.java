package clonegod.java7.io.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SeekableByteChannelTest {
	
	/**
	 * 按给定位置读取文件中的字符
	 * 基于此特性，可以使用多线程读取文件的不同位置，快速提取数据
	 * 另一个应用场景：
	 * 	日志文件中每行的字节数固定，读取指定行时计算出偏移量即可快速提取到目标行的内容
	 */
	public static void main(String[] args) {
		try {
			Path logFile = Paths.get("tmp/transaction.log");
			int lineSeparatorLen = System.getProperty("line.separator").getBytes().length;
			int sizePerLine = 6 + lineSeparatorLen;
			int lineNo = 3;
			ByteBuffer buffer = ByteBuffer.allocate(sizePerLine);
			
			FileChannel channel = FileChannel.open(logFile, StandardOpenOption.READ);
			channel.read(buffer, (lineNo - 1) * sizePerLine);
			
			buffer.flip();
			System.out.println(new String(buffer.array(), StandardCharsets.UTF_8));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
