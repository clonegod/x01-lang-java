package baeldung.concurrentmap.ConcurrentSkipListMap;

/**
 * ConcurrentNavigableMap ---> ConcurrentMap & NavigableMap/SortedMap
 * 	|- ConcurrentSkipListMap 
 * 
 * ConcurrentSkipListMap can be seen a scalable concurrent version of TreeMap.
 * ConcurrentSkipListMap 可以看作是扩展了并发功能的TreeMap
 * 	
 * In addition to TreeMap's features, key insertion, removal, update and access operations are guaranteed with thread-safety.
 * ConcurrentSkipListMap在TreeMap特性的基础上，对key insertion, removal, update and access操作支持并发安全。
 * 
 * 要求：key具有排序功能，实现Comparable，或者具有Comparator功能
 * 
 */
