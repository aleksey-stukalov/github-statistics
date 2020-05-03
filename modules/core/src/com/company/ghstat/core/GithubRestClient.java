package com.company.ghstat.core;

import com.company.ghstat.entity.Contributor;
import com.company.ghstat.entity.Repository;
import com.company.ghstat.entity.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by Aleksey Stukalov on 02.05.2020.
 */
public interface GithubRestClient {

    String API_URL = "https://api.github.com";

    @GET("/orgs/{org}/repos")
    Call<List<Repository>> repos(@Path("org") String organization
            , @Query("page") int page);

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(@Path("owner") String owner
            , @Path("repo") String repo
            , @Query("page") int page
            , @Query("per_page") int perPage);


    @GET("/users/{login}")
    Call<UserInfo> userDetails(@Path("login") String login);


//    @Headers("accept: application/vnd.github.v3.star+json")
//    @GET("/repos/{owner}/{repo}/stargazers")
//    Call<List<Star>> getStars(@Path("owner") String owner
//            , @Path("repo") String repo
//            , @Query("page") int page
//            , @Query("per_page") int perPage);
}
