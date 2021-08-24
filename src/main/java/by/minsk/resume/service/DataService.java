package by.minsk.resume.service;

import by.minsk.resume.entity.SkillCategory;
import by.minsk.resume.repository.storage.SkillCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired
    private SkillCategoryRepository skillCategoryRepository;


    public List<SkillCategory> skillCategories(){
        return skillCategoryRepository.findAll(new Sort("id"));
    }

}
