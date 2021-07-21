package kr.or.ddit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.validate.groups.UpdateGroup;

public class ValidatorUtils<T> {
	private static Validator validator;
	static {
		ValidatorFactory factory =  Validation.byDefaultProvider()
		        .configure()
		        .messageInterpolator(
		                new ResourceBundleMessageInterpolator(
		                        new PlatformResourceBundleLocator( "kr.or.ddit.msgs.errorMessage" )
		                )
		        )
		        .buildValidatorFactory();
		
		validator = factory.getValidator();
	}
	
	public boolean validate(T target, Map<String, List<String>> errors, Class<?>...groups) {
		Set<ConstraintViolation<T>> violations = validator.validate(target, groups);
		for(ConstraintViolation<T> singleViolation : violations) { 
			String key = singleViolation.getPropertyPath().toString();
			String message = singleViolation.getMessage();
		 	List<String> messages = errors.get(key);
		 	if(messages==null) {
		 		messages = new ArrayList<>();
		 		errors.put(key, messages);
		 	}
		 	messages.add(message);
		}
		return violations.size()==0;
	}
}
