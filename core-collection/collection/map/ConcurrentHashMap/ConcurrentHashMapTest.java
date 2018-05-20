package ConcurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashMapTest {
    public static void main(String args[]) {
 
        ConcurrentMap<Integer, String> concurrentHashMap =
                                        new ConcurrentHashMap<Integer, String>();
        concurrentHashMap.put(1, "javaMadeSoEasy");
        System.out.println("concurrentHashMap : "+concurrentHashMap);
        
        System.out.println("\n putIfAbsent method >> "+
                            concurrentHashMap.putIfAbsent(1, "ankit"));
        System.out.println("concurrentHashMap : "+concurrentHashMap);
 
        System.out.println("\n putIfAbsent method >> "+
                            concurrentHashMap.putIfAbsent(2, "audi"));
        System.out.println("concurrentHashMap : "+concurrentHashMap);
        
    }
}