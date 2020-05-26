package com.kemenu.kemenu_backend.application.validation;

import org.springframework.util.ReflectionUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Constraint(validatedBy = SamePassword.SamePasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SamePassword {

    String message() default "The password and repeated password don't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SamePasswordValidator implements ConstraintValidator<SamePassword, Object> {
        @Override
        public boolean isValid(Object request, ConstraintValidatorContext context) {
            Field passwordField = ReflectionUtils.findField(request.getClass(), "password");
            passwordField.setAccessible(true);
            Field repeatedPasswordField = ReflectionUtils.findField(request.getClass(), "repeatedPassword");
            repeatedPasswordField.setAccessible(true);

            String password = (String) ReflectionUtils.getField(passwordField, request);
            String repeatedPassword = (String) ReflectionUtils.getField(repeatedPasswordField, request);
            return password.equals(repeatedPassword);
        }
    }
}
