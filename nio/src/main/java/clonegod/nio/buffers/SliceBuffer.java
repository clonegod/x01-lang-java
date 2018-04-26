package clonegod.nio.buffers;

import java.nio.ByteBuffer;

public class SliceBuffer {
	static public void main(String args[]) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(10);

		for (int i = 0; i < buffer.capacity(); ++i) {
			buffer.put((byte) i);
		}
		
		// 指定即将slice的区域
		buffer.position(3);
		buffer.limit(7);

		ByteBuffer slice = buffer.slice();

		for (int i = 0; i < slice.capacity(); ++i) {
			byte b = slice.get(i);
			b *= 11; // 对新buffer的数据进行修改
			slice.put(i, b);
		}
		
		// 读取缓冲区所有内容前，设置内部状态
		buffer.position(0);
		buffer.limit(buffer.capacity());
		
		while (buffer.remaining() > 0) {
			System.out.println(buffer.get());
		}
	}
}
