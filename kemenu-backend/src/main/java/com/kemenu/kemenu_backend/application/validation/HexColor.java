package com.kemenu.kemenu_backend.application.validation;

import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

@Constraint(validatedBy = HexColor.HexColorValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HexColor {

    String message() default "Invalid hexadecimal color";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class HexColorValidator implements ConstraintValidator<HexColor, String> {
        private static final Pattern hexColorPattern = Pattern.compile("#[0-9A-Fa-f]{6}");
        @Override
        public boolean isValid(String hexColor, ConstraintValidatorContext constraintValidatorContext) {
            return StringUtils.isEmpty(hexColor) || hexColorPattern.matcher(hexColor).matches();
        }
    }
}
