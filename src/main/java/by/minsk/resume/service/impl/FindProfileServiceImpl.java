package by.minsk.resume.service.impl;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.model.CurrentProfile;
import by.minsk.resume.repository.search.ProfileSearchRepository;
import by.minsk.resume.repository.storage.ProfileRepository;
import by.minsk.resume.service.FindProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindProfileServiceImpl implements FindProfileService, UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindProfileServiceImpl.class);

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileSearchRepository profileSearchRepository;

    @Override
    public Profile findByUid(String uid) {
        return profileRepository.findByUid(uid);
    }

    @Override
    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Iterable<Profile> findAllForIndexing() {
        Iterable<Profile> all = profileRepository.findAll();
        for (Profile p : all) {
            p.getSkills().size();
            p.getCertificates().size();
            p.getLanguages().size();
            p.getPractics().size();
            p.getCourses().size();
        }
        return all;
    }

    @Override
    public Page<Profile> findBySearchQuery(String query, Pageable pageable) {
        return profileSearchRepository.findByObjectiveLikeOrSummaryLikeOrInfoOrPracticsCompanyLikeOrPracticsPositionLike(
                query, query, query, query, query, pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("user name is" + username);
        Profile profile = findProfile(username);
        if (profile != null) {
            LOGGER.error("user uid is " + profile.getUid());
            LOGGER.error("user password is " + profile.getPassword());
            LOGGER.error("user password is " + profile.getPassword());
            return new CurrentProfile(profile);
        } else {
            LOGGER.error("Profile not found by " + username);
            throw new UsernameNotFoundException("Profile not found by " + username);
        }
    }

    private Profile findProfile(String anyUniqueId) {
        Profile profile = profileRepository.findByUid(anyUniqueId);
        if (profile == null) {
            profile = profileRepository.findByEmail(anyUniqueId);
            if (profile == null) {
                profile = profileRepository.findByPhone(anyUniqueId);
            }
        }
        return profile;
    }
}
