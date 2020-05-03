package com.company.ghstat.service;

import com.company.ghstat.entity.Contributor;
import com.company.ghstat.entity.Repository;
import com.company.ghstat.entity.UserInfo;

import java.io.IOException;
import java.util.List;

public interface GithubInfoService {
    String NAME = "ghstat_GithubInfoService";

    List<Repository> getRepos(String organization) throws IOException;

    List<Contributor> getContributors(Repository repository, int page, int pageSize) throws IOException;

    UserInfo getUserInfo(String login) throws IOException;
}