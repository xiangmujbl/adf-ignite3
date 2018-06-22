package com.jnj.adf.ignite.common;

import java.util.UUID;

/**
 * @author Tang
 */
public class IgniteRuntime {

    private static IgniteRuntime runtime;

    public synchronized static IgniteRuntime getRuntime(){
        if(runtime == null)
            runtime = new IgniteRuntime();
        return runtime;
    }

    /**
     * node id
     */
    private UUID id;
    /**
     * grid role
     */
    private String role;
    /**
     * naming server used to connect master grid
     */
    private String namingServer;

    /**
     * addresses for client to connect
     */
    private String addresses;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNamingServer() {
        return namingServer;
    }

    public void setNamingServer(String namingServer) {
        this.namingServer = namingServer;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
