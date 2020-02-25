package com.wei.boot.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Author weisihua
 * @Date 2020/2/25 下午6:16
 */
public class ESClientFactory {

    private static class ClientHolder{
        private static final RestHighLevelClient CLIENT = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
    }

    public static RestHighLevelClient getClient(){
        return ClientHolder.CLIENT;
    }
}
