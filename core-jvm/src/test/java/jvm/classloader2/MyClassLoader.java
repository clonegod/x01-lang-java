package jvm.classloader2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * 自定义类加载器，实现动态加载类
 */
public class MyClassLoader extends ClassLoader{

	String baseDir;
	Set<String> classSet;
	
    public MyClassLoader(ClassLoader parent, String baseDir, Set<String> classSet) {
        super(parent);
        this.baseDir = baseDir;
        this.classSet = classSet;
    }

    @Override
    public Class<?> loadClass(String clsName) throws ClassNotFoundException {
        if(!classSet.contains(clsName))
                return super.loadClass(clsName);
        
        // load specify class by MyClassLoader
        try {
        	String fullpath = baseDir+File.separator+clsName.replace('.', File.separatorChar) + ".class";
            InputStream input = new FileInputStream(fullpath);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass(clsName, classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}