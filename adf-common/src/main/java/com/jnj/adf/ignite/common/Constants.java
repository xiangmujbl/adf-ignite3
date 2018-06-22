package com.jnj.adf.ignite.common;

public interface Constants {

    String SCHEMA_ADF = "ADF";
    String TABLE_GRIDS = "GRIDS";
    String TABLE_PATH_GRID = "PATH_GRID";
    String TABLE_GRID_PATH = "GRID_PATH";
    String CACHE_GRIDS = "_" + SCHEMA_ADF + "_" + TABLE_GRIDS + "_";
    String CACHE_PATH_GRID = "_" + SCHEMA_ADF + "_" + TABLE_PATH_GRID + "_";
    String CACHE_GRID_PATH = "_" + SCHEMA_ADF + "_" + TABLE_GRID_PATH + "_";

    String USER_ATTR_ROLE = "role";
    String USER_ATTR_NAMING_SERVER = "naming.server";
    String USER_ATTR_CONNECT_ADDRESSES = "connect.addresses";
}
