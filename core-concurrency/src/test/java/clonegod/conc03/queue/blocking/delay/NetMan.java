package clonegod.conc03.queue.blocking.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 网民
 *
 */
public class NetMan implements Delayed {  
    
    private String name;  
    //身份证  
    private String id;  
    //截止时间  
    private long endTime;  
    //定义时间工具类
    private TimeUnit timeUnit = TimeUnit.SECONDS;
      
    public NetMan(String name,String id,long endTime){  
        this.name=name;  
        this.id=id;  
        this.endTime = endTime;  
    }  
      
    public String getName(){  
        return this.name;  
    }  
      
    public String getId(){  
        return this.id;  
    }  
      
    /** 
     * 判断是否到了截止时间 
     */  
    @Override  
    public long getDelay(TimeUnit unit) { 
        //return unit.convert(endTime, TimeUnit.MILLISECONDS) - unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    	return endTime - System.currentTimeMillis();
    }  
  
    /** 
     * 队列内部元素相互比较，排序用 
     */  
    @Override  
    public int compareTo(Delayed delayed) {  
    	NetMan w = (NetMan)delayed;  
        return this.getDelay(this.timeUnit) - w.getDelay(this.timeUnit) > 0 ? 1:0;  
    }  
  
    
}  