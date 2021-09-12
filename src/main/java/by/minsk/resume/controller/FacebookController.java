package by.minsk.resume.controller;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.service.SocialService;
import by.minsk.resume.util.SecurityUtil;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacebookController {
    @Value("${social.facebook.idClient}")
    private String idClient;

    @Value("${social.facebook.secret}")
    private String secret;

    private String redirectURL;

    @Value("${app.host}")
    public void setRedirectURL(String appHost) {
        this.redirectURL = appHost + "/fromFB";
    }

    @Autowired
    private SocialService<User> facebookSocialService;

    private String getAutorizeURL() {
        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.addPermission(FacebookPermissions.EMAIL);
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_4_0);
        return client.getLoginDialogUrl(idClient, redirectURL, scopeBuilder);
    }

    @RequestMapping(value = "/fbLogin", method = RequestMethod.GET)
    public String gotoFaceBook() {
        return "redirect:" + getAutorizeURL();
    }

    @RequestMapping(value = "/fromFB", method = RequestMethod.GET)
    public String fromFB(@RequestParam(value = "code", required = false) String code) {
        if (StringUtils.isBlank(code)) {
            return "redirect:/sign-in";
        }
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_4_0);
        AccessToken accessToken = client.obtainUserAccessToken(idClient, secret, redirectURL, code);
        client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_4_0);
        User user = client.fetchObject("me", User.class, Parameter.with("fields", "name, email,first_name,last_name"));
        Profile profile = facebookSocialService.loginViaSocialNetwork(user);
        if (profile != null) {
            SecurityUtil.authentificate(profile);
            return "redirect:/" + profile.getUid();
        } else {
            return "redirect:/sign-in";
        }
    }
}

