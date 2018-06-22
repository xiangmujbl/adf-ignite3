package com.jnj.adf.client.ignite;

import com.jnj.adf.client.api.ADFTable;
import org.apache.ignite.client.ClientCache;

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
}
