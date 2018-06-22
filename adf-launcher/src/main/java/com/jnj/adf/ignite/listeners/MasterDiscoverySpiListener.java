package com.jnj.adf.ignite.listeners;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.spi.discovery.DiscoverySpiCustomMessage;
import org.apache.ignite.spi.discovery.DiscoverySpiListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Discovery Spi Listener for master grid
 *
 * @author Tang
 */
public class MasterDiscoverySpiListener implements DiscoverySpiListener {
    private final static Logger logger = LoggerFactory.getLogger(MasterDiscoverySpiListener.class);

    @Override
    public void onLocalNodeInitialized(ClusterNode locNode) {

    }

    @Override
    public void onDiscovery(int type, long topVer, ClusterNode node, Collection<ClusterNode> topSnapshot, @Nullable
            Map<Long, Collection<ClusterNode>> topHist, @Nullable DiscoverySpiCustomMessage data) {

        logger.info("Received new discovery event,type : {}", type, data);
    }
}
