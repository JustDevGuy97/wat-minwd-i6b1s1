package com.pekjava.githubgraphapp.pojos;

import java.util.ArrayList;
import java.util.List;

public class Follower {
    private String login;
    private int level;
    private List<Follower> followers = new ArrayList<>();

    public Follower(String login, int level) {
        this.login = login;
        this.level = level;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void addFollower(Follower follower) {
        followers.add(follower);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Follower> getFollowers() {
        return followers;
    }
}
