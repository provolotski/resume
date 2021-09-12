package by.minsk.resume.service.impl;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.repository.storage.ProfileRepository;
import by.minsk.resume.service.SocialService;
import com.restfb.types.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacebookSocialService implements SocialService<User> {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile loginViaSocialNetwork(User model) {
        if(StringUtils.isNotBlank(model.getEmail())){
            Profile profile = profileRepository.findByEmail(model.getEmail());
            if (profile != null){
                return profile;
            }
        }
        return null;
    }
}
