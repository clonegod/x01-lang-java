package clonegod.conc03.queue.blocking.delay;

import java.util.concurrent.DelayQueue;

/**
 * 网吧上网
 *	网民交费上网，交网费，得到可上机时间，时间到，网民下机。---延迟队列来实现此功能。
 */
public class NetBar implements Runnable {  
    
    private DelayQueue<NetMan> queue = new DelayQueue<NetMan>();  
    
    public boolean open =true;
      
    public void play(String name,String id,int money){  
        NetMan man = new NetMan(name, id, 1000 * money + System.currentTimeMillis());  
        System.out.println("网名"+man.getName()+" 身份证"+man.getId()+"交钱"+money+"块,开始上机...");  
        this.queue.add(man);  
    }  
      
    public void goHome(NetMan man){  
        System.out.println("网名"+man.getName()+" 身份证"+man.getId()+"时间到下机...");  
    }  
  
    @Override  
    public void run() {  
        while(open){  
            try {  
                NetMan man = queue.take();  
                goHome(man);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
      
    public static void main(String args[]){  
        try{  
            System.out.println("网吧开始营业");  
            NetBar siyu = new NetBar();  
            Thread shangwang = new Thread(siyu);  
            shangwang.start();  
              
            siyu.play("路人甲", "123", 1);  
            siyu.play("路人乙", "234", 10);  
            siyu.play("路人丙", "345", 5);  
        }  
        catch(Exception e){  
            e.printStackTrace();
        }  
  
    }  
}  