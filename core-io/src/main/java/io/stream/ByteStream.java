/*
 * 源和目的都是内存的流操作
 */
package io.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author Administrator
 */
public class ByteStream {

    public static void main(String[] args) throws Exception {
        // 内存中的数组
        byte[] someBytesInMemory = "你好".getBytes();

        ByteArrayInputStream bais = new ByteArrayInputStream(someBytesInMemory);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c = 0;
        while ((c = bais.read()) != -1) {
            baos.write(c);
        }

        byte[] bytes = baos.toByteArray();
        System.out.println(new String(bytes));
    }
}
