package clonegod.core.serialize.imporved;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/*
 * Serialization class
 */
public class SerializeEmployee {
 
    public static void main(String[] args) {
 
           Employee object1 = new Employee( "amy");
           Employee object2 = new Employee( "ankit");
 
           try {
                  OutputStream fout = new FileOutputStream("target/ser-with-eof.txt");
                  ObjectOutput oout = new ObjectOutputStream(fout);
 
                  System.out.println("Serialization process has started, "
                               + "serializing employee objects...");
                  oout.writeObject(object1);
                  oout.writeObject(object2);
                  
                  //write instance of EofIndicatorClass at EOF --- 手动写入结束标记
                  oout.writeObject(new EofIndicatorClass());
 
                  oout.close();
                  System.out.println("Object Serialization completed.");
                  
           } catch (IOException ioe) {
                  ioe.printStackTrace();
           }
 
    }
 
}