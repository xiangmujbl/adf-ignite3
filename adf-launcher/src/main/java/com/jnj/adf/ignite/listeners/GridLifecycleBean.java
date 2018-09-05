package com.jnj.adf.ignite.listeners;

import com.jnj.adf.ignite.common.IgniteRuntime;
import com.jnj.adf.ignite.domain.GridInfo;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.jnj.adf.ignite.common.Constants.*;

/**
 * Ignite lifecycle bean implementation
 *
 * @author Tang
 */
public class GridLifecycleBean implements LifecycleBean {
    private final static Logger logger = LoggerFactory.getLogger(GridLifecycleBean.class);

    @Override
    public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {
        switch (evt) {
            case BEFORE_NODE_START:
                logger.info("Node will start. {}", evt);
                break;
            case AFTER_NODE_START:
                logger.info("Node is starting!!");
                register2master();
                break;
            case BEFORE_NODE_STOP:
                logger.info("Node will stop!!");
                break;
            case AFTER_NODE_STOP:
                logger.info("Node is stopping!!");
                break;
            default:
                break;
        }
    }

    /**
     * register grid to master grid
     */
    private void register2master() {
        Ignite ignite = Ignition.ignite();
        IgniteConfiguration igniteConf = ignite.configuration();
        // ClientConnectorConfiguration clientConf = igniteConf.getClientConnectorConfiguration();
        Map<String, ?> userAttrs = igniteConf.getUserAttributes();
        if (userAttrs != null) {
            String role = null;
            if (userAttrs.containsKey(USER_ATTR_ROLE)) {
                role = userAttrs.get(USER_ATTR_ROLE).toString();
                IgniteRuntime.getRuntime().setRole(role);
            }
            String connectAddresses = null;
            if (userAttrs.containsKey(USER_ATTR_CONNECT_ADDRESSES)) {
                connectAddresses = userAttrs.get(USER_ATTR_CONNECT_ADDRESSES).toString();
                IgniteRuntime.getRuntime().setAddresses(connectAddresses);
            }
            if (userAttrs.containsKey(USER_ATTR_NAMING_SERVER)) {
                String namingServer = userAttrs.get(USER_ATTR_NAMING_SERVER).toString();
                IgniteRuntime.getRuntime().setNamingServer(namingServer);
                ClientConfiguration cfg = new ClientConfiguration().setAddresses(namingServer);
                try (IgniteClient igniteClient = Ignition.startClient(cfg)) {
                    ClientCache<String, GridInfo> gridCache = igniteClient.getOrCreateCache(CACHE_GRIDS);
                    GridInfo grid = new GridInfo();
                    grid.setName(role);
                    grid.setAddress(connectAddresses);
                    gridCache.put(role, grid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
