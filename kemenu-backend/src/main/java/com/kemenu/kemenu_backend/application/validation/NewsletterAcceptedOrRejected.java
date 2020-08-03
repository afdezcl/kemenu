package com.kemenu.kemenu_backend.application.validation;

import com.kemenu.kemenu_backend.domain.model.NewsletterStatus;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.util.Objects.nonNull;

@Constraint(validatedBy = NewsletterAcceptedOrRejected.NewsletterAcceptedOrRejectedValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NewsletterAcceptedOrRejected {

    String message() default "The value should be ACCEPTED or REJECTED";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NewsletterAcceptedOrRejectedValidator implements ConstraintValidator<NewsletterAcceptedOrRejected, NewsletterStatus> {
        @Override
        public boolean isValid(NewsletterStatus newsletterStatus, ConstraintValidatorContext constraintValidatorContext) {
            return nonNull(newsletterStatus) && (newsletterStatus.isAccepted() || newsletterStatus.isRejected());
        }
    }
}
