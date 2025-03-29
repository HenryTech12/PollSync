package com.votingapi.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.votingapi.demo.dto.NewsDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class NewsService {

    @Value("${news-api-key}")
    private String apiKey;
    private String sortBy = "publishedAt";
    private String q = "tesla";
    private String baseUrl = "https://newsapi.org/v2/everything?q="+q+"&sortBy="+sortBy+"&apiKey="+apiKey;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    public NewsService(String apiKey, String baseUrl) {
        //this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public void updateParameters(String q, String sortBy) {
        this.q = q;
        this.sortBy = sortBy;
        baseUrl = "https://newsapi.org/v2/everything?q="+q+"&sortBy="+sortBy+"&apiKey="+apiKey;
    }

    public void updateParameters(String q) {
        this.q = q;
        baseUrl = "https://newsapi.org/v2/everything?q="+q+"&sortBy="+sortBy+"&apiKey="+apiKey;
    }

    public void sort(String sortBy) {
        this.sortBy = sortBy;
        baseUrl = "https://newsapi.org/v2/everything?q="+q+"&sortBy="+sortBy+"&apiKey="+apiKey;
    }


    public NewsService() {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public NewsDTO readJSON() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

       HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
       return objectMapper.readValue(httpResponse.body(), new TypeReference<NewsDTO>() {
       });
    }
}
