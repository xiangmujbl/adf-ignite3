package com.jnj.adf.ignite;

import com.jnj.adf.ignite.common.IgniteRuntime;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.events.Event;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Start master server node
 *
 * @author Tang
 */
public class MasterServer {

    private final static Logger logger = LoggerFactory.getLogger(MasterServer.class);

    public static void main(String[] args) {
        Ignite ignite = Ignition.start("master.xml");

        IgniteCluster cluster = ignite.cluster();
        logger.info("cluster status, activate : {}", cluster.active());
        logger.info("Ignite node id is {}", cluster.forLocal().node().id());
        IgniteRuntime.getRuntime().setId(cluster.forLocal().node().id());

        IgnitePredicate<Event> localPre = evt -> {
            logger.info("Received event [node = {} ,evt = {}]", evt.node().id(), evt.name());
            return true;
        };
        ignite.events().localListen(localPre, EventType.EVTS_DISCOVERY);

    }
}
