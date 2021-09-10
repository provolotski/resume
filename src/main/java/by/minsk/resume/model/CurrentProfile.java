package by.minsk.resume.model;

import by.minsk.resume.Constants;
import by.minsk.resume.entity.Profile;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class CurrentProfile extends User {
    private static final long serialVersionUID = 3850489832510630519L;
    private final Long id;
    private final String fullName;

    public CurrentProfile(Profile profile) {
        super(profile.getUid(), profile.getPassword(), true, true, true, true, Collections.singleton(new SimpleGrantedAuthority(Constants.USER)));
        this.id = profile.getId();
        this.fullName = profile.getFullName();

    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return String.format("Current profile [id= %s, username = %s]",id,getUsername());
    }
}
