package by.minsk.resume.service;

import by.minsk.resume.entity.Profile;

public interface SocialService<T> {
    Profile loginViaSocialNetwork(T model);
}
