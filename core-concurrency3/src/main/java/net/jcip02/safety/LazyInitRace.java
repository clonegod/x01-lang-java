package net.jcip02.safety;

import net.jcip.annotations.NotThreadSafe;

/**
 * LazyInitRace
 *
 * Race condition in lazy initialization----竞态条件未加控制，导致单例模式出现问题
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class LazyInitRace {
    private static ExpensiveObject instance = null;
    
    private LazyInitRace() {}
    
    public static ExpensiveObject getInstance() {
    	/**
    	 * BAD
    	 * 错误的单例模式应用
    	 */
        if (instance == null) //竞态条件
            instance = new ExpensiveObject(); //threadA,threadB,threadX同时出现在这里的时候，就会创建出多个对象
        return instance;
    }
}

class ExpensiveObject { }
