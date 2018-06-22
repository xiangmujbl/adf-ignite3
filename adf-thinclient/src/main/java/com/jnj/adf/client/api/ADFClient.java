package com.jnj.adf.client.api;

import com.jnj.adf.client.api.conf.TableConfiguration;

/**
 * @author Tang
 */
public interface ADFClient {

    /***
     * connect to grid use default naming
     * @param namingServer naming server
     * @return success
     */
    boolean connect(String namingServer);

    /***
     * close all pool,destroy proxy regions
     */
    void disconnect();

    /***
     * check the connect status
     * @param group
     * @return
     */
    boolean isConnect(String group);


    /***
     *
     * @param userName
     * @param password
     * @return
     */
    String login(String userName, String password);

    /**
     * create a new table
     * @param grid grid id
     * @param name table name
     * @return ADFTable
     */
    ADFTable createTable(String grid, String name);

    /**
     * create a new table
     * @param grid grid id
     * @param conf table configuration
     * @return ADFTable
     */
    ADFTable createTable(String grid, TableConfiguration conf);

    /**
     * get table by name
     * @param name table name
     * @return ADFTable
     */
    ADFTable getTable(String name);

    /**
     * check table exists
     * @param name table name
     * @return exists
     */
    boolean existsTable(String name);
}
