package ah_doc_manag.web.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CheckYearValidator implements ConstraintValidator<YearEqualsDate, Object> {
    private String year;
    private String date;
    private String errorMessage;

    @Override
    public void initialize(YearEqualsDate constraintAnnotation) {
        this.year = constraintAnnotation.year();
        this.date = constraintAnnotation.date();
        this.errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Long yearValue = (Long) new BeanWrapperImpl(value).getPropertyValue(year);
        LocalDate dateValue = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(date);
        if (yearValue == null || dateValue == null) {
            return false;
        }


        int yearDateValue = dateValue.getYear();
        if(yearValue != yearDateValue) {
            addErrorToField(context, year);

            addErrorToField(context, date);

            return false;
        }
        return true;
    }

    private void addErrorToField(ConstraintValidatorContext context, String fieldName) {
        context.buildConstraintViolationWithTemplate(errorMessage)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }
}
