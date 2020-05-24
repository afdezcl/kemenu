package com.kemenu.kemenu_backend.application.validation;

import com.kemenu.kemenu_backend.application.customer.PasswordChangeRequest;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SamePassword.SamePasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SamePassword {

    String message() default "The password and repeated password don't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SamePasswordValidator implements ConstraintValidator<SamePassword, PasswordChangeRequest> {
        @Override
        public boolean isValid(PasswordChangeRequest request, ConstraintValidatorContext context) {
            return request.getPassword().equals(request.getRepeatedPassword());
        }
    }
}
