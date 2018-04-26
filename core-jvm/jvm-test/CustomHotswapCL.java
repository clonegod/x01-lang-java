package com.aysnclife.dataguru.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

class CustomHotswapCL extends ClassLoader { 

	private String basedir; // 需要该类加载器直接加载的类文件的基目录
    private HashSet<String> dynaclazns; // 需要由该类加载器直接加载的类名

    public CustomHotswapCL(String basedir, String[] clazns) { 
        super(null); // 指定父类加载器为 null 
        this.basedir = basedir; 
        dynaclazns = new HashSet<String>(); 
        try {
        	 for (int i = 0; i < clazns.length; i++) { 
                 loadDirectly(clazns[i]); 
                 dynaclazns.add(clazns[i]); 
             }
		} catch (IOException e) {
			e.printStackTrace();
		} 
    } 

    private Class<?> loadDirectly(String name) throws IOException { 
        Class<?> cls = null; 
        
        // get full path of the class
        StringBuffer sb = new StringBuffer(basedir); 
        String classname = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + classname); 
        File classF = new File(sb.toString()); 
        
        // 获取到Class 实例
        byte[] raw = new byte[(int) classF.length()]; 
        FileInputStream fin = new FileInputStream(classF);
        fin.read(raw); 
        fin.close(); 
        cls = super.defineClass(name,raw,0,raw.length); 

        return cls; 
    }
    
    /**
     * 自定义类加载
     */
    @Override
	protected Class<?> loadClass(String name, boolean resolve) 
            throws ClassNotFoundException { 
        Class<?> cls = null; 
        cls = super.findLoadedClass(name); 
        
        // 未加载，并且没有在自定义加载器需要加载的类集合中，则委托给上层去加载
        if(cls == null && !this.dynaclazns.contains(name)) 
            cls = super.getSystemClassLoader().loadClass(name);
        
        // 如果没有找到对应的字节码文件，则说明类不存在
        if (cls == null) 
            throw new ClassNotFoundException(name);
        
        // 链接一个类
        if (resolve) 
        	super.resolveClass(cls); 
        
        return cls; 
    } 

}