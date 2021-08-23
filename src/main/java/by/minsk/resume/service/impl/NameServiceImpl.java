package by.minsk.resume.service.impl;

import by.minsk.resume.service.NameService;
import org.springframework.stereotype.Service;

@Service
public class NameServiceImpl implements NameService {

    @Override
    public String  convertName( String name){
        return name.toUpperCase();
    }
}
