package lab1;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> {
    private static final int DEFAULT_MAX_SIZE = 1000;
    private final int MAX_SIZE;
    private final Map<K, DoublyLinkedList<CacheElement>.Node> cache;
    private final DoublyLinkedList<CacheElement> list = new DoublyLinkedList<>();

    /**
     * Constructs an empty LRU Cache.
     * @param maxSize max count of stored values (the oldest will be removed)
     */
    public LRUCache(int maxSize) {
        assert maxSize > 0;
        MAX_SIZE = maxSize;
        cache = new HashMap<>(MAX_SIZE + 1);
    }

    /**
     * Constructs an empty LRU Cache with default max size of 1000.
     */
    public LRUCache() {
        this(DEFAULT_MAX_SIZE);
    }

    /**
     * Inserts a pair of (key, value) into cache and removes the oldest pair if it's size exceeds max available size.
     * @param key not null key
     * @param value not null value
     */
    public void put(@NotNull K key, @NotNull V value) {
        assert size() <= maxSize();

        if (cache.containsKey(key)) {
            removeFromList(key);
        }
        putNew(key, value);

        if (size() > maxSize()) {
            removeOldest();
        }
    }

    /**
     * Searches for value associated with key.
     * @param key not null key
     * @return associated value if present
     */
    public Optional<V> get(@NotNull K key) {
        assert size() <= maxSize();

        return Optional.ofNullable(cache.get(key)).map(node -> {
            list.moveToFront(node);
            return node.getValue().getValue();
        });
    }

    public int size() {
        return cache.size();
    }

    public int maxSize() {
        return MAX_SIZE;
    }

    private void putNew(@NotNull K key, @NotNull V value) {
        cache.put(key, list.add(new CacheElement(key, value)));
    }

    private void removeFromList(@NotNull K key) {
        list.remove(cache.get(key));
    }

    private void removeOldest() {
        assert size() == maxSize() + 1;

        list.removeFirst().ifPresent(elem -> cache.remove(elem.getKey()));
    }

    private class CacheElement {
        private final K key;
        private final V value;

        private CacheElement(@NotNull K key, @NotNull V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
