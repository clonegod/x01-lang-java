package prototype3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CloneUtils {
	
	/** 
	 * 使用泛型，使得复制对象的方法可以被复用
	 *  
	 * */
	@SuppressWarnings("unchecked")
	public static  <T> T deepCopy(T instance) throws Exception {
		// 将对象写入内存
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream bos = new ObjectOutputStream(baos);
		bos.writeObject(instance);
		
		// 从内存加载回对象，生成全新的对象
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream bis = new ObjectInputStream(bais);
		Object reborn =  bis.readObject();
		return (T)reborn;
	}
}
