package com.jnj.adf.ignite.common;

import org.apache.ignite.cache.QueryEntity;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author Tang
 */
public final class Utils {

    /**
     * domain class to QueryEntity
     *
     * @param tableName table name
     * @param keyCls    key class
     * @param valueCls  value class
     * @return QueryEntity
     */
    public static <K, V> QueryEntity toQueryEntity(String tableName, Class<K> keyCls, Class<V> valueCls) {
        QueryEntity queryEntity = new QueryEntity(keyCls, valueCls);
        queryEntity.setTableName(tableName);
        queryEntity.setIndexes(null);
        // queryEntity.setKeyFieldName("_key");
        // queryEntity.setKeyType(keyCls.getName());
        if (valueCls.isPrimitive() || valueCls.isAssignableFrom(String.class)
                || Collection.class.isAssignableFrom(valueCls) ) {
            // queryEntity.setValueFieldName("value");
            // queryEntity.setValueType(valueCls.getName());
            // LinkedHashMap<String, String> m = new LinkedHashMap<>();
            // m.put("value", valueCls.getName());
            // queryEntity.setFields(m);
        } else {
            LinkedHashMap<String, String> m = new LinkedHashMap<>();
            Field[] fields = valueCls.getDeclaredFields();
            Arrays.stream(fields).forEach(field -> m.put(field.getName(), field.getType().getName()));
            queryEntity.setFields(m);
        }
        return queryEntity;
    }

    public static void main(String[] args) {
        toQueryEntity("", String.class, Set.class);

    }
}
