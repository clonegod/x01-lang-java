package baeldung.synchronizer.Phaser;

import java.util.concurrent.Phaser;

/**
 * 自定义Phaser， 主要复写每个阶段完成后执行的onAdvance()方法
 *	返回false，表示：流程未结束，需要继续执行后面的流程
 *	返回true， 表示：流程已全部结束
 */
public class CustomPhaser extends Phaser {
	public CustomPhaser() {
		super();
	}

	public CustomPhaser(int parties) {
		super(parties);
	}

	public CustomPhaser(Phaser parent, int parties) {
		super(parent, parties);
	}

	public CustomPhaser(Phaser parent) {
		super(parent);
	}

	//在每个阶段执行完成后回调的方法  
	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
		switch (phase) {  
        case 0:  
        	System.out.println("阶段A：工人到齐!");
            return false;  
        case 1:  
        	System.out.println("阶段A：Done!");
            return false;  
        case 2:  
        	System.out.println("阶段B：工人到齐!");
        	return false;  
        case 3:  
        	System.out.println("阶段B：Done!");
        	return false;  
        case 4:  
        	System.out.println("阶段C：工人到齐!");
        	return false;  
        case 5:  
        	System.out.println("阶段C：Done!");
        	return true;  
        default:  
            return true;  // 结束Phaser
        }  
	}

	
}
