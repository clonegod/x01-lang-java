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
import org.objectweb.asm.Opcodes;

/**
 * 
使用ASM，通过字节码 完成以下代码：
        int a=6;
        int b=7;
        int c=(a+b)*3;
        System.out.println(c);
        
 * @author clonegod
 *
 */
public class ArithmeticByAsm extends ClassLoader {
	
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
			mw.visitVarInsn(Opcodes.BIPUSH, 6); // 6放入操作数栈
			mw.visitVarInsn(Opcodes.ISTORE, 1); // 加载到局部变量表索引位为1的位置
			
			mw.visitVarInsn(Opcodes.BIPUSH, 7);// 7放入操作数栈
			mw.visitVarInsn(Opcodes.ISTORE, 2);// 加载到局部变量表索引位为2的位置
			
			mw.visitVarInsn(Opcodes.ILOAD, 1); // 载入局部变量表索引位为1的数
			mw.visitVarInsn(Opcodes.ILOAD, 2); // 载入局部变量表索引位为2的数
			mw.visitInsn(Opcodes.IADD); // 两数执行加法
			
			mw.visitInsn(Opcodes.ICONST_3);
			mw.visitInsn(Opcodes.IMUL); // 执行乘法
			mw.visitVarInsn(Opcodes.ISTORE, 3);
			
			mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out",  "Ljava/io/PrintStream;");
			mw.visitVarInsn(Opcodes.ILOAD, 3); 
			mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",  "(I)V");  
			mw.visitInsn(RETURN);  
			mw.visitMaxs(0,0);  
			mw.visitEnd();  
		}
		
		byte[] code = cw.toByteArray();  
		
		return code;
	}
	
	public static void main(String[] args) throws Exception {
		
		ArithmeticByAsm loader = new ArithmeticByAsm();  
		
		byte[] code = loader.generateBytes();
		
		Class<?> exampleClass = loader.defineClass("Example", code, 0, code.length);  
		
		exampleClass.getMethods()[0].invoke(null, new Object[] { null }); 
	}
}
