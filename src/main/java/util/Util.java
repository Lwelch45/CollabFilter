package util;

import java.text.Collator;
import java.util.*;

/**
 * Created by laurencewelch on 3/17/15.
 */
public class Util {

  public static <Y> Long[] getLongKeyFromMap(Map<Long, Y> map) {
    Long[] res = new Long[map.size()];
    int i = 0;
    for (Map.Entry<Long, Y> entry : map.entrySet()) {
      res[i] = entry.getKey();
      i++;
    }
    return res;
  }

  public static <Y, K> Long[] getLongIdFromMap(Map<Y, HashMap<Long, K>> map) {
    HashSet<Long> unique = new HashSet<>();
    for (Map.Entry<Y, HashMap<Long, K>> entry : map.entrySet()) {
      for (Map.Entry<Long, K> tuple : entry.getValue().entrySet()) {
        unique.add(tuple.getKey());
      }
    }
    return (Long[]) unique.toArray();
  }

  public static <T> Integer indexOfArray(T[] array, T item) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == item) {
        return i;
      }
    }
    return -1;
  }
  /*
     * Paramterized method to sort Map e.g. HashMap or Hashtable in Java
     * throw NullPointerException if Map contains null key
     */
  public static <K extends Comparable,V extends Comparable> Map<K,V> sortByKeys(Map<K,V> map){
    List<K> keys = new LinkedList<>(map.keySet());
    Collections.sort(keys);

    //LinkedHashMap will keep the keys in the order they are inserted
    //which is currently sorted on natural ordering
    Map<K,V> sortedMap = new LinkedHashMap<>();
    for(K key: keys){
      sortedMap.put(key, map.get(key));
    }

    return sortedMap;
  }

  /*
   * Java method to sort Map in Java by value e.g. HashMap or Hashtable
   * throw NullPointerException if Map contains null values
   * It also sort values even if they are duplicates
   */
  public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
    List<Map.Entry<K,V>> entries = new LinkedList<>(map.entrySet());

    Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

      @Override
      public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
        return o1.getValue().compareTo(o2.getValue());
      }
    });

    //LinkedHashMap will keep the keys in the order they are inserted
    //which is currently sorted on natural ordering
    Map<K,V> sortedMap = new LinkedHashMap<>();

    for(Map.Entry<K,V> entry: entries){
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
  }

}
