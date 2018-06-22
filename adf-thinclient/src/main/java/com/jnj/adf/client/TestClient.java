package com.jnj.adf.client;

import com.jnj.adf.client.api.ADFClient;
import com.jnj.adf.client.api.ADFClientFactory;
import com.jnj.adf.client.api.ADFTable;

/**
 * @author Tang
 */
public class TestClient {

    public static void main(String[] args) {
        ADFClient client = ADFClientFactory.newClient();
        if (client != null) {
            System.out.println(client);
            client.connect("127.0.0.1:10801");

            ADFTable table = client.createTable("grid1", "test1");
            table.put("aaa", "bbb");

            System.out.println(table.get("aaa"));
        }
    }
}
