package jvm.asm.hello;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_7;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * 使用asm创建字节码
 * @author Administrator
 *
 */
public class AsmHelloWorld extends ClassLoader {
	
	public byte[] generateBytes() {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);  
		cw.visit(V1_7, ACC_PUBLIC, "Example", null, "java/lang/Object", null);  
		
		MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null,  null); 
		{
		mw.visitVarInsn(ALOAD, 0);  //this 入栈
		mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		mw.visitInsn(RETURN);  
		mw.visitMaxs(0, 0);  
		mw.visitEnd();  
		}	
		
		{
		mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",  "([Ljava/lang/String;)V", null, null);  
		mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out",  "Ljava/io/PrintStream;");  
		mw.visitLdcInsn("Hello world!");  
		mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",  "(Ljava/lang/String;)V");  
		mw.visitInsn(RETURN);  
		mw.visitMaxs(0,0);  
		mw.visitEnd();  
		}
		
		byte[] code = cw.toByteArray();  
		
		return code;
	}
	
	public static void main(String[] args) throws Exception {
		
		AsmHelloWorld loader = new AsmHelloWorld();  
		
		byte[] code = loader.generateBytes();
		
		Class<?> exampleClass = loader.defineClass("Example", code, 0, code.length);  
		
		exampleClass.getMethods()[0].invoke(null, new Object[] { null }); 
	}
}
