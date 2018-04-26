package clonegod.serialize01.jdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializeUtil {
	
	public static <T> void serialize(T obj, OutputStream out) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> cls, InputStream in) {
		try {
			ObjectInputStream ois = new ObjectInputStream(in);
			Object obj = ois.readObject();
			return (T)obj;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T deepClone(T object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 8);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
			
			byte[] data = baos.toByteArray();
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(in);
			
			Object obj = ois.readObject();
			ois.close();
			
			return (T)obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
