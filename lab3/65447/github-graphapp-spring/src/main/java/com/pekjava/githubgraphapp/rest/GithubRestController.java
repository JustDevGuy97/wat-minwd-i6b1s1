package com.pekjava.githubgraphapp.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pekjava.githubgraphapp.GithubGraphappApplication;
import com.pekjava.githubgraphapp.pojos.Follower;
import com.pekjava.githubgraphapp.pojos.MyDtoNullKeySerializer;

import com.pekjava.githubgraphapp.pojos.Repository;
import com.pekjava.githubgraphapp.pojos.User;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.github.*;
import org.kohsuke.github.extras.OkHttpConnector;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GithubRestController {

    private static ObjectMapper mapper = new ObjectMapper();
    private static OkHttpClient client = new OkHttpClient();

    private String name;
    private int levels;

//    private String run(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            return response.body().string();
//        } finally {
//            response.body().close();
//        }
//    }

//    @GetMapping("/repos")
//    @ResponseBody
//    public List<Repository> getRepos(@RequestParam("user") String theNick) {
//        GitHub gitHub = GithubGraphappApplication.getGithub();
//
////        List<Repository> repos = new ArrayList<>();
////        mapper.getSerializerProvider().setNullKeySerializer(new MyDtoNullKeySerializer());
//
//        try {
//            Map<String, GHRepository> repos = gitHub.getUser(theNick).getRepositories();
//            List<Repository> reposList = new ArrayList<>();
//            System.out.println(repos.keySet());
//            for (String key : repos.keySet()) {
//                reposList.add(new Repository(key));
//            }
////            String repos = run("https://api.github.com/users/" + theNick + "/repos");
//            return reposList;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @GetMapping("/followers")
//    @ResponseBody
//    public List<Follower> getFollowers(@RequestParam("user") String theUser, @RequestParam("levels") int levels) {
//        GitHub github = GithubGraphappApplication.getGithub();
//
//        try {
//            GHPersonSet<GHUser> followers = github.getUser(theUser).getFollowers();
//            List<Follower> followersList = new ArrayList<>();
//
//
//            for (GHUser follower : followers) {
//                Follower tmpFollower = new Follower(follower.getLogin(), 1);
//                followersList.add(tmpFollower);
//                if(levels == 2) {
//                    for(GHUser follower2 : follower.getFollowers()) {
//                        Follower tmpFollower2 = new Follower(follower2.getLogin(), 2);
//                        tmpFollower.getFollowers().add(tmpFollower2);
//                    }
//                }
//            }
//            return followersList;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @PostMapping("/users")
    @CrossOrigin
    public User sendInfo(User theUser) {
        this.name = theUser.getName();
        this.levels = theUser.getLevels();
        System.out.println(name + " " + levels);
        return getInfo();
    }


    @GetMapping("/user")
    @ResponseBody
    @CrossOrigin
    public User getInfo() {
        GitHub gitHub = GithubGraphappApplication.getGithub();

        try {
            Map<String, GHRepository> repos = gitHub.getUser(name).getRepositories();
            List<Repository> reposList = new ArrayList<>();
            System.out.println(repos.keySet());
            for (String key : repos.keySet()) {
                reposList.add(new Repository(key));
            }

            GHPersonSet<GHUser> followers = gitHub.getUser(name).getFollowers();
            List<Follower> followersList = new ArrayList<>();
            if(levels != 0) {
                for (GHUser follower : followers) {
                    Follower tmpFollower = new Follower(follower.getLogin(), 1);
                    followersList.add(tmpFollower);
                    if (levels == 2) {
                        for (GHUser follower2 : follower.getFollowers()) {
                            Follower tmpFollower2 = new Follower(follower2.getLogin(), 2);
                            tmpFollower.getFollowers().add(tmpFollower2);
                        }
                    }
                }
            }

            User user = new User(name, levels, reposList, followersList);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
