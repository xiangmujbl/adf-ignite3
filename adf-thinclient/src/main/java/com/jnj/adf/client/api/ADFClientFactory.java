package com.jnj.adf.client.api;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Factory used to create client
 *
 * @author Tang
 */
public final class ADFClientFactory {

    private static ADFClient client = null;

    /**
     * create a new client
     *
     * @return ADFClient
     */
    public synchronized static ADFClient newClient() {
        if (client == null) {
            ServiceLoader<ADFClient> loader = ServiceLoader.load(ADFClient.class);
            Iterator<ADFClient> iterator = loader.iterator();
            if (iterator.hasNext())
                client = iterator.next();
        }
        return client;
    }

    /**
     * get current client
     *
     * @return current client
     */
    public static ADFClient currentClient() {
        return client;
    }
}
