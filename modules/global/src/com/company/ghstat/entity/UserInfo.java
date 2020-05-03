package com.company.ghstat.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseLongIdEntity;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|login")
@MetaClass(name = "ghstat_UserDetails")
public class UserInfo extends BaseLongIdEntity {
    private static final long serialVersionUID = -438984508557036609L;

    @MetaProperty
    protected String login;

    @MetaProperty
    protected String avatarUrl;

    @MetaProperty
    protected String email;

    @MetaProperty
    protected String company;

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

}