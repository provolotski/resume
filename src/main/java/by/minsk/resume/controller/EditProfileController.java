package by.minsk.resume.controller;



import by.minsk.resume.form.SkillForm;
import by.minsk.resume.repository.storage.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import by.minsk.resume.repository.storage.SkillCategoryRepository;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EditProfileController {

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditProfile(){
        return "edit";
    }


    @RequestMapping(value = "/edit/skills",method = RequestMethod.GET)
    public String getEditTechSkills(Model model){
        model.addAttribute("skillForm",new SkillForm(profileRepository.findOne(1L).getSkills()));
        return gotoSkillsJSP(model);
    }
    @RequestMapping(value = "/edit/skills", method = RequestMethod.POST)
    public String saveEditTechSkills(@ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return gotoSkillsJSP(model);
        }
        //TODO Update skills
        return "redirect:/mike-ross";
    }

    private String gotoSkillsJSP(Model model) {
        model.addAttribute("skillCategories", skillCategoryRepository.findAll(new Sort("id")));
        return "/edit/skills";
    }


}
