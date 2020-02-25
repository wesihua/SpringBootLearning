package com.wei.boot.elastic;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Author weisihua
 * @Date 2020/2/25 下午6:25
 */
public class Get {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = ESClientFactory.getClient();
        RestHighLevelClient client2 = ESClientFactory.getClient();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>> " + client.equals(client2));
        GetRequest request = new GetRequest("companys").id("1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        if(response != null && response.isExists()){
            String source = response.getSourceAsString();
            System.out.println(source);
        }
        client.close();
    }
}
