package by.minsk.resume.controller;

import by.minsk.resume.Constants;
import by.minsk.resume.entity.Profile;
import by.minsk.resume.model.CurrentProfile;
import by.minsk.resume.service.FindProfileService;

import by.minsk.resume.util.SecurityUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
public class PublicDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicDataController.class);

    @Autowired
    private FindProfileService findProfileService;

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public String getProfile(@PathVariable("uid") String uid, Model model) {
        Profile profile = findProfileService.findByUid(uid);
        if (profile == null) {
            return "profile-not-found";
        }
        model.addAttribute("profile", profile);
        return "profile";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String getError() {
        return "error";
    }

    @RequestMapping(value = {"/welcome"})
    public String listAll(Model model) {
        Page<Profile> profiles = findProfileService.findAll(new PageRequest(0, Constants.MAX_PROFILES_PER_PAGE, new Sort("id")));
        model.addAttribute("profiles", profiles.getContent());
        model.addAttribute("page", profiles);
        return "profiles";
    }

    @RequestMapping(value = "/fragment/more", method = RequestMethod.GET)
    public String moreProfiles(Model model, @PageableDefault(size = Constants.MAX_PROFILES_PER_PAGE) @SortDefault(sort = "id") Pageable pageable) throws UnsupportedEncodingException {
        Page<Profile> profiles = findProfileService.findAll(pageable);
        model.addAttribute("profiles", profiles.getContent());
        return "fragment/profile-items";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchProfile(Model model, @RequestParam(value = "query", required = false) String query,
                                @PageableDefault(size = Constants.MAX_PROFILES_PER_PAGE) @SortDefault(sort = "id") Pageable pageable) throws UnsupportedEncodingException {
        Page<Profile> profiles = null;
        if (StringUtils.isNotBlank(query)) {
            LOGGER.debug("Ищем: {}", query);
            profiles = findProfileService.findBySearchQuery(query, pageable);
            if (profiles.isLast()) {
                return "profile-not-found";
            }
        } else {
            profiles = findProfileService.findAll(pageable);
        }
        model.addAttribute("profiles", profiles.getContent());
        return "fragment/profile-items";
    }

    @RequestMapping(value = "/sign-in")
    public String signIn() {
        CurrentProfile currentProfile = SecurityUtil.getCurrentProfile();
        if(currentProfile != null) {
            return "redirect:/" + currentProfile.getUsername();
        }
        else{
            return "sign-in";
        }
    }
    @RequestMapping(value = "/sign-in-failed")
    public String signInFailed(HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null) {
            return "redirect:/sign-in";
        }
        return "sign-in";
    }


}
