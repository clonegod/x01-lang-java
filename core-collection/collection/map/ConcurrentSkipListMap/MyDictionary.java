package ConcurrentSkipListMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Let’s say you have to build dictionary and multiple users can add data in that dictionary? 
 * And you can use 2 Collection classes? 
 * Which Collection classes you will prefer and WHY?
 *	
 *	--- ConcurrentSkipListMap 可以提供多线程并发安全，而且支持key的排序，对于字典而言排序是必须的。
 *	--- TreeSet 具有排序功能，而且可以去除重复元素。
 */
public class MyDictionary {
    public static void main(String[] args) {
           ConcurrentSkipListMap<String, TreeSet<String>> myDictionary =
                        new ConcurrentSkipListMap<String, TreeSet<String>>();
 
           TreeSet<String> innocentMeaning = new TreeSet<String>();
           innocentMeaning.add("not responsible for an event yet suffering its consequences");
           innocentMeaning.add("not guilty of a crime");
 
           myDictionary.put("innocent", innocentMeaning);
 
           TreeSet<String> appealingMeaning = new TreeSet<String>();
           appealingMeaning.add("attractive");
           appealingMeaning.add("expressing a desire for help");
 
           myDictionary.put("appealing", appealingMeaning);
 
           System.out.println(myDictionary);
 
    }
}