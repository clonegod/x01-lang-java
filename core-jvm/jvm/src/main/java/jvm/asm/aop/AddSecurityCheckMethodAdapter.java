package jvm.asm.aop;


import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class AddSecurityCheckMethodAdapter extends MethodAdapter { 
	 public AddSecurityCheckMethodAdapter(MethodVisitor mv) { 
		 super(mv); 
	 } 

	 @Override
	 public void visitCode() { 
		 // 相对于classes目录，所以这里需要写带包名的路径
		 visitMethodInsn(Opcodes.INVOKESTATIC, "jvm/asm/aop/SecurityChecker", 
			"checkSecurity", "()V"); 
	 } 
 }