package com.example.newsapp;

import java.util.ArrayList;

public class NewsModal {
    private int totalResults;
    private String status;
    // getiing  the articles
    private ArrayList<Articles> articles;
    //create constructor and geter and setter
    public NewsModal(int totalResults, String status, ArrayList<Articles> articles) {
        this.totalResults = totalResults;
        this.status = status;
        this.articles = articles;
    }


    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
    }
}
