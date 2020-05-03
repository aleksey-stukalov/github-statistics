package com.company.ghstat.service;

import com.company.ghstat.core.GithubClientManager;
import com.company.ghstat.core.GithubRestClient;
import com.company.ghstat.entity.Contributor;
import com.company.ghstat.entity.Repository;
import com.company.ghstat.entity.UserInfo;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service(GithubInfoService.NAME)
public class GithubInfoServiceBean implements GithubInfoService {

    @Inject
    private GithubClientManager githubClientManager;

    @Override
    public List<Repository> getRepos(String organization) throws IOException {
        GithubRestClient client = githubClientManager.unauthorized();
        List<Repository> result = new ArrayList<>();
        boolean next = true;
        int page = 1;
        while (next) {
            Response<List<Repository>> response = client.repos(organization, page++).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request finished with error code: " + response.code());
            }
            List<Repository> repos = response.body();
            if (Objects.requireNonNull(repos).size() == 0)
                next = false;
            else
                result.addAll(repos);
        }
        return result;
    }

    @Override
    public List<Contributor> getContributors(Repository repository, int page, int pageSize) throws IOException {
        GithubRestClient client = githubClientManager.unauthorized();
        Response<List<Contributor>> response = client.contributors(repository.getOwner().getLogin(), repository.getName(), page, pageSize).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Request finished with error code: " + response.code());
        }
        return response.body();
    }

    @Override
    public UserInfo getUserInfo(String login) throws IOException {
        GithubRestClient client = githubClientManager.unauthorized();
        Response<UserInfo> response = client.userDetails(login).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Request finished with error code: " + response.code());
        }
        return response.body();
    }
}