package clonegod.core.serialize.imporved;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class DeSerializeEmployee {
    public static void main(String[] args) {
           InputStream fin;
           try {
                  fin = new FileInputStream("target/ser-with-eof.txt");
                  ObjectInput oin = new ObjectInputStream(fin);
 
                  System.out.println("DeSerialization process has started, "
                               + "displaying employee objects...");
 
                   /*
                  *If oin.readObject() returns instanceof EofIndicatorClass that means
                  *it's EOF, exit while loop and EOFException will not be thrown.
                  */
                 Object obj;
                 while(true){
                	 obj =  oin.readObject();
                	 // --- 判断结束标记
                	 if(obj instanceof EofIndicatorClass) {
                		 break;
                	 }
                      System.out.println(obj);
                 }
 
                 oin.close();
 
           } catch (EOFException e) {
        	   // should not happen
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
