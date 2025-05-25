package org.example.tutorial11.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (s.isBlank()){
            return true;
        }

        if (s.length() < 10) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{password.error.length}").addConstraintViolation();
            return false;
        }

        int lowerCase = 0, upperCase = 0, digits = 0, specialChars = 0;

        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) lowerCase++;
            else if (Character.isUpperCase(c)) upperCase++;
            else if (Character.isDigit(c)) digits++;
            else if ("!@#$%^&*()_+-=[]{}|;:'\",.<>?/\\`~".indexOf(c) != -1) specialChars++;
        }

        if (lowerCase < 1) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{password.error.lowercase}").addConstraintViolation();
            return false;
        }

        if (upperCase < 2) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{password.error.uppercase}").addConstraintViolation();
            return false;
        }

        if (digits < 3) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{password.error.digits}").addConstraintViolation();
            return false;
        }

        if (specialChars < 4) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{password.error.special}").addConstraintViolation();
            return false;
        }

        return true;
    }
}
