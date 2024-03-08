package com.demo.book.helper;



import com.demo.book.helper.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordValidator implements ConstraintValidator<Password,String> {
    private int minLength;
    private boolean hasUppercase;
    private boolean hasLowercase;
    private boolean hasNumber;
    private boolean hasSpecial;
    @Override
    public void initialize(Password constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
        this.minLength = constraintAnnotation.minLength();
        this.hasUppercase = constraintAnnotation.hasUppercase();
        this.hasLowercase = constraintAnnotation.hasLowercase();
        this.hasNumber = constraintAnnotation.hasNumber();
        this.hasSpecial = constraintAnnotation.hasSpecial();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        boolean valid = value.length() >= minLength;
        if (hasUppercase) {
            valid &= !value.equals(value.toLowerCase());
        }
        if (hasLowercase) {
            valid &= !value.equals(value.toUpperCase());
        }
        if (hasNumber) {
            valid &= value.matches(".*\\d.*");
        }
        if (hasSpecial) {
            valid &= value.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        }
        return valid;
    }
}
