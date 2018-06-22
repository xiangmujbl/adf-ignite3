package com.jnj.adf.client.api;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Tang
 */
public final class ADFClientFactory {

    /**
     * create a new client
     * @return ADFClient
     */
    public static ADFClient newClient(){
        ServiceLoader<ADFClient> loader = ServiceLoader.load(ADFClient.class);
        Iterator<ADFClient> iterator = loader.iterator();
        if(iterator.hasNext())
            return iterator.next();
        return null;
    }
}
