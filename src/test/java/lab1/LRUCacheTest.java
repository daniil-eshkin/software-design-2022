package lab1;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("unused")
public class LRUCacheTest {
    private LRUCache<String, String> cache;

    @Before
    public void initCache() {
        cache = new LRUCache<>(3);
    }

    @Test
    public void noPuts() {
        assertThat(cache.get("k1")).isEmpty();
    }

    @Test
    public void singlePut() {
        cache.put("k1", "v1");
        assertThat(cache.get("k1")).hasValue("v1");
    }

    @Test
    public void overwrite() {
        assertThat(cache.get("k1")).isEmpty();
        cache.put("k1", "v11");
        assertThat(cache.get("k1")).hasValue("v11");
        cache.put("k1", "v12");
        assertThat(cache.get("k1")).hasValue("v12");
    }

    @Test
    public void removeOldest() {
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        cache.put("k4", "v4");
        assertThat(cache.get("k1")).isEmpty();
    }

    @Test
    public void removeOldestAfterGet() {
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        assertThat(cache.get("k1")).hasValue("v1");

        cache.put("k4", "v4");
        assertThat(cache.get("k2")).isEmpty();
    }

    @Test
    public void removeOldestAfterOverwrite() {
        cache.put("k1", "v11");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        cache.put("k1", "v12");

        cache.put("k4", "v4");
        assertThat(cache.get("k2")).isEmpty();
    }

    @Test
    public void removeOldestAfterOverwriteTheSameValue() {
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        cache.put("k1", "v1");

        cache.put("k4", "v4");
        assertThat(cache.get("k2")).isEmpty();
    }

    @Test
    public void allValuesArePresent() {
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        assertThat(cache.get("k1")).hasValue("v1");
        assertThat(cache.get("k2")).hasValue("v2");
        assertThat(cache.get("k3")).hasValue("v3");
    }

    @Test(expected = AssertionError.class)
    public void invalidSize() {
        LRUCache<String, String> invalidCache = new LRUCache<>(-1);
    }

    @Test(expected = AssertionError.class)
    public void invalidSize2() {
        LRUCache<String, String> invalidCache = new LRUCache<>(0);
    }

    @Test
    public void defaultMaxSize() {
        LRUCache<String, String> defaultCache = new LRUCache<>();
    }
}
