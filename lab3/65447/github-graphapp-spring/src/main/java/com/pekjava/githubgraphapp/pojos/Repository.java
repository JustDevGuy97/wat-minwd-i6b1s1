package com.pekjava.githubgraphapp.pojos;

public class Repository {
    private String title;

    public Repository(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
