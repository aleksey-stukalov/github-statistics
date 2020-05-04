package com.company.ghstat.core;

import com.company.ghstat.entity.Contributor;
import com.company.ghstat.entity.Repository;
import com.company.ghstat.entity.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Retrofit client for GitHub API
 *
 * @see <a href="https://developer.github.com/v3/">GitHub API</a>
 * @see <a href="https://square.github.io/retrofit/">Retrofit</a>
 */
public interface GithubRestClient {

    /**
     * Provides a retrofit call wrapper for the list of GitHub repositories.
     * Applies pagination with the default page size of 30.
     *
     * @param organization GitHub organization, e.g. cuba-platform
     * @param page Number of page to request
     * @return Returns retrofit call wrapper for the repositories request
     *
     * @see <a href="https://developer.github.com/v3/repos/#list-organization-repositories">Repositories GitHub API</a>
     */
    @GET("/orgs/{org}/repos")
    Call<List<Repository>> repos(@Path("org") String organization
            , @Query("page") int page);

    /**
     * Provides a retrofit call wrapper for the list of GitHub contributors.
     * Applies pagination with the page size of {@code perPage}.
     *
     * @param owner GitHub owner, e.g. cuba-platform
     * @param repo GitHub repository, e.g. cuba
     * @param page Number of page to request
     * @param perPage Records per page
     * @return Returns retrofit call wrapper for the contributors request
     *
     * @see <a href="https://developer.github.com/v3/repos/#list-contributors">Contributors GitHub API</a>
     */
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(@Path("owner") String owner
            , @Path("repo") String repo
            , @Query("page") int page
            , @Query("per_page") int perPage);


    /**
     * Provides a retrofit call wrapper for GitHub user details
     *
     * @param login GitHub user login
     * @return Returns retrofit call wrapper for the user details request
     *
     * @see <a href="https://developer.github.com/v3/users/#get-a-single-user">Users GitHub API</a>
     */
    @GET("/users/{login}")
    Call<UserInfo> userDetails(@Path("login") String login);


//    @Headers("accept: application/vnd.github.v3.star+json")
//    @GET("/repos/{owner}/{repo}/stargazers")
//    Call<List<Star>> getStars(@Path("owner") String owner
//            , @Path("repo") String repo
//            , @Query("page") int page
//            , @Query("per_page") int perPage);
}
