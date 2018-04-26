package clonegod.thread01.sync;
/**
 * 业务整体需要使用完整的synchronized，保持业务的原子性。
 *
 */
public class Test04DirtyRead {

	private String username = "zs";
	private String password = "123";
	
	public synchronized void setValue(String username, String password){
		this.username = username;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.password = password;
		
		System.out.println("setValue最终结果：username = " + username + " , password = " + password);
	}
	
	// 对共享资源的操作需要确保数据操作的“原子性”
	public /*synchronized*/ void getValue(){
		System.out.println("getValue方法得到：username = " + this.username + " , password = " + this.password);
	}
	
	
	public static void main(String[] args) throws Exception{
		
		final Test04DirtyRead dr = new Test04DirtyRead();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				dr.setValue("ls", "456");		
			}
		});
		t1.start(); // 新线程
		Thread.sleep(1000);
		
		// 主线程
		dr.getValue();
	}
	
	
	
}
