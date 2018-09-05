package com.jnj.adf.ignite;

import com.jnj.adf.ignite.common.Utils;
import com.jnj.adf.ignite.domain.GridInfo;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static com.jnj.adf.ignite.common.Constants.*;

/**
 * active master cluster
 *
 * @author Tang
 */
public class MasterActivator {

    private final static Logger logger = LoggerFactory.getLogger(MasterActivator.class);

    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start("master-client.xml")) {
            IgniteCluster cluster = ignite.cluster();
            logger.info("Cluster active : {}", cluster.active());
            cluster.active(false);
            if (!cluster.active()) {
                Collection<ClusterNode> nodes = cluster.forServers().nodes();
                logger.info("Cluster has server nodes : {}", nodes);
                cluster.setBaselineTopology(nodes);
                cluster.active(true);
                initSystemCache();
            }
        }
    }

    private static void initSystemCache() {
        Ignite ignite = Ignition.ignite();

        Collection<String> existsNames = ignite.cacheNames();

        {
            if (!existsNames.contains(CACHE_GRIDS)) {
                // Create cache to store grid information
                CacheConfiguration<String, GridInfo> conf = new CacheConfiguration<>(CACHE_GRIDS);
                conf.setSqlSchema(SCHEMA_ADF);
                conf.setCacheMode(CacheMode.REPLICATED);
                conf.setDataRegionName("Default_Region");
                // gridCache.setSqlOnheapCacheEnabled(true);
                conf.setQueryEntities(Collections.singleton(Utils.toQueryEntity(TABLE_GRIDS, String.class, GridInfo
                        .class)));
                IgniteCache<String, GridInfo> cache = ignite.createCache(conf);
                String role = "master";
                GridInfo grid = new GridInfo();
                grid.setName(role);
                grid.setAddress("127.0.0.1:10801");
                cache.put(role, grid);
            }
        }

        {
            if (!existsNames.contains(CACHE_PATH_GRID)) {
                // Create cache to store the relationship between path and grid
                CacheConfiguration<String, String> cache = new CacheConfiguration<>(CACHE_PATH_GRID);
                cache.setSqlSchema(SCHEMA_ADF);
                cache.setCacheMode(CacheMode.REPLICATED);
                cache.setQueryEntities(Collections.singleton(Utils.toQueryEntity(TABLE_PATH_GRID, String.class,
                        String.class)));
                ignite.createCache(cache);
            }
        }

        {
            if (!existsNames.contains(CACHE_GRID_PATH)) {
                // Create cache to store the relationship between grid and path
                CacheConfiguration<String, Set<String>> cache = new CacheConfiguration<>(CACHE_GRID_PATH);
                cache.setSqlSchema(SCHEMA_ADF);
                cache.setCacheMode(CacheMode.REPLICATED);
                cache.setQueryEntities(Collections.singleton(Utils.toQueryEntity(TABLE_GRID_PATH, String.class,
                        String.class)));
                ignite.createCache(cache);
            }
        }
    }
}
