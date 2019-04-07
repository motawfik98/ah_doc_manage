package ah_doc_manag.web.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CheckYearValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface YearEqualsDate {
    String message() default "{invalid.date.year}";

    String year() default "year";

    String date() default "date";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
