package com.clonegod.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.clonegod.validation.validation.CardNumberConstraintValidator;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { CardNumberConstraintValidator.class })
public @interface ValidCardNumber {

	String message() default  "{com.clonegod.validation.constraints.InvalidCardNumber.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
}
