package clonegod.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

import org.junit.Assert;
import org.junit.Test;

public class BufferTest {
	
	@Test
	public void testBufferProperty() {
		
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.put((byte)'H').put((byte)'e').put((byte)'l').put((byte)'l').put((byte)'o');
		buf.mark(); // mark = position
		buf.put((byte)'p'); // p将被最后的w覆盖
		buf.reset(); // position = mark
		
		buf.flip(); // limit = position; position = 0; & discard mark
		System.out.println(buf.position());
		System.out.println(buf.limit());
		readBytesInBufferTwice(buf);
		
		System.out.println(buf.position());
		buf.limit(buf.capacity());
		System.out.println(buf.limit());
		buf.put(0, (byte)'M').put((byte)'w'); // 绝对位置（基于索引）的put不会改变position
		
		buf.flip();
		readBytesInBufferTwice(buf);
		
		buf.rewind(); // position=0; reread the data in a buffer that has already been flipped.
		readBytesInBufferTwice(buf);
		
		
		buf.clear(); // It doesn't change any of the data elements of the buffer but simply sets the limit to the capacity and the position back to 0
		System.out.println("after clear");
		readBytesInBufferTwice(buf);
		
		
	}
	
	private static void readBytesInBufferTwice(ByteBuffer buf) {
		while(buf.hasRemaining()) {
			byte b = buf.get();
			System.out.print((char)b);
		}
		
		System.out.print("\t");
		
		buf.rewind(); // position置0，重复读buffer
		int count = buf.remaining();
		for(int i=0; i<count; i++) {
			byte b = buf.get();
			System.out.print((char)b);
		}
		System.out.println();
	}
	
	
	static int index = 0;
	static String [] strings = {
		"A random string value",
		"The product of an infinite number of monkeys",
		"Hey hey we're the Monkees",
		"Opening act for the Monkees: Jimi Hendrix",
		"'Scuse me while I kiss this fly", 
		"Help Me! Help Me!",
	};
	@Test
	public void testBufferFillDrain() {
		
		CharBuffer buffer = CharBuffer.allocate(100);
		
		while(fillBuffer(buffer)) {
			buffer.flip();
			drainBuffer(buffer);
			buffer.clear();
		}
	}

	private void drainBuffer(CharBuffer buffer) {
		while(buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();
	}

	private boolean fillBuffer(CharBuffer buffer) {
		if(index >= strings.length) {
			return false;
		}
		
		String string = strings[index];
		++index;
		for(int i=0; i<string.length(); i++) {
			buffer.put(string.charAt(i));
		}
		return true;
	}
	
	@Test
	public void testCompact() throws Exception {
		String text = "LEARN_NIO";
		ByteBuffer buf = ByteBuffer.allocate(100);
		for(int i=0; i<text.length(); i++) {
			buf.put((byte)text.charAt(i));
		}
		
		ByteBuffer firstWord = ByteBuffer.allocate(5);
		byte[] dst1 = firstWord.array();
		
		buf.flip();
		buf.get(dst1, 0, "LEARN".length());
		readBytesInBufferTwice(firstWord);
		
		buf.compact(); //the unread data elements need to be shifted down so that the first element is at index zero
		buf.put("_BUFFER".getBytes()); // overwritten data which start at the current position
//		ByteBuffer secondWord = ByteBuffer.allocate(5);
//		byte[] dst2 = secondWord.array();
//		buf.get(dst2, 1, "NIO".length());
		
		buf.flip(); // If you want to drain the buffer contents after compaction, the buffer will need to be flipped as discussed earlier.
		readBytesInBufferTwice(buf);
	}
	
	@Test
	public void testSlice() {
		CharBuffer cb = CharBuffer.allocate(10);
		cb.put("你好中国天安门");
		
		cb.position(2).limit(4);
		CharBuffer sliced = cb.slice(); // The content of the new buffer will start at this buffer's current position end at limit
		System.out.println(sliced.toString());
		
	}
	
	@Test
	public void testByteOrder() {
		String nativeOrder = ByteOrder.nativeOrder().toString();
		System.out.println(nativeOrder);
		
		// 多字节的基本数据类型，其存储方式有两种：
		// ByteOrder.BIG_ENDIAN; 高字节排前面（从左到右存放）
		// ByteOrder.LITTLE_ENDIAN; 低字节排前面（从左到右存放）
		
		int i = 0x3BC5315E; //1002778974
		System.out.println(i);
		
		ByteBuffer BIG_ENDIAN_BUF = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
		BIG_ENDIAN_BUF.put(0, (byte)0x3B);
		BIG_ENDIAN_BUF.put(1, (byte)0xC5);
		BIG_ENDIAN_BUF.put(2, (byte)0x31);
		BIG_ENDIAN_BUF.put(3, (byte)0x5E);
		
		IntBuffer intBuffer = BIG_ENDIAN_BUF.asIntBuffer();
		Assert.assertEquals(intBuffer.get(), 1002778974);
		
		ByteBuffer LITTLE_ENDIAN_BUF = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		LITTLE_ENDIAN_BUF.put(0, (byte)0x5E);
		LITTLE_ENDIAN_BUF.put(1, (byte)0x31);
		LITTLE_ENDIAN_BUF.put(2, (byte)0xC5);
		LITTLE_ENDIAN_BUF.put(3, (byte)0x3B);
		
		IntBuffer intBuffer2 = LITTLE_ENDIAN_BUF.asIntBuffer();
		Assert.assertEquals(intBuffer2.get(), 1002778974);
		
		ByteBuffer buf = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
		buf.putInt(1002778974);
		Assert.assertArrayEquals(buf.array(), BIG_ENDIAN_BUF.array());
	}
	
	@Test
	public void testDirectBufferAndReuseIt() {
		//if you will be using the buffer repeatedly in a high-performance scenario, 
		//you're better off allocating direct buffers and reusing them.
		//direct buffers are not subject to garbage collection because they are outside the standard JVM heap.
		//I recommend the old software maxim: first make it work, then make it fast. 
		
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024);
		System.out.println(directBuffer.isDirect());
		
	}
	
	@Test
	public void testMappedByteBuffer() {
		//Mapped buffers are always direct and can be created only from a FileChannel object.
		
		
	}
	
	/**
	 * byte		1*8bit
	 * char		2*8bit
	 * short	2*8bit
	 * int		4*8bit
	 * long		8*8bit
	 * float	4*8bit
	 * double	8*8bit
	 */
	@Test
	public void testBufferView() {
		// 字节数组的字符VIEW，按每2个byte转1个char处理
		// 指定字节的存放顺序ByteOrder.BIG_ENDIAN 高位字节在左边
		ByteBuffer byteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
		
		// 第1个字符
		byteBuffer.put(0, (byte)0);
		byteBuffer.put(1, (byte)'H');
		
		// 第2个字符
		byteBuffer.put(2, (byte)0);
		byteBuffer.put(3, (byte)'i');
		
		// 第3个字符
		byteBuffer.put(4, (byte)0);
		byteBuffer.put(5, (byte)'!');
		
		// 仅有1个byte，无法转为为char，忽略
		byteBuffer.put(6, (byte)0);
		
		println(byteBuffer);

		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		println(charBuffer);
		System.out.println(charBuffer.order());
		
		
	}

	private void println(Buffer buffer) {
		System.out.println ("pos=" + buffer.position()
				+ ", limit=" + buffer.limit()
				+ ", capacity=" + buffer.capacity()
				+ ": '" + buffer.toString() + "'");
	}
}
