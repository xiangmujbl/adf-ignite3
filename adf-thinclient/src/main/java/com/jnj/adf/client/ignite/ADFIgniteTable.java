package com.jnj.adf.client.ignite;

import com.jnj.adf.client.api.ADFTable;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.client.ClientCache;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;

/**
 * ADF Table implementation by Ignite
 *
 * @author Tang
 */
public class ADFIgniteTable<K, V> implements ADFTable<K, V> {

    private ClientCache<K, V> cache;

    public ADFIgniteTable(ClientCache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public List<V> findAll() {
        List<V> results = new ArrayList<>();
        try (QueryCursor<Cache.Entry<K, V>> cursor = cache.query(new ScanQuery<>())) {
            for (Cache.Entry<K, V> entry : cursor)
                results.add(entry.getValue());
        }
        return results;
    }

    @Override
    public List<V> select(String sql, Class<V> cls) {
        List<V> results = new ArrayList<>();
        try (QueryCursor<Cache.Entry<K, V>> cursor = cache.query(new SqlQuery(cls, "SELECT * FROM ADF.GRIDS"))) {
            for (Cache.Entry<K, V> entry : cursor)
                results.add(entry.getValue());
        }
        return results;
    }
}
