package kr.or.ddit.validate.constraints;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.groups.Default;

/**
 *	전화번호 형식 확인을 위한 검증 어노테이션
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy= TelNumberValidator.class)
public @interface TelNumber {
	String message() default "{kr.or.ddit.validate.constraints.TelNumber.message}";
	Class<?>[] groups() default {}; 
	Class<? extends Payload>[] payload() default { };
	
	String regexp() default "\\d{2,4}-\\d{3,4}-\\d{4}";
	String placeholder() default "000-0000-0000";
}
