package clonegod.nio.channel.filechannel;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class FastCopyFile {
	static public void main(String args[]) throws Exception {
//		if (args.length < 2) {
//			System.err.println("Usage: java FastCopyFile infile outfile");
//			System.exit(1);
//		}

		String infile = "src/main/resources/copyfile/input.txt";
		String outfile = "src/main/resources/copyfile/output.txt";

		FileInputStream fin = new FileInputStream(infile);
		FileOutputStream fout = new FileOutputStream(outfile);

		FileChannel fcin = fin.getChannel();
		FileChannel fcout = fout.getChannel();

		ByteBuffer buffer = ByteBuffer.allocateDirect(1024); // 直接缓冲区（利用操作系统I/O操作的缓冲区）

		while (true) {
			buffer.clear();

			int r = fcin.read(buffer);

			if (r == -1) {
				break;
			}

			buffer.flip();

			fcout.write(buffer);
		}
		
		fin.close();
		fout.close();
	}
}
