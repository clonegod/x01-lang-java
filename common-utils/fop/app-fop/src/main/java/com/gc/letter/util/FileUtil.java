package com.gc.letter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
	
	private static FileUtil instance = null;
	
	private FileUtil() {}
	
	public static FileUtil getInstance() {
		if(instance==null) {
			synchronized (FileUtil.class) {
				if(instance==null) {
					instance = new FileUtil();
				}
			}
		}
		return instance;
	}
	
	public byte[] getBytesFromFile(File src) throws Exception {
		FileInputStream fin = null;
		FileChannel fc = null;
		try {
			fin = new FileInputStream(src);
			fc = fin.getChannel(); 
			ByteBuffer buffer = ByteBuffer.allocate(fin.available());
			fc.read(buffer);
			return buffer.array();
		} finally {
			if(fc!=null)
				fc.close();
			if(fin!=null)
				fin.close();
		}
	}
	
	public void writeBytes2File(byte[] bytes, File dest)throws Exception {
		FileOutputStream fout = null;
		FileChannel fc = null;
		try {
			fout = new FileOutputStream(dest);
			fc = fout.getChannel(); 
			ByteBuffer src = ByteBuffer.allocate(bytes.length);
			src.put(bytes);
			src.flip();
			fc.write(src);
		} finally {
			if(fc!=null)
				fc.close();
			if(fout!=null)
				fout.close();
		}
	}
	
	public void recordFoXML(String pipNo, String fo) throws Exception {
		File folder = new File("../appLogs/letter/fos");
		folder.mkdir();
		File pipFo = new File(folder,pipNo+".xml");
		if(pipFo.exists())
			pipFo.delete();
		FileWriter writer = null;
		try {
			writer = new FileWriter(pipFo);
			writer.write(fo);
			writer.flush();
		} finally {
			if(writer!=null)
				writer.close();
		}
	}
	
}
