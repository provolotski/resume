package by.minsk.resume.annotation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ANNOTATION_TYPE,TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface FirstFieldLessThanSecond {
    String message() default "FieldMatch";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    @Target({ANNOTATION_TYPE, TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FirstFieldLessThanSecond[] value();
    }



}
