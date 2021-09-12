package by.minsk.resume.service;

import by.minsk.resume.model.NotificationMessage;

public interface NotificationTemplateService {
    NotificationMessage createNotificationMessage (String templateName, Object model);
}
