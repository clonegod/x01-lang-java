package serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializeEmployee {
 
    public static void main(String[] args) {
 
           Employee object1 = new Employee(1, "amy");
           Employee object2 = new Employee(2, "ankit");
 
           try {
                  OutputStream fout = new FileOutputStream("target/ser.txt");
                  ObjectOutput oout = new ObjectOutputStream(fout);
                  System.out.println("Serialization process has started, serializing employee objects...");
                  oout.writeObject(object1);
                  oout.writeObject(object2);
                    oout.close();
                  System.out.println("Object Serialization completed.");
                  
           } catch (IOException ioe) {
                  ioe.printStackTrace();
           }
 
    }
 
}