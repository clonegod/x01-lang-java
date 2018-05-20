package serialize;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
 
/**
 * when file is read till end using readObject() in while loop then EOFException is thrown. 
 * Java Api doesn’t provide any elegant solution to signify end the file.
 *
 * During deserialization process when file is read till end using readObject() in while loop 
 * 	then EOFException is thrown as we saw in DeSerialization program. 
 */
public class DeSerializeEmployee {
 
    public static void main(String[] args) {
           InputStream fin;
           try {
                  fin = new FileInputStream("target/ser.txt");
                  ObjectInput oin = new ObjectInputStream(fin);
 
                  System.out.println("DeSerialization process has started, "
                               + "displaying employee objects...");
                  Employee emp;
                  while ((emp = (Employee) oin.readObject()) != null) {
                        System.out.println(emp);
                  }
                  oin.close();
 
           } catch (EOFException e) {
        	   	// 这里会抛出异常 - 反序列化时，java api没有提供优雅的方式处理文件结束的问题
                  System.err.println("File ended");
           }  catch (FileNotFoundException e) {
                  e.printStackTrace();
           } catch (IOException e) {
                  e.printStackTrace();
           } catch (ClassNotFoundException e) {
                  e.printStackTrace();
           }
 
           System.out.println("Object DeSerialization completed.");
 
    }
}