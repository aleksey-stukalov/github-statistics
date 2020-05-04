package com.company.ghstat.core;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.function.Function;

/**
 * <p>Utility bean encapsulating boilerplate for GitHub REST client generation</p>
 *
 * @see com.company.ghstat.core.GithubRestClient
 */
@Component(GithubClientManager.NAME)
public class GithubClientManager {
    public static final String NAME = "ghstat_GithubClientManager";

    public static String API_URL = "https://api.github.com";

    /**
     * Provides an unauthorized retrofit client for anonymous calls for the GitHub API
     *
     * @return Returns unauthorized retrofit client
     * @see <a href="https://developer.github.com/apps/building-github-apps/understanding-rate-limits-for-github-apps/">API calls limits</a>
     */
    public GithubRestClient unauthorized() {
        return get(builder -> builder.build().create(GithubRestClient.class));
    }

    /**
     * <p>Provides an authorized retrofit client for authorized calls for the GitHub API</p>
     * <p>Basic authentication is used. Make sure you use personal access token instead of password</p>
     *
     * @param login user login
     * @param password personal access token
     * @return Returns authorized retrofit client
     *
     * @see <a href="https://developer.github.com/apps/building-github-apps/understanding-rate-limits-for-github-apps/">
     *     API calls limits</a>
     * @see <a href="https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line">
     *     Creating a personal access token</a>
     */
    public GithubRestClient authorized(final String login, final String password) {
        return get(builder -> {
            OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request modifiedRequest = chain.request()
                        .newBuilder()
                        .header("Authorization"
                                , Credentials.basic(login, password))
                        .build();
                return chain.proceed(modifiedRequest);
            }).build();

            return baseBuilder()
                    .client(httpClient)
                    .build()
                    .create(GithubRestClient.class);
        });
    }

    protected GithubRestClient get(Function<Retrofit.Builder, GithubRestClient> delegate) {
        return delegate.apply(baseBuilder());
    }

    protected Retrofit.Builder baseBuilder() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create()));
    }
}