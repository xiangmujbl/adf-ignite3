package com.jnj.adf.ignite;

import com.jnj.adf.ignite.domain.GridInfo;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static com.jnj.adf.ignite.common.Constants.CACHE_GRIDS;

/**
 * @author Tang
 */
public class WriteDataTest {

    private final static Logger logger = LoggerFactory.getLogger(WriteDataTest.class);
    private final static long MAX_SIZE = 1;

    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start("master-client.xml")) {
            IgniteCache<String, GridInfo> cache = ignite.getOrCreateCache(CACHE_GRIDS);
            // ignite.cluster().disableWal(CACHE_GRIDS);
            for(int i = 0;i < MAX_SIZE;i++){
                String role = "master" + i;
                GridInfo grid = new GridInfo();
                grid.setName(role);
                grid.setAddress("127.0.0.1:10801");
                cache.put(role, grid);
            }
            // ignite.cluster().enableWal(CACHE_GRIDS);
        }
    }
}
