package clonegod.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Test;

import com.google.common.primitives.Bytes;

public class BufferInnerStateTest {
	
	/**
	 * ByteBuffer提供了便利的方法用来写入基本数据类型
	 * 写入与读取的数据类型要一致，如putInt() 写入的数据要使用 getInt()来读取。
	 */
	@Test
	public void testPutAndGet() {
		ByteBuffer buf = ByteBuffer.allocate(10);
		
		buf.putInt(200);
		System.out.println(buf.position());
		
		buf.putShort((short)18);
		System.out.println(buf.position());
		
		buf.flip();
		System.out.println(buf.getInt());
		System.out.println(buf.getShort());
	}
	
	/**
	 * wrap：将已有数组作为buffer的内部元素，buffer的容量由数组决定。
	 * 	修改buffer会影响底层array的元素，反之亦然。
	 */
	@Test
	public void testWarpArray() {
		byte[] bytes = {(byte)'H', (byte)'e', (byte)'l', (byte)'l', (byte)'o'};
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		print(buf);
		
		buf.put(4, (byte)'?');
		
		buf.rewind();
		print(buf);
		// 元素数组也被修改
		Bytes.asList(bytes).forEach(x -> {System.out.print((char)x.byteValue());}); 
	}
	
	/**
	 * flip: limit = position, position=0
	 * 	往buffer写入数据之后，需要读取buffer中的数据时，执行flip操作调整positon和limit的位置。
	 */
	@Test
	public void testFilp() {
		ByteBuffer buf = ByteBuffer.allocate(5);
		buf.put((byte)'A');
		buf.put((byte)'B');
		buf.put((byte)'C');
		buf.flip();
		print(buf);
	}
	
	/**
	 * rewind: position=0, 将指针移动到队头，对再次从起始位置读取数据，直到limit结束
	 */
	@Test
	public void testRewind() {
		CharBuffer cf = CharBuffer.allocate(10);
		cf.put("Hello");
		cf.flip();
		print(cf);
		print(cf);
		
		cf.rewind();
		print(cf);
	}
	
	/**
	 * compact-复制未读取元素到队头，然后将position设置为n+1，limit设置为capacity，准备继续写入新元素
	 * 	当buffer被部分读取之后，还有剩余未读元素，此时再次写入时，需要将剩余字符移动到队头，然后接着写入
	 */
	@Test
	public void testCompact() {
		CharBuffer cf = CharBuffer.allocate(10);
		cf.put('A'); cf.put('B'); cf.put('C');
		
		cf.flip();
		cf.get(); // 读取第1个字符
		
		cf.compact(); // 整理缓冲区，将剩余未读字符往前移动到队头（A字符将被覆盖）
		cf.put('a');
		cf.flip();
		print(cf);
	}
	
	/**
	 * mark - 标记position
	 * reset - 恢复position到mark记录的位置
	 * 注意：flip,compact等方法会清除mark
	 */
	@Test
	public void testMarkAndRest() {
		CharBuffer cf = CharBuffer.allocate(5);
		cf.put('H'); cf.put('e'); cf.put('l'); cf.put('l'); cf.put('o');
		
		cf.position(1).mark(); // mark position = 1
		cf.get(); // position = 2
		print(cf);
		
		cf.reset(); // position = 1
		print(cf);
	}
	
	/**
	 * clear: position=0, limit=capacity, discard mark
	 * 	重置内部状态到初始状态---重置内部状态，重新从position=0开始写入新的数据。
	 */
	@Test
	public void testClear() {
		CharBuffer cf = CharBuffer.allocate(5);
		
		cf.put('H'); cf.put('e'); cf.put('l'); cf.put('l'); cf.put('o');
		cf.flip();
		print(cf);
		
		cf.clear();
		
		cf.put('W'); cf.put('e'); cf.put('l'); cf.put('l'); cf.put('o');
		cf.flip();
		print(cf);
		
	}
	
	private void print(ByteBuffer buffer) {
		System.out.println(String.format("position=%d, limit=%d, capacity=%d",
				buffer.position(), buffer.limit(), buffer.capacity())
				);
		while(buffer.hasRemaining()) {
			System.out.print((char)buffer.get());
		}
		System.out.println();
	}
	
	private void print(CharBuffer buffer) {
		System.out.println(String.format("position=%d, limit=%d, capacity=%d",
				buffer.position(), buffer.limit(), buffer.capacity())
				);
		while(buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();
	}
}
