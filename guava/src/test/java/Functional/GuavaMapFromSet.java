package Functional;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import com.google.common.base.Function;

public class GuavaMapFromSet<K, V> extends AbstractMap<K, V> {
	
	private Set<Entry<K, V>> entries;
	private Function<? super K, ? extends V> function;
	private WeakHashMap<K, V> cache;

	public GuavaMapFromSet(Set<K> keys, Function<? super K, ? extends V> function) {
		this.function = function;
		this.cache = new WeakHashMap<K, V>();
		this.entries = new MyEntrySet(keys);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return this.entries;
	}

	private class MyEntrySet extends AbstractSet<Entry<K, V>> {
		
		private Set<K> keys;

		public MyEntrySet(Set<K> keys) {
			this.keys = keys;
		}

		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			return new EntryIterator();
		}

		@Override
		public int size() {
			return this.keys.size();
		}

		public class EntryIterator implements Iterator<Entry<K, V>> {
			private Iterator<K> inner;

			public EntryIterator() {
				this.inner = MyEntrySet.this.keys.iterator();
			}

			@Override
			public boolean hasNext() {
				return this.inner.hasNext();
			}

			@Override
			public Map.Entry<K, V> next() {
				K key = this.inner.next();
				return new SingleEntry(key);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}

	}
	
	private class SingleEntry implements Entry<K, V> {
		private K key;

		public SingleEntry(K key) {
			this.key = key;
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			// 尝试从缓存中获取
			V value = GuavaMapFromSet.this.cache.get(this.key); 
			if (value == null) {
				// 实时计算，并将结果缓存起来
				value = GuavaMapFromSet.this.function.apply(this.key); 
				GuavaMapFromSet.this.cache.put(this.key, value); 
			}
			return value;
		}

		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}
	}

}