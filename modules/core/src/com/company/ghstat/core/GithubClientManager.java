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

@Component(GithubClientManager.NAME)
public class GithubClientManager {
    public static final String NAME = "ghstat_GithubClientManager";

    public GithubRestClient unauthorized() {
        return get(builder -> builder.build().create(GithubRestClient.class));
    }

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

    public GithubRestClient get(Function<Retrofit.Builder, GithubRestClient> delegate) {
        return delegate.apply(baseBuilder());
    }

    private Retrofit.Builder baseBuilder() {
        return new Retrofit.Builder()
                .baseUrl(GithubRestClient.API_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create()));
    }
}