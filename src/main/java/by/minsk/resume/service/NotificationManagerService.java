package by.minsk.resume.service;

import by.minsk.resume.entity.Profile;

public interface NotificationManagerService {
    void sendRestoreAccessLink(Profile profile, String restoreLink);
    void sentPasswordChanged(Profile profile);
}
