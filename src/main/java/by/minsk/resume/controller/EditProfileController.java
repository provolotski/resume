package by.minsk.resume.controller;

import javax.validation.Valid;


import by.minsk.resume.model.CurrentProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.minsk.resume.form.SkillForm;
import by.minsk.resume.service.EditProfileService;
import by.minsk.resume.util.SecurityUtil;

@Controller
public class EditProfileController {

    @Autowired
    private EditProfileService editProfileService;

    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String getEditProfile(){
        return "edit";
    }

    @RequestMapping(value = "/edit/skills", method = RequestMethod.GET)
    public String getEditSkills(Model model) {
        model.addAttribute("skillForm", new SkillForm(editProfileService.listSkills(SecurityUtil.getCurrentIdProfile())));
        return gotoSkillsJSP(model);
    }

    @RequestMapping(value = "/edit/skills", method = RequestMethod.POST)
    public String saveEditSkills(@Valid @ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return gotoSkillsJSP(model);
        }
        editProfileService.updateSkills(SecurityUtil.getCurrentIdProfile(), form.getItems());
        return "redirect:/mike-ross";
    }

    private String gotoSkillsJSP(Model model){
        model.addAttribute("skillCategories", editProfileService.listSkillCategories());
        return "edit/skills";
    }
    @RequestMapping(value = "/my-profile")
    public String getMyProfile(@AuthenticationPrincipal CurrentProfile currentProfile)
    {
        return "redirect:/"+currentProfile.getUsername();
    }
}
