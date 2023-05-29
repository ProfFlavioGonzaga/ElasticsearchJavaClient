package com.elasticsearch;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.client.ESClient;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ESClient esClient = new ESClient("math test");
        var searchResponse = esClient.search();

        List<Hit<ObjectNode>> hits = searchResponse.hits().hits();

        hits.forEach(h -> {
            assert h.source() != null;
            printResult(h.source().get("title").asText(), h.source().get("content").asText());
        });
    }

    private static void printResult(String title, String content) {
        System.out.println(title);
        System.out.println("-------------------");
        content = content.replaceAll("</?(som|math)\\d*>","");
        content = content.replaceAll("[^A-Za-z\\s]+", "");
        content = content.replaceAll("\\s+", " ");
        content = content.replaceAll("^\\s+", "");
        System.out.println(content);
        System.out.println("================================");
    }
}