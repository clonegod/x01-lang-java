package cloneg.serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import clonegod.dubbox.restful.api.User;

public class TestKryoSerialize {
	/**
	 * Kryo序列化测试结果：
		4.92 MB
		
		Kryo Serializable writeObject time:430 ms
		Kryo Serializable readObject time:318 ms
	 */
	
	private static String SER_FILE_OUTPUT_PATH = "target/ser-kryo.bin";
	
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("Kryo Serializable writeObject time:" + (System.currentTimeMillis() - start) + " ms");
		start = System.currentTimeMillis();
		getSerializableObject();
		System.out.println("Kryo Serializable readObject time:" + (System.currentTimeMillis() - start) + " ms");

	}

	public static void setSerializableObject() throws FileNotFoundException {

		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		kryo.register(User.class);
		Output output = new Output(new FileOutputStream(SER_FILE_OUTPUT_PATH));
		for (int i = 0; i < 100_000; i++) {
			kryo.writeObject(output, new User(UUID.randomUUID().toString(), "zhang" + i, "password" + i, i + 1, new Date()));
		}
		output.flush();
		output.close();
	}

	public static void getSerializableObject() {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		Input input;
		try {
			input = new Input(new FileInputStream(SER_FILE_OUTPUT_PATH));
			User user = null;
			while ((user = kryo.readObject(input, User.class)) != null) {
				// System.out.println(user.getAge() + " " + user.getName() + " " +
			}

			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KryoException e) {

		}
	}

}
