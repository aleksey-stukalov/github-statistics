package com.company.ghstat.service;

import com.company.ghstat.entity.Contributor;
import com.company.ghstat.entity.Repository;
import com.company.ghstat.entity.UserInfo;

import java.io.IOException;
import java.util.List;

/**
 * <p>Provides access from CUBA mechanisms to the GitHub API</p>
 *
 * @see com.company.ghstat.core.GithubRestClient
 */

public interface GithubInfoService {
    String NAME = "ghstat_GithubInfoService";

    /**
     * Provides all repositories for the GitHub organization
     *
     * @param organization GitHub organization, e.g. cuba-platform
     *
     * @return Returns a list of all repositories
     * @throws IOException when GitHub API cannot be reached
     */
    List<Repository> getRepos(String organization) throws IOException;

    /**
     * Provides all repositories for the GitHub organization
     *
     * @param repository GitHub repository, e.g. cuba for the cuba-platform organization
     * @param page number of page to request
     * @param pageSize number of results for the requested page
     *
     * @return Returns a result list of contributors, desc ordered by a number of contributions.
     *         Number of list elements equals to {@code pageSize} and belonging to the specified {@code page}.
     *
     * @throws IOException when GitHub API cannot be reached
     */
    List<Contributor> getContributors(Repository repository, int page, int pageSize) throws IOException;

    /**
     * Provides GithHub user details
     *
     * @param login GitHub user login
     * @return Returns GithHub user details
     * @throws IOException when GitHub API cannot be reached
     */
    UserInfo getUserInfo(String login) throws IOException;
}