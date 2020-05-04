package com.company.ghstat.web.screens.userinfo;

import com.company.ghstat.entity.UserInfo;
import com.company.ghstat.service.GithubInfoService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.components.UrlResource;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * {@link UserInfo} edit screen
 */

@UiController
@UiDescriptor("user-info-view.xml")
@LoadDataBeforeShow
public class UserInfoView extends Screen {
    @Inject
    private GithubInfoService githubInfoService;
    @Inject
    private Logger log;
    @Inject
    private Notifications notifications;
    @Inject
    private Image avatar;

    private String userLogin;
    @Inject
    private DataManager dataManager;

    /**
     * Re-loads the editing instance of {@link UserInfo} entity into the {@code userInfoDl} data container.
     *
     * @param loadContext parameter is not used in the implementation
     * @return Returns an instance of {@link UserInfo}
     */
    @Install(to = "userInfoDl", target = Target.DATA_LOADER)
    private UserInfo userInfoDlLoadDelegate(LoadContext<UserInfo> loadContext) {
        try {
            return githubInfoService.getUserInfo(userLogin);
        } catch (IOException e) {
            handleExceptionWithNotification(e);
            close(StandardOutcome.CLOSE);
            return null;
        }
    }

    /**
     * Sets avatar url as a source into {@link Image} component
     */
    @Subscribe(id = "userInfoDc", target = Target.DATA_CONTAINER)
    public void onUserInfoDcItemChange(InstanceContainer.ItemChangeEvent<UserInfo> event) {
        if (event.getItem() == null || event.getItem().getAvatarUrl() == null) {
            avatar.reset();
        } else {
            try {
                URL url = new URL(event.getItem().getAvatarUrl());
                avatar.setSource(UrlResource.class).setUrl(url);
            } catch (MalformedURLException e) {
                handleExceptionWithNotification(e);
            }
        }
    }

    private void handleExceptionWithNotification(Exception e) {
        log.error(e.getMessage());
        notifications.create(Notifications.NotificationType.ERROR)
                .withPosition(Notifications.Position.BOTTOM_RIGHT)
                .withCaption(e.getMessage())
                .show();
    }

    public void setUserLoginToShow(String userLogin) {
        this.userLogin = userLogin;
    }

    @Subscribe("closeBtn")
    public void onCloseBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }
}