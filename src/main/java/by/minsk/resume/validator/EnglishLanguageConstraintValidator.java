package by.minsk.resume.validator;

import by.minsk.resume.annotation.constraints.EnglishLanguage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnglishLanguageConstraintValidator implements ConstraintValidator<EnglishLanguage, String> {
    private boolean withNumbers;
    private boolean withPunctuations;
    private boolean withSpechSymbols;

    @Override
    public void initialize(EnglishLanguage englishLanguage) {
        this.withNumbers = englishLanguage.withNumbers();
        this.withPunctuations = englishLanguage.withPunctuations();
        this.withSpechSymbols = englishLanguage.withSpechSymbols();


    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null ){
            return true;
        }
        String validationTemplate = getValidationTemplate();
        for (int i = 0; i<s.length(); i++)
        {
            char ch = s.charAt(i);
            if(validationTemplate.indexOf(ch) ==-1){
                return false;
            }
        }
        return true;
    }

    private static final String SPETCH_SYMBOLS = "~#$%^&*-+=_\\|/@`!'\";:><,.?{}";
    private static final String PUNCTUATIONS = ".,?!-:()'\"[]{}; \t\n";
    private static final String NUMBERS = "0123456789";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String getValidationTemplate(){
        StringBuilder template = new StringBuilder(LETTERS);
        if(withNumbers) {
            template.append(NUMBERS);
        }
        if(withPunctuations) {
            template.append(PUNCTUATIONS);
        }
        if(withSpechSymbols) {
            template.append(SPETCH_SYMBOLS);
        }
        return template.toString();
    }
}
