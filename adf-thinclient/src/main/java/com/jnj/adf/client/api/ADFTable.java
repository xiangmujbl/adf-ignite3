package com.jnj.adf.client.api;

import java.util.List;

/**
 * ADF table interface
 *
 * @param <K> key class
 * @param <V> value class
 */
public interface ADFTable<K, V> {

    /**
     * put operation
     *
     * @param key   key
     * @param value value
     */
    void put(K key, V value);

    /**
     * get operation
     *
     * @param key key
     * @return value
     */
    V get(K key);

    /**
     * find all entities
     *
     * @return
     */
    List<V> findAll();

    /**
     * select by using sql
     *
     * @param sql sql
     * @param cls value class
     * @return return object
     */
    List<V> select(String sql, Class<V> cls);
}
