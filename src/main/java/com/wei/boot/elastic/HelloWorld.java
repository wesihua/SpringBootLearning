package com.wei.boot.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author weisihua
 * @Date 2020/2/25 下午4:16
 */
public class HelloWorld {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        IndexRequest request = new IndexRequest("companys");
        //request.type("book");
        //request.id("1");
        //request.opType(DocWriteRequest.OpType.CREATE);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "腾讯2");
        map.put("author", "china");
        //map.put("date", "2020-01-01 10:09:34");
        map.put("create", new Date());
        request.source(map);
        //IndexResponse response = null;
        try {
            //response = client.index(request, RequestOptions.DEFAULT);
            client.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
                @Override
                public void onResponse(IndexResponse indexResponse) {
                    DocWriteResponse.Result result = indexResponse.getResult();
                    System.out.println(result.name());
                    System.out.println(indexResponse.getVersion());
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (ElasticsearchException e) {
            if(e.status() == RestStatus.CONFLICT){
                System.out.println("文档冲突了！");
            }
            e.printStackTrace();
        }
//        DocWriteResponse.Result result =response.getResult();
//        System.out.println(result.name());
//        System.out.println(response.getVersion());
        //client.close();
    }
}
