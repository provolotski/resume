package by.minsk.resume.service.impl;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.model.NotificationMessage;
import by.minsk.resume.service.NotificationManagerService;
import by.minsk.resume.service.NotificationSendService;
import by.minsk.resume.service.NotificationTemplateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class NotificationManagerServiceImpl implements NotificationManagerService {
    public static final Logger LOGGER = LoggerFactory.getLogger(NotificationManagerServiceImpl.class);

    @Autowired
    private NotificationSendService notificationSendService;

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @Override
    public void sendRestoreAccessLink(Profile profile, String restoreLink) {
        LOGGER.debug("Restore link: {} for account {}", restoreLink, profile.getUid());
        Map<String, Object> model = new HashMap<>();
        model.put("profile",profile);
        model.put("restoreLink",restoreLink);
        processNotification(profile, "restoreAccessNotification",model);
    }

    @Override
    public void sentPasswordChanged(Profile profile) {
        LOGGER.debug("Password changed for account {}",profile.getUid());
        Map<String, Object> model = new HashMap<>();
        model.put("profile",profile);
        processNotification(profile, "passwordChangedNotification",model);


    }

    private void processNotification(Profile profile, String templateName, Object model){
        String destibationAddress = notificationSendService.getDestinationAddress(profile);
        if(StringUtils.isNotBlank(destibationAddress)){
            NotificationMessage notificationMessage = notificationTemplateService.createNotificationMessage(templateName, model);
            notificationMessage.setDestinationAddress(destibationAddress);
            notificationMessage.setDestinationName(profile.getFullName());
            notificationSendService.sendNotification(notificationMessage);
        }else{
            LOGGER.error("Notification Ignored: destination address is empty for profile {}",profile.getUid());
        }

    }
}
