package com.jnj.adf.ignite;

import com.jnj.adf.ignite.common.Constants;
import com.jnj.adf.ignite.domain.GridInfo;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import javax.cache.Cache;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Tang
 */
public class TestClient {

    public static void main(String[] args) {
        ClientConfiguration cfg = new ClientConfiguration().setAddresses("127.0.0.1:10801");
        try (IgniteClient igniteClient = Ignition.startClient(cfg)) {
            // System.out.println(igniteClient.cacheNames());
            // ClientCache<String, GridInfo> cache = igniteClient.getOrCreateCache("_ADF_GRIDS_");
            // for (int i = 0; i < 1000000; i++) {
            //     GridInfo testGrid = new GridInfo();
            //     testGrid.setName("test" + i);
            //     testGrid.setAddress("127.0.0.1:10801");
            //     cache.put("test" + i, testGrid);
            // }

            ClientCache<String, BinaryObject> cache = igniteClient.getOrCreateCache("PayTrans").withKeepBinary();

            try (QueryCursor<Cache.Entry<String, BinaryObject>> cursor = cache.query(new ScanQuery<>())) {
                Cache.Entry<String, BinaryObject> entry;
                Iterator<Cache.Entry<String, BinaryObject>> iterator = cursor.iterator();
                while (iterator.hasNext()) {
                    entry = iterator.next();
                    System.out.println(entry.getKey() + "=====" + entry.getValue());
                }
            }

            try(QueryCursor<Cache.Entry<String,BinaryObject>> cursor = cache.query(new SqlQuery("PayTrans_val","SELECT * FROM PayTrans"))){
                Iterator<Cache.Entry<String,BinaryObject>> iterator = cursor.iterator();
                Cache.Entry<String, BinaryObject> entry;
                while (iterator.hasNext()){
                    entry = iterator.next();
                    System.out.println(entry.getKey() + "=====" + entry.getValue());
                }
            }

            // {
            //     ClientCache<String, String> cache1 = igniteClient.getOrCreateCache(Constants.CACHE_PATH_GRID);
            //     cache1.put("aaa", "bbb");
            //     ClientCache<String, Set<String>> cache2 = igniteClient.getOrCreateCache(Constants.CACHE_GRID_PATH);
            //     Set<String> s = new HashSet<>();
            //     s.add("ccc");
            //     s.add("ddd");
            //     cache2.put("111", s);
            //     System.out.println(cache2.get("111"));
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
