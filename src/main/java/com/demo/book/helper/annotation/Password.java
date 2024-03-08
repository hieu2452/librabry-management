package com.demo.book.helper.annotation;

import com.demo.book.helper.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface Password {
    String message() default "Invalid password";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

    int minLength() default 8;

    boolean hasUppercase() default true;

    boolean hasLowercase() default true;

    boolean hasNumber() default true;

    boolean hasSpecial() default true;
}
