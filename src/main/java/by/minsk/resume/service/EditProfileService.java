package by.minsk.resume.service;

import by.minsk.resume.entity.Profile;
import by.minsk.resume.entity.Skill;
import by.minsk.resume.entity.SkillCategory;
import by.minsk.resume.form.SignUpForm;

import java.util.List;

public interface EditProfileService {
    Profile createNewProfile(SignUpForm signUpForm);
    List<Skill> listSkills(long idProfile);
    List<SkillCategory> listSkillCategories ();
    void updateSkills(long idProfile, List<Skill> skills);

}
