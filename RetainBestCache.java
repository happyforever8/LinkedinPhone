===== Questions======


public class RetainBestCache<K, T extends Rankable> {
    int entriesToRetain;
    HashMap<K, T> map = new HashMap<K,T>();
    DataSource<K,T> ds;

/* Constructor with a data source (assumed to be slow) and a cache size */
public RetainBestCache(DataSource<K,T> ds, int entriesToRetain) {
    //impliment here
    }
    /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
    * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
    * evicting the T with lowest rank among the ones that it has available
    * If there is a tie, the cache may choose any T with lowest rank to evict.
    */
public T get(K key) {
    //impliment here
    }
    /*
    * For reference, here are the Rankable and DataSource in‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌terfaces.
    * You do not need to implement them, and should not make assumptions
    * about their implementations.
    */
public interface Rankable {
    /**
    * Returns the Rank of this object, using some algorithm and potentially
    * the internal state of the Rankable.
    */
long getRank();
}
public interface DataSource<K, T extends Rankable> {
    T get(K key);
}



========== Answer================



public class RetainBestCache<K, T extends Rankable> {

     private Map<K, T> cache;
     private Map<Long, Set<K>> rankingOfObject;
     private DataSource<K, T> dataSource;
     private int maxSizeOfCache;

    /* Constructor with a data source (assumed to be slow) and a cache size */
    public RetainBestCache(DataSource<K,T> ds, int entriesToRetain) {
        // Implementation here
        cache = new HashMap<>();
        rankingOfObject = new TreeMap<>(); 
        dataSource = ds;  //data is not cached, get from datasource,
        maxSizeOfCache = entriesToRetain;  // the size of cache
    }

    /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
     * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
     * evicting the T with lowest rank among the ones that it has available
     * If there is a tie, the cache may choose any T with lowest rank to evict.
     */
    public T get(K key) {
        // Implementation here
        if(cache.containsKey(key)) {
          return cache.get(key);
        }
        return fetchDataFromDs(key);
    }

    private T fetchDataFromDs(K key) {
       if(cache.size() >= maxSizeOfCache) {
          evictElement();
        }
        T object = dataSource.get(key);
        cache.put(key, object);
        long rankOfObject = object.getRank();
        if(!rankingOfObject.containsKey(rankOfObject)) {
          rankingOfObject.put(rankOfObject, new HashSet<>());
        }
        rankingOfObject.get(rankOfObject).add(key);
        return object;
    }

    private void evictElement() {
        Entry<Long, Set<K>> entry = rankingOfObject.firstEntry();
      //Entry<Long, Set<K>> entry = rankingOfObject.topEntry();
      K key = entry.getValue().getIterator().next();
      entry.getValue().remove(key);
      cache.remove(key)
      if(entry.getValue().size() == 0) {
        rankingOfObject.remove(entry.getKey());
      }
    }


}

// What if rank is defined as number of reads of element in cache?
// LRU
// Let's assume that rank can change dynamicly. It is not immutable, but it is not LRU. We do not know how it is changed


/*
* For reference, here are the Rankable and DataSource interfaces.
* You do not need to implement them, and should not make assumptions
* about their implementations.
*/

public interface Rankable {
    /**
     * Returns the Rank of‍‍‍‍‍‍‍‍‌‍‌‌‍‌‌‌‌‌ this object, using some algorithm and potentially
     * the internal state of the Rankable.
     */
    long getRank();
}

public interface DataSource<K, T extends Rankable> {
    T get (K key);
}
 
    
    ======================pq version===================
        
刚刚写了一个版本，跟楼上grandyang链接里面的差不多，欢迎指正!
import java.ut ...[/quote]
[hide=200]没必要用TreeMap吧，感觉PriorityQueue应该就够了吧？[code]public class RetainBestCache<K, T extends Rankable> {
        int entriesToRetain;
        HashMap<K, T> map = new HashMap<K,T>();
        PriorityQueue<Wrapper<K, T>> pq;
        DataSource<K,T> ds;

        /* Constructor with a data source (assumed to be slow) and a cache size */
        public RetainBestCache(DataSource<K,T> ds, int entriesToRetain) {
                //impliment here
                this.pq = new PriorityQueue<>(new Comparator<Wrapper>() {
                        public int compare(Wrapper w1, Wrapper w2) {
                                return w1.data.getRank() - w2.data.getRank();
                        }
                });
                this.ds = ds;
                this.entriesToRetain = entriesToRetain;
        }
        /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
        * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
        * evicting the T with lowest rank among the ones that it has available
        * If there is a tie, the cache may choose any T with lowest rank to evict.
        */
        public T get(K key) {
                //impliment here
                if (map.containsKey(key)) {
                        return map.get(key);
                }
                T data = DataSource.get(key);
                if (map.size() < entriesToRetain) {
                        map.put(key, data);
                        pq.offer(new Wrapper(key, data));
                } else {
                        evict();
                        map.put(key, data);
                        pq.offer(new Wrapper(key, data));
                }
                return data;
        }

        private evict() {
                Wrapper leastRank = pq.poll();
                map.remove(leastRank.key);
        }
}

class Wrapper<K, T> {
        T data;
        K key;

