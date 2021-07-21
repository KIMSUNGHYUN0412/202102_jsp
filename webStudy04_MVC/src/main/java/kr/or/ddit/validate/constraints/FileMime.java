package kr.or.ddit.validate.constraints;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *	파일 컨텐츠 타입 확인을 위한 검증 어노테이션
 *
 */
@Target({FIELD, METHOD})
@Retention(RUNTIME) 
@Constraint(validatedBy= FileMimeValidator.class) //목적 데이터 검증 제한
public @interface FileMime {
	String mime(); 
	String message() default "{kr.or.ddit.validate.constraints.FileMime.message}";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
