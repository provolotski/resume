package by.minsk.resume.util;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.model.CurrentProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

    public static CurrentProfile  getCurrentProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CurrentProfile) {
            return ((CurrentProfile)principal);
        } else {
            return null;
        }
    }

    public static Long getCurrentIdProfile() {
        CurrentProfile currentProfile = getCurrentProfile();
        return currentProfile != null ? currentProfile.getId() : null;
    }
    public static void authentificate(Profile profile) {
        CurrentProfile currentProfile = new CurrentProfile(profile);
        LOGGER.info("KPSS password is" + currentProfile.getPassword());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                currentProfile, currentProfile.getPassword(), currentProfile.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static boolean isCurrentProfileAuthentificated() {
        return getCurrentProfile() != null;
    }

    public static String generateNewActionUid(){
        return UUID.randomUUID().toString();
    }

    public static String generateNewRestoreAccessToken(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
