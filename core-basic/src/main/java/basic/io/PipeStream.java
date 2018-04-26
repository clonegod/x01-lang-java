/*
 * 高效对接流数据,通常数据从某个线程使用PipedInputStream读取，其它线程将其写入到PipedOutputStream
即：1个线程负责读，1个线程负责写
注意：不建议单线程操作这两个对象！！！可能造成死锁！！！
 */
package basic.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class PipeStream {

    public static void main(String[] args) throws Exception {
        final PipedInputStream input = new PipedInputStream(1024 * 8);
        final PipedOutputStream output = new PipedOutputStream();

        // output中写出的数据将自动转接到input流中
        input.connect(output);

        new Thread(() -> {
            try {
                byte[] buf = new byte[1024];
                int len = input.read(buf);
                System.out.println("str=" + new String(buf, 0, len));
            } catch (Exception e) {
                Logger.getLogger(PipeStream.class.getName()).log(Level.SEVERE, null, e);
            }

        }).start();

        new Thread(() -> {
            try {
                output.write("管道流来了".getBytes());
            } catch (IOException ex) {
                Logger.getLogger(PipeStream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }
}
