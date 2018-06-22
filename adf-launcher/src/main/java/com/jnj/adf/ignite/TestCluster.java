package com.jnj.adf.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tang
 */
public class TestCluster {
    private final static Logger logger = LoggerFactory.getLogger(TestCluster.class);
    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start("grid-client.xml")) {
            IgniteCluster cluster = ignite.cluster();
            cluster.active(true);
            logger.info("The oldest node of cluster is {}", cluster.forOldest().nodes());
        }
    }
}
