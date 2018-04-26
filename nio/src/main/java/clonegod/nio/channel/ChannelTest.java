package clonegod.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelTest {
	
	// Only byte buffers can be used with channels
	
	// channel <-> file
	// FileChannel object can be obtained only by calling the getChannel() method 
	// on an open RandomAccessFile, FileInputStream, or FileOutputStream object.
	
	
	// channel <-> socket
	// The socket channels have factory methods to create new socket channels directly.
	// Only stream-oriented channels, such as sockets and pipes, can be placed in nonblocking mode.
	
	
	public static void main(String[] args) throws Exception {
		ReadableByteChannel source = Channels.newChannel(System.in);
		WritableByteChannel dest = Channels.newChannel(System.out);
		
		channelCopy(source, dest);
		source.close();
		dest.close();
	}

	private static void channelCopy(ReadableByteChannel source,
			WritableByteChannel dest) throws IOException {
		ByteBuffer byteBuf = ByteBuffer.allocate(1024);
		
		while(source.read(byteBuf) != -1) {
			byteBuf.flip();
			while(byteBuf.hasRemaining()) {
				dest.write(byteBuf);
			}
			byteBuf.clear();
		}
	}
}
