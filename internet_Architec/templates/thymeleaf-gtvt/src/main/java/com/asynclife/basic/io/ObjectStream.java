/*
 *
 */
package com.asynclife.basic.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class ObjectStream {

    public static void main(String[] args) throws Exception {
        Person p = new Person("测试", 20);
        File file = new File("obj.dat");

        writeObj(p, file);
        Person p2 = readObj(file);

        System.out.println(p);
        System.out.println(p2);
    }

    private static void writeObj(Person p, File file) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(p);
        oos.close();
    }

    private static Person readObj(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();

        return (Person) obj;

    }

    static class Person implements Serializable {

        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + "," + age;
        }

    }

}
