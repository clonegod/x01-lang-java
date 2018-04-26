package clonegod.nio.channel.filechannel;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class UseMappedFile {
	static private final int start = 0;
	static private final int size = 1024;

	static public void main(String args[]) throws Exception {
		RandomAccessFile raf = new RandomAccessFile("src/main/resources/mappedfile/usemappedfile.txt", "rw");
		FileChannel fc = raf.getChannel();
		
		// 基于内存模式的快速读写
		MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start,
				size);

		mbb.put(0, (byte) 97);
		mbb.put(1, (byte) 65);
		mbb.put(1023, (byte) 122);

		raf.close(); //上述修改将直接作用到磁盘文件上
	}
}
