package com.company.ghstat.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseLongIdEntity;

/**
 * Represents GitHub repository
 *
 * @see com.company.ghstat.core.GithubRestClient#repos
 * @see <a href="https://developer.github.com/v3/repos/#list-organization-repositories">Repositories GitHub API</a>
 */
@NamePattern("%s: ☆ %s|name,stargazersCount")
@MetaClass(name = "ghstat_Repository")
public class Repository extends BaseLongIdEntity {
    private static final long serialVersionUID = -6182069355182550647L;

    @MetaProperty
    private String name;

    @MetaProperty
    private Long stargazersCount;

    @MetaProperty
    private UserInfo owner;

    public Long getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Long stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }
}