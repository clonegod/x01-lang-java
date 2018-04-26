package clonegod.nio.buffers;

import java.nio.ByteBuffer;

public class CreateArrayBuffer {
	static public void main(String args[]) throws Exception {
		byte array[] = new byte[1024];

		ByteBuffer buffer = ByteBuffer.wrap(array); // 利用已有数组，包装为ByteBuffer

		buffer.put((byte) 'a');
		buffer.put((byte) 'b');
		buffer.put((byte) 'c');

		buffer.flip();
		
		// 注意：get(index) 不会造成buffer内部的position和limit改变
		
		System.out.println(buffer.get(0)+":"+(char) buffer.get());
		System.out.println(buffer.get(1)+":"+(char) buffer.get());
		System.out.println(buffer.get(2)+":"+(char) buffer.get());
	}
}
