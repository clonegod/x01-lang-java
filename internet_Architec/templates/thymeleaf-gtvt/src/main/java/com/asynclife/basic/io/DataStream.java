/*
 * 专门用于操作基本数据类型的流：可解决写Int时只写了低8位的问题，其它数据类型类似！对于字符串，将采用UTF-8修改版进行数据转换！
 */
package com.asynclife.basic.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Administrator
 */
public class DataStream {

    public static void main(String[] args) throws Exception {

        // 用UTF-8修改版操作字符串（在数据流前面增加2个byte来描述数据长度）
        // 用DataOutputStream写出的数据，只能由DataInputStream进行读取
        writeUTF();
        readUTF();
    }

    private static void writeUTF() throws Exception {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));

        dos.writeInt(20000);

        dos.writeUTF("你好");

        dos.flush();
        dos.close();
    }

    private static void readUTF() throws Exception {
        DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));

        int i = dis.readInt();
        System.out.println("i=" + i);

        String str = dis.readUTF();
        System.out.println("str=" + str);

        dis.close();
    }
}
