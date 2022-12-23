package com.uidser.ppmusic.common.validate.annocation.classz;

import com.uidser.ppmusic.common.validate.annocation.DateValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class DateValidConstrainValidator implements ConstraintValidator<DateValid, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        return value.toString().length() > 0;
    }
}
