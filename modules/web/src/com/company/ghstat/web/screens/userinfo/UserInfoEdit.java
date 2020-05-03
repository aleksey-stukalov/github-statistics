package com.company.ghstat.web.screens.userinfo;

import com.company.ghstat.service.GithubInfoService;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.components.UrlResource;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.ghstat.entity.UserInfo;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Aleksey Stukalov on 03.05.2020.
 */

@UiController("ghstat_UserDetails.edit")
@UiDescriptor("user-info-edit.xml")
@EditedEntityContainer("userInfoDc")
@LoadDataBeforeShow
public class UserInfoEdit extends StandardEditor<UserInfo> {
    @Inject
    private GithubInfoService githubInfoService;
    @Inject
    private Logger log;
    @Inject
    private Notifications notifications;
    @Inject
    private Image avatar;

    @Install(to = "userInfoDl", target = Target.DATA_LOADER)
    private UserInfo userInfoDlLoadDelegate(LoadContext<UserInfo> loadContext) {
        try {
            return githubInfoService.getUserInfo(getEditedEntity().getLogin());
        } catch (IOException e) {
            log.error(e.getMessage());
            notifications.create(Notifications.NotificationType.ERROR)
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .withCaption(e.getMessage())
                    .show();
            return getEditedEntity();
        }
    }

    @Subscribe(id = "userInfoDc", target = Target.DATA_CONTAINER)
    public void onUserInfoDcItemChange(InstanceContainer.ItemChangeEvent<UserInfo> event) {
        if (event.getItem() == null || event.getItem().getAvatarUrl() == null) {
            avatar.reset();
            return;
        }
        try {
            URL url = new URL(event.getItem().getAvatarUrl());
            avatar.setSource(UrlResource.class).setUrl(url);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            notifications.create(Notifications.NotificationType.ERROR)
                    .withPosition(Notifications.Position.BOTTOM_RIGHT)
                    .withCaption(e.getMessage())
                    .show();
        }
    }
}