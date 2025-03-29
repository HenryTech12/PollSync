package com.votingapi.demo.dto;

import java.util.List;

public class NewsDTO {

    private String status;
    private int totalResults;
    List<ArticlesDTO> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticlesDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesDTO> articles) {
        this.articles = articles;
    }
}
