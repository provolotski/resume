package by.minsk.resume.service;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.model.NotificationMessage;

public interface NotificationSendService {
    void sendNotification(NotificationMessage message);
    String getDestinationAddress(Profile profile);
}
