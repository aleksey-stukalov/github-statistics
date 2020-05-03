package com.company.ghstat.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseLongIdEntity;

import javax.validation.constraints.NotNull;

@NamePattern("%s|login")
@MetaClass(name = "ghstat_Contributor")
public class Contributor extends BaseLongIdEntity {
    private static final long serialVersionUID = 7556564437111382511L;

    @NotNull
    @MetaProperty(mandatory = true)
    private String login;

    @MetaProperty
    private Long contributions;

    public void setContributions(Long contributions) {
        this.contributions = contributions;
    }

    public Long getContributions() {
        return contributions;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}