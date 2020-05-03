package com.company.ghstat.web.screens.contributor;

import com.company.ghstat.entity.Contributor;
import com.company.ghstat.entity.Repository;
import com.company.ghstat.entity.UserInfo;
import com.company.ghstat.service.GithubInfoService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aleksey Stukalov on 02.05.2020.
 */

@UiController("ghstat_Contributor.browse")
@UiDescriptor("contributor-browse.xml")
@LookupComponent("contributorsTable")
public class ContributorBrowse extends StandardLookup<Contributor> {
    @Inject
    private GithubInfoService githubInfoService;
    @Inject
    private Logger log;
    @Inject
    private Notifications notifications;
    @Inject
    private TextField<String> repoOwnerField;
    @Inject
    private LookupField<Repository> repoNameField;
    @Inject
    private CollectionLoader<Contributor> contributorsDl;
    @Inject
    private CollectionLoader<Repository> reposDl;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataManager dataManager;

    @Install(to = "reposDl", target = Target.DATA_LOADER)
    private List<Repository> reposDlLoadDelegate(LoadContext<Repository> loadContext) {
        repoNameField.setValue(null);
        if (repoOwnerField.getValue() == null)
            return new ArrayList<>();
        try {
            List<Repository> repos = githubInfoService.getRepos(repoOwnerField.getValue());
            repos.sort((o1, o2) -> o2.getStargazersCount().compareTo(o1.getStargazersCount()));
            return repos;
        } catch (Exception e) {
            log.error(e.getMessage());
            notifications.create(Notifications.NotificationType.ERROR)
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .withCaption(e.getMessage())
                    .show();
            return new ArrayList<>();
        }
    }

    @Subscribe(id = "reposDl", target = Target.DATA_LOADER)
    public void onReposDlPostLoad(CollectionLoader.PostLoadEvent<Repository> event) {
        if (event.getLoadedEntities().size() > 0)
            repoNameField.setValue(event.getLoadedEntities().get(0));
    }

    @Install(to = "contributorsDl", target = Target.DATA_LOADER)
    private List<Contributor> contributorsDlLoadDelegate(LoadContext<Contributor> loadContext) {
        if (repoNameField.getValue() == null)
            return new ArrayList<>();

        try {
            int page = contributorsDl.getFirstResult() / contributorsDl.getMaxResults() + 1;
            return githubInfoService.getContributors(repoNameField.getValue(), page, contributorsDl.getMaxResults());
        } catch (Exception e) {
            log.error(e.getMessage());
            notifications.create(Notifications.NotificationType.ERROR)
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .withCaption(e.getMessage())
                    .show();
            return new ArrayList<>();
        }
    }

    @Subscribe("repoOwnerField")
    public void onRepoOwnerFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        reposDl.load();
    }

    @Subscribe("repoNameField")
    public void onRepoNameFieldValueChange(HasValue.ValueChangeEvent<Repository> event) {
        contributorsDl.load();
    }

    @Install(to = "contributorsTable.login", subject = "columnGenerator")
    private Component contributorsTableLoginColumnGenerator(Contributor contributor) {
        LinkButton linkButton = uiComponents.create(LinkButton.class);
        linkButton.setCaption(contributor.getLogin());
        linkButton.addClickListener(event -> {
            UserInfo userInfo = dataManager.create(UserInfo.class);
            userInfo.setLogin(contributor.getLogin());
            screenBuilders.editor(UserInfo.class, this)
                    .editEntity(userInfo)
                    .withOpenMode(OpenMode.DIALOG)
                    .show();
        });
        return linkButton;
    }
}