package cloneg.serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.UUID;

import clonegod.dubbox.restful.api.User;

public class TestJdkSerialize {
	
	/**
	 * JDK内置序列化测试结果：
		6.17 MB
		
		java Serializable writeObject time:2914 ms
		java Serializable readObject time:3781 ms
	 */
	
	private static String SER_FILE_OUTPUT_PATH = "target/ser-jdk.bin";
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("java Serializable writeObject time:" + (System.currentTimeMillis() - start) + " ms");
		start = System.currentTimeMillis();
		getSerializableObject();
		System.out.println("java Serializable readObject time:" + (System.currentTimeMillis() - start) + " ms");
	}

	public static void setSerializableObject() throws IOException {

		FileOutputStream fo = new FileOutputStream(SER_FILE_OUTPUT_PATH);

		ObjectOutputStream so = new ObjectOutputStream(fo);

		for (int i = 0; i < 100_000; i++) {
			so.writeObject(new User(UUID.randomUUID().toString(), "zhang" + i, "password" + i, i + 1, new Date()));
		}
		so.flush();
		so.close();
	}

	public static void getSerializableObject() {
		FileInputStream fi;
		try {
			fi = new FileInputStream(SER_FILE_OUTPUT_PATH);
			ObjectInputStream si = new ObjectInputStream(fi);

			User user = null;
			while ((user = (User) si.readObject()) != null) {
				// System.out.println(user.getAge() + " " + user.getName());
			}
			fi.close();
			si.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
