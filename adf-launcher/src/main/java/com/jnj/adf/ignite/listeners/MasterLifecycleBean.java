package com.jnj.adf.ignite.listeners;

import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ignite lifecycle bean implementation
 *
 * @author Tang
 */
public class MasterLifecycleBean implements LifecycleBean {
    private final static Logger logger = LoggerFactory.getLogger(MasterLifecycleBean.class);

    @Override
    public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {
        switch (evt) {
            case BEFORE_NODE_START:
                logger.info("Node will start. {}", evt);
                break;
            case AFTER_NODE_START:
                logger.info("Node is starting!!");
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
}
