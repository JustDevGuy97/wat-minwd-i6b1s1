package com.pekjava.githubgraphapp.pojos;

import java.util.List;

public class User {
    private String name;
    private int levels;
    private List<Repository> repos;
    private List<Follower> followers;

    public User() {
    }

    public User(String name, int levels, List<Repository> repos, List<Follower> followers) {
        this.name = name;
        this.levels = levels;
        this.repos = repos;
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public List<Repository> getRepos() {
        return repos;
    }

    public List<Follower> getFollowers() {
        return followers;
    }

    public void setRepos(List<Repository> repos) {
        this.repos = repos;
    }

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }
}
