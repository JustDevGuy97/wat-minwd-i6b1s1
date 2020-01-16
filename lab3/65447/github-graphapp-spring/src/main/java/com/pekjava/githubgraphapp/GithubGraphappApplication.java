package com.pekjava.githubgraphapp;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@SpringBootApplication
public class GithubGraphappApplication {

    private static GitHub github = null;

    public static void main(String[] args) {
        SpringApplication.run(GithubGraphappApplication.class, args);
        authorizeGithub();
    }

    public static void authorizeGithub() {

        try {
            github = new GitHubBuilder().withOAuthToken("12d101d381f8e05e7067bf4c73bc4eeb1363d7e1").build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(github.getApiUrl());
    }

    public static GitHub getGithub() {
        return github;
    }

}
