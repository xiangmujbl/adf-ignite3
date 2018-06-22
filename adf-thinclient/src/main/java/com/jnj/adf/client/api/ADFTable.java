package com.jnj.adf.client.api;

/**
 * ADF table interface
 * @param <K> key class
 * @param <V> value class
 */
public interface ADFTable<K, V>{

    /**
     * put operation
     * @param key key
     * @param value value
     */
     void put(K key, V value);

    /**
     * get operation
     * @param key key
     * @return value
     */
     V get(K key);
}
