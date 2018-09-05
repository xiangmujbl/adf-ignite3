package com.jnj.adf.client.ignite;

import com.jnj.adf.client.api.ADFClient;
import com.jnj.adf.client.api.ADFTable;
import com.jnj.adf.client.api.conf.TableConfiguration;
import com.jnj.adf.ignite.domain.GridInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.jnj.adf.ignite.common.Constants.*;

/**
 * ADF ignite client
 *
 * @author Tang
 */
public class ADFIgniteClient implements ADFClient {

    private AtomicBoolean connected = new AtomicBoolean(false);
    private IgniteClient igniteClient = null;

    private final static Map<String, IgniteClient> CONNECTIONS = new ConcurrentHashMap<>();

    /***
     * connect to grid use default naming
     * @param namingServer naming server
     * @return success
     */
    @Override
    public boolean connect(String namingServer) {
        ClientConfiguration cfg = new ClientConfiguration().setAddresses(namingServer);
        try {
            igniteClient = Ignition.startClient(cfg);
            connected.set(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return true;
    }

    @Override
    public void disconnect() {
        try {
            igniteClient.close();
            connected.set(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnect(String group) {
        return connected.get();
    }

    @Override
    public String login(String userName, String password) {
        return null;
    }

    /**
     * create a new table
     *
     * @param grid grid id
     * @param name table name
     * @return ADFTable
     */
    @Override
    public ADFTable createTable(String grid, String name) {
        if (igniteClient != null) {
            if (existsTable(name))
                return getTable(name);
            IgniteClient client;
            try {
                client = connectGrid(grid);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            if (client != null) {
                ClientCache cache = client.createCache(name);
                ClientCache<String, String> cache1 = igniteClient.getOrCreateCache(CACHE_PATH_GRID);
                cache1.put(name, grid);
                return new ADFIgniteTable(cache);
            }
        }
        return null;
    }

    /**
     * create a new table
     *
     * @param grid grid id
     * @param conf table configuration
     * @return ADFTable
     */
    @Override
    public ADFTable createTable(String grid, TableConfiguration conf) {
        return null;
    }

    /**
     * get table by table name
     *
     * @param name table name
     * @return ADFTable
     */
    @Override
    public ADFTable getTable(String name) {
        if (igniteClient != null) {
            IgniteClient client;
            try {
                client = connectGridByTable(name);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            if (client != null) {
                ClientCache cache = client.getOrCreateCache(name);
                return new ADFIgniteTable(cache);
            } else {
                ClientCache cache = igniteClient.getOrCreateCache(name);
                return new ADFIgniteTable(cache);
            }
        }
        return null;
    }

    /**
     * check table exists
     *
     * @param name table name
     * @return
     */
    @Override
    public boolean existsTable(String name) {
        if (igniteClient != null) {
            ClientCache<String, String> cache = igniteClient.getOrCreateCache(CACHE_PATH_GRID);
            return cache.containsKey(name);
        }
        return false;
    }

    /**
     * connect grid by grid name
     *
     * @param grid grid name
     * @return IgniteClient
     */
    private IgniteClient connectGrid(String grid) {
        if (StringUtils.isEmpty(grid))
            return null;
        if (CONNECTIONS.containsKey(grid))
            return CONNECTIONS.get(grid);
        if (igniteClient != null) {
            ClientCache<String, GridInfo> cache = igniteClient.getOrCreateCache(CACHE_GRIDS);
            GridInfo gridInfo = cache.get(grid);
            if (gridInfo != null && StringUtils.isNotBlank(gridInfo.getAddress())) {
                ClientConfiguration cfg = new ClientConfiguration().setAddresses(gridInfo.getAddress());
                IgniteClient client = Ignition.startClient(cfg);
                CONNECTIONS.put(grid, client);
                return client;
            }
        }
        return null;
    }

    /**
     * connect grid by table name
     *
     * @param name table name
     * @return IgniteClient
     */
    private IgniteClient connectGridByTable(String name) {
        if (igniteClient != null) {
            ClientCache<String, String> cache = igniteClient.getOrCreateCache(CACHE_PATH_GRID);
            String grid = cache.get(name);
            if (grid != null)
                return connectGrid(grid);
        }
        return null;
    }
}
