package clonegod.nio.buffers;

import java.nio.ByteBuffer;

/*
 * byte: 1Byte
 * char,short: 2Byte
 * int,float: 4Byte
 * long,double: 8Byte
 * */
public class TypesInByteBuffer {
	static public void main(String args[]) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(4+8+8);

		buffer.putInt(30); // 4byte
		buffer.putLong(7000000000000L); // 8byte
		buffer.putDouble(Math.PI); // 8byte
		
		System.out.println("buffer剩余可用字节空间："+buffer.remaining());
		
		buffer.flip();

		System.out.println(buffer.getInt());
		System.out.println(buffer.getLong());
		System.out.println(buffer.getDouble());
	}
}