        public Wrapper(T data, K key) {
                this.data = data;
                this.key = key;
        }
}

/*
* For reference, here are the Rankable and DataSource interfaces.
* You do not need to implement them, and should not make assumptions
* about their implementations.
*/
public interface Rankable {
        /**
        * Returns the Rank of this object, using some algorithm and potentially
        * the internal state of the Rankable.
        */
        long getRank();
}

public interface DataSource<K, T extends Rankable> {
        T get(K key);
}
    
================ with lock ========
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RetainBestCache<K, T extends Rankable> {
    int entriesToRetain;
    private Map<K, T> cache;
    private TreeMap<Long, Set<K>> rankingKeySetMap;
    Semaphore semaphore;
    DataSource<K,T> ds;

    /* Constructor with a data source (assumed to be slow) and a cache size */
    public RetainBestCache(DataSource<K,T> ds, int entriesToRetain) {
        cache = new HashMap<>();
        rankingKeySetMap = new TreeMap<>();
        this.ds = ds;
        this.entriesToRetain = entriesToRetain;
        semaphore = new Semaphore(entriesToRetain);********************************
    }
    /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
     * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
     * evicting the T with lowest rank among the ones that it has available
     * If there is a tie, the cache may choose any T with lowest rank to evict.
     */
    public T get(K key) {
        //implement here
        if(cache.containsKey(key)){
            return cache.get(key);
        }
        return fetchFromDS(key);
    }

    private T fetchFromDS(K key){
        if(cache.size() >= entriesToRetain){
            evitLowestRank();
        }
        T object = ds.get(key);
        try{
            semaphore.acquire();******************
            cache.put(key, object);
            long score = object.getRank();
            if(!rankingKeySetMap.containsKey(score)){
                rankingKeySetMap.put(score, new HashSet<>());
            }
            rankingKeySetMap.get(score).add(key);
        }
        catch (InterruptedException e){

        }
        finally {
            semaphore.release();*********************
        }
        return object;
    }

    private void evitLowestRank(){
        Map.Entry<Long, Set<K>> entry = rankingKeySetMap.firstEntry();
        K key = entry.getValue().iterator().next();
        entry.getValue().remove(key);
        cache.remove(key);
        if(entry.getValue().size() == 0){
            rankingKeySetMap.remove(entry.getKey());
        }
    }
}
    /*
     * For reference, here are the Rankable and DataSource interfaces.
     * You do not need to implement them, and should not make assumptions
     * about their implementations.
     */
    public interface Rankable {
        /**
         * Returns the Rank of this object, using some algorithm and potentially
         * the internal state of the Rankable.
         */
        long getRank();
    }
    public interface DataSource<K, T extends Rankable> {
        T get(K key);
    }
