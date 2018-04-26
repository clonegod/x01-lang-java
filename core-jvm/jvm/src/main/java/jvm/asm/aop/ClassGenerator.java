package jvm.asm.aop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.objectweb.asm.*; 
    
 public class ClassGenerator{ 
	 
	 public static void modifyClass(final String targetClass) throws Exception { 
		 InputStream accountClassInStream = new FileInputStream(targetClass);
		 ClassReader cr = new ClassReader(accountClassInStream);
		 ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		 
		 // 插入AOP拦截功能：通过visitMethod对执行方法进行拦截处理
		 ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw); 
		 cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
		 
		 // 将修改写入class字节码文件
		 byte[] data = cw.toByteArray(); 
		 File file = new File(targetClass); 
		 FileOutputStream fout = new FileOutputStream(file); 
		 fout.write(data); 
		 fout.close(); 
	 } 
	 
 }