package com.jnj.adf.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 * Used to start Ignite server
 * @author Tang
 */
public class GridServer {

    public static void main(String[] args) {
        Ignite ignite = Ignition.start("grid.xml");
    }
}
