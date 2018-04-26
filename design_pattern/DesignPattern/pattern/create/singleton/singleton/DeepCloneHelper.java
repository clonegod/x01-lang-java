package singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeepCloneHelper {
		/**
		 * 序列化对象到文件
		 */
		public static void ser(File file) {
			try {
				file.createNewFile();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(SerializeMan.getInstance());
				oos.flush();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 从文件反序列化得到对象
		 */
		public static Object deser(File file) {
			Object out = null;
			if(file.exists()) {
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
					out = (SerializeMan) ois.readObject();
					ois.close();
					file.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return out;
		}
	}