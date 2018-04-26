package objectpool;
// ObjectPool Class

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class AbstractObjectPool<T> {
  private long expirationTime;

  private Hashtable<T, Long> locked; /**正被使用的资源*/
  private Hashtable<T, Long> unlocked; /**空闲资源*/

  public AbstractObjectPool() {
    expirationTime = 30000; // 30 seconds
    locked = new Hashtable<T, Long>();
    unlocked = new Hashtable<T, Long>();
  }

  protected abstract T create();

  public abstract boolean validate(T o);

  public abstract void expire(T o);

  public synchronized T acquire() {
    long now = System.currentTimeMillis();
    T t;
    // 如果池中有空闲资源
    if (unlocked.size() > 0) {
      Enumeration<T> e = unlocked.keys();
      while (e.hasMoreElements()) {
        t = e.nextElement();
        if ((now - unlocked.get(t)) > expirationTime) {
          // object has expired
          unlocked.remove(t);
          expire(t);
          t = null;
        } else {
          if (validate(t)) {
        	// object still valid
            unlocked.remove(t);
            locked.put(t, now);
            return (t);
          } else {
            // object failed validation
            unlocked.remove(t);
            expire(t);
            t = null;
          }
        }
      }
    }
    // no objects available, create a new one
    t = create();
    locked.put(t, now);
    return (t);
  }

  // return resource to pool
  public synchronized void release(T t) {
    locked.remove(t);
    unlocked.put(t, System.currentTimeMillis());
  }
}
