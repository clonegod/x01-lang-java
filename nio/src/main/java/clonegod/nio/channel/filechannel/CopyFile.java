package clonegod.nio.channel.filechannel;

// $Id$

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class CopyFile
{
  static public void main( String args[] ) throws Exception {
//    if (args.length<2) {
//      System.err.println( "Usage: java CopyFile infile outfile" );
//      System.exit( 1 );
//    }

    String infile = "src/main/resources/copyfile/input.txt";
    String outfile = "src/main/resources/copyfile/output.txt";

    FileInputStream fin = new FileInputStream( infile );
    FileOutputStream fout = new FileOutputStream( outfile );

    FileChannel fcin = fin.getChannel();
    FileChannel fcout = fout.getChannel();

    ByteBuffer buffer = ByteBuffer.allocate( 1024 );

    while (true) {
      buffer.clear(); // 读之前，先清空缓冲区

      int r = fcin.read( buffer ); // 将数据从channel读入buffer

      if (r==-1) {
        break;
      }

      buffer.flip(); // 每次读入完成后，重新设置limit和position的位置：等效于limit=position, position=0

      fcout.write( buffer ); // 将数据从缓冲区写出到channel
    }
    
    fin.close();
    fout.close();
  }
}
